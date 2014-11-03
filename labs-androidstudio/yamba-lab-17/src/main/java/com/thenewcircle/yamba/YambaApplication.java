package com.thenewcircle.yamba;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class YambaApplication extends Application {

    private static final String TAG = YambaApplication.class.getSimpleName();

    private static String testValue;

    public static String getTestValue() {
        return testValue;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        testValue = "test";

        Log.d(TAG, "onCreate");

        Intent intent = new Intent("com.thenewcircle.yamba.intent.action.GET_TIMELINE");

        int requestId = (int) System.currentTimeMillis();

        PendingIntent pendingIntent =
                PendingIntent.getService(getApplicationContext(),
                        requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a reference to the alarm manager
        AlarmManager alarmManager =
                (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        // We want the alarm to go off 5 seconds from now.
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(System.currentTimeMillis());
        startTime.add(Calendar.SECOND, 5);

        // Schedule the alarm, the alarm should repeat every 30 seconds
        int repeatSeconds = 10;

        alarmManager.setRepeating(AlarmManager.RTC, startTime.getTimeInMillis(),
                        repeatSeconds * 1000, pendingIntent);

        Log.d(TAG, "Running onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // remove alarm
    }
}
