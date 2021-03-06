package com.thenewcircle.yamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;

public class StatusActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = StatusActivity.class.getSimpleName();

    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;
    private AsyncTask task;

    private String username;
    private String password;

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StatusActivity.this);

        username = prefs.getString("username", "student");
        password = prefs.getString("password", "password");

        Log.d(TAG, "onCreated");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (task != null) {
            Log.d("StatusActivity", "Cancelling task in onStop");
            task.cancel(true);
        }
    }

    public void postTweet(String tweet) {

        task = new PostTweetTask(this).execute(tweet);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("username")) {
            username = sharedPreferences.getString("username", "student");
        }
        Log.d(TAG,"onSharedPreferenceChanged");
        String USERNAME_KEY = getResources().getString(R.string.prefsUsernameKey);
        String PASSWORD_KEY = getResources().getString(R.string.prefsPasswordKey);
        username = sharedPreferences.getString(USERNAME_KEY, "student");
        password = sharedPreferences.getString(PASSWORD_KEY, "password");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
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
                Log.d("StatusActivty","Cancelling AsyncTasks");
                return "cancelled";
            }

//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StatusActivity.this);
//
//            String username = prefs.getString("username", "student");
//            String password = prefs.getString("password", "password");

            YambaClient yambaClient = new YambaClient(username, password);

            try {
                yambaClient.postStatus(tweet[0]);

                List<YambaClient.Status> timeline = yambaClient.getTimeline(100);

                for (YambaClient.Status status : timeline) {
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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Posting tweet...");
            dialog.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent activityIntent = new Intent(this, SettingsActivity.class);
            startActivity(activityIntent);
            return true;
        }

        if (id == R.id.action_timeline) {
            Log.d("StatusActivity","Item Pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
