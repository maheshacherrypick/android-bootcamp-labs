package org.justinlloyd.yambaclient;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
	}
}
