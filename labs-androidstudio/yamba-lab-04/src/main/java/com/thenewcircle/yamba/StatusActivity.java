package com.thenewcircle.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity {

    private static final String TAG = StatusActivity.class.getSimpleName();
    
    private Button mButtonTweet;
    private EditText mTextStatus;
    private TextView mTextCount;
    private int mDefaultColor;

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
                Log.d("StatusActivity","Post tweet");
                String tweet = mTextStatus.getText().toString();
                String result = postTweet(tweet);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }


        });

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        Log.d(TAG, "onCreated");
    }

    public String postTweet(String tweet) {
        YambaClient yambaClient = new YambaClient("student", "password");

        try {
            yambaClient.postStatus(tweet);
            return "success";
        } catch (YambaClientException e) {
            e.printStackTrace();
            return "failure";






        }
    }

}
