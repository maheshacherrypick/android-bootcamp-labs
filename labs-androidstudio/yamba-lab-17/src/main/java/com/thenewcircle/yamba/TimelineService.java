package com.thenewcircle.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.thenewcircle.yamba.client.YambaClient;
import com.thenewcircle.yamba.client.YambaClientException;
import com.thenewcircle.yamba.client.YambaStatus;

import java.util.List;

public class TimelineService extends IntentService {

    public static final String TAG = TimelineService.class.getSimpleName();

    public TimelineService() {
        super("TimelineService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        YambaClient yambaClient =
                new YambaClient("student", "password");

        List<YambaStatus> timeline = null;

        try {
            timeline = yambaClient.getTimeline(100);
        } catch (YambaClientException e) {
            e.printStackTrace();
        }

        for (YambaStatus status: timeline) {
            Log.d(TAG, status.getMessage());
        }
    }

}
