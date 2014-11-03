package com.thenewcircle.yamba;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class StatusActivityTest
       extends ActivityInstrumentationTestCase2<StatusFragment> {

    public StatusActivityTest() {
        super("com.thenewcircle.yamba", StatusFragment.class);
    }

    Instrumentation mInstrumentation;
    Activity mActivity;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mInstrumentation = getInstrumentation();
        mActivity = getActivity(); // get a references to the app under test
    }

    @UiThreadTest
    public void testCharacterCount() {

        final TextView mTextCount = (TextView) mActivity.findViewById(R.id.status_text_count);
        final EditText mStatusText = (EditText) mActivity.findViewById(R.id.status_text);

//        mActivity.runOnUiThread(new Runnable() {
//            public void run() {
//                mStatusText.setText("This is my tweet");
//            }
//        });

        Log.d("Test", YambaApplication.getUsername());

        //mInstrumentation.callApplicationOnCreate(new YambaApplication());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

//        String username = prefs.getString(getString(R.string.prefsUsernameKey), "student");
//        String password = prefs.getString(getString(R.string.prefsPasswordKey), "password");

        Log.d("Test", YambaApplication.getUsername());

        mStatusText.setText("This is my tweet");

        String mActualCount = mTextCount.getText().toString();

        assertEquals( "Incorrect count", mActualCount, "124");

    }
}
