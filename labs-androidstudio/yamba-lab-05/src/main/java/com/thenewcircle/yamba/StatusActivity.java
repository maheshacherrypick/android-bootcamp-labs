package com.thenewcircle.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity {

    private static final String TAG = StatusActivity.class.getSimpleName();

    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;
    private AsyncTask task;

    public void whenUserClicksButton(View view) {
        Log.d("StatusActivity", "User pressed button");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mButtonTweet = (Button) findViewById(R.id.status_button_tweet);
        mTextStatus = (EditText) findViewById(R.id.status_text);
        mTextCount = (TextView) findViewById(R.id.status_text_count);
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("StatusActivity", "Running onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void postTweet(String tweet) {

        new PostTweetTask().execute(tweet);
    }

    private class PostTweetTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... tweet) {

            YambaClient yambaClient = new YambaClient("student", "password");

            try {
                yambaClient.postStatus(tweet[0]);
                return "success";
            } catch (YambaClientException e) {
                e.printStackTrace();
                return "failure";
            }

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
//            dialog.dismiss();
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//            mButtonTweet.setVisibility(View.VISIBLE);
//            task = null;
        }

    }

}
