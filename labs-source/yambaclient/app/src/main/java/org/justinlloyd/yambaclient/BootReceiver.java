package org.justinlloyd.yambaclient;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver
{
	private static final String TAG = BootReceiver.class.getName();
	private static final long DEFAULT_REFRESH_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	public BootReceiver()
	{
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		PendingIntent pendingRefresh = PendingIntent.getService(context, -1, new Intent(context, RefreshService.class), PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Log.d(TAG, "Set a repeating alarm for the YambaClient RefreshService to " + DEFAULT_REFRESH_INTERVAL + " minutes.");
		alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), DEFAULT_REFRESH_INTERVAL, pendingRefresh);
	}
}
