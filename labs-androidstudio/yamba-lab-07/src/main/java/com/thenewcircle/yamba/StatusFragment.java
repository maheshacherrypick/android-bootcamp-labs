package com.thenewcircle.yamba;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thenewcircle.yamba.client.YambaClient;
import com.thenewcircle.yamba.client.YambaClientException;
import com.thenewcircle.yamba.client.YambaStatus;

import java.util.List;

public class StatusFragment extends Fragment {

    private static final String TAG = StatusFragment.class.getSimpleName();

    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;
    private AsyncTask task;

    public void whenUserClicksButton(View view) {
        Log.d("StatusActivity", "User pressed button");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_status, container, false);

        mButtonTweet = (Button) rootView.findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) rootView.findViewById(R.id.status_text);
        mTextCount = (TextView) rootView.findViewById(R.id.status_text_count);
        mTextCount.setText(Integer.toString(140));
        mDefaultColor = mTextCount.getTextColors().getDefaultColor();

        mTextStatus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - s.length();
                mTextCount.setText(Integer.toString(count));

                if (count < 50) {
                    mTextCount.setTextColor(Color.RED);
                } else {
                    mTextCount.setTextColor(mDefaultColor);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

        });

        mButtonTweet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("StatusActivity", "Post tweet");
                String tweet = mTextStatus.getText().toString();
                postTweet(tweet);

            }


        });

        Log.d(TAG, "onCreated");

        return rootView;

    }

    @Override
    public void onStop() {
        super.onStop();
        if (task != null) {
            Log.d("StatusActivity", "Cancelling task in onStop");
            task.cancel(true);
        }
    }

    public void postTweet(String tweet) {

        task = new PostTweetTask(getActivity()).execute(tweet);

    }

    private class PostTweetTask extends AsyncTask<String, Void, String> {

        private Activity activity;
        private ProgressDialog dialog;

        private PostTweetTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... tweet) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isCancelled()) {
                Log.d("StatusActivty", "Cancelling AsyncTasks");
                return "cancelled";
            }

            YambaClient yambaClient = new YambaClient(YambaApplication.getUsername(), YambaApplication.getPassword());

            try {
                yambaClient.postStatus(tweet[0]);

                List<YambaStatus> timeline = yambaClient.getTimeline(100);

                for (YambaStatus status : timeline) {
                    status.getMessage();
                    status.getUser();
                }

                return "success";
            } catch (YambaClientException e) {
                e.printStackTrace();
                return "failure";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Posting tweet...");
            dialog.show();
        }
    }
}
