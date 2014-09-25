package com.thenewcircle.yamba;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
	public static final int NOTIFICATION_ID = 42;

	@Override
	public void onReceive(Context context, Intent intent) {

        int count = intent.getIntExtra("count", 0);

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, TimelineActivity.class);

		PendingIntent pendingIntent =
                PendingIntent.getActivity(context, -1, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

		Notification notification = new Notification.Builder(context)
				.setContentTitle("New tweets!")
				.setContentText("You've got " + count + " new tweets")
				.setSmallIcon(android.R.drawable.sym_action_email)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.getNotification();
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
