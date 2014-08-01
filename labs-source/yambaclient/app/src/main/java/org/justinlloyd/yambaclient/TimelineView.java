package org.justinlloyd.yambaclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;

public class TimelineView extends Activity
{
	private final static String TAG = TimelineView.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline_view);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		else if (id == R.id.action_refresh)
		{
			Log.d(TAG, "Refresh action was clicked");
			refreshTimeline();
			return true;
		}
		else if (id == R.id.action_post_status_update)
		{
		}

		return super.onOptionsItemSelected(item);
	}

	public void buttonRefreshTimeline(View v)
	{
		refreshTimeline();
	}

	private void refreshTimeline()
	{
		Log.d(TAG, "Refresh timeline button clicked");
		YambaClient yambaClient = new YambaClient("student", "password");
		try
		{
			List<YambaClient.Status> statusUpdates = yambaClient.getTimeline(100);
			for (YambaClient.Status statsUpdate : statusUpdates)
			{
				Log.i(TAG, "Status Update: " + statsUpdate.getUser() + " : " + statsUpdate.getCreatedAt() + " - " + statsUpdate.getMessage());
			}

		}

		catch (YambaClientException e)
		{
			Log.e(TAG, "Talking to the Yamba sever threw an exception");
			Log.e(TAG, e.toString());
		}
	}
}
