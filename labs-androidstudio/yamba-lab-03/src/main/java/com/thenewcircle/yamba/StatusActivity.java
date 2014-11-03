package com.thenewcircle.yamba;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity {

    private static final String TAG = StatusActivity.class.getSimpleName();

    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;
    private int mTweetSize;

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

        mTweetSize = getResources().getInteger(R.integer.tweet_size);

        mTextCount.setText(Integer.toString(mTweetSize));

        mDefaultColor = mTextCount.getTextColors().getDefaultColor();

        mTextStatus.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable textString) {
                int count = mTweetSize - textString.length();
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
                Log.d(TAG,"Post tweet");
                String tweet = mTextStatus.getText().toString();
                String result = postTweet(tweet);
                Toast.makeText(StatusActivity.this, result, Toast.LENGTH_SHORT).show();
            }

        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d(TAG, "onCreated");
    }

    public String postTweet(String tweet) {
        return "success";
    }

}
