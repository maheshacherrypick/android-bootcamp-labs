package org.justinlloyd.yambaclient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

		return super.onOptionsItemSelected(item);
	}

	public void onActionSettings(MenuItem item)
	{
		Log.d(TAG, "Settings action was clicked");
		startActivity(new Intent(this, SettingsActivity.class));
	}

	public void onActionRefresh(MenuItem item)
	{
		Log.d(TAG, "Refresh action was clicked");
		refreshTimeline();
	}

	public void onActionPostStatusUpdate(MenuItem item)
	{
		Log.d(TAG, "Post Status Update action was clicked");
		startActivity(new Intent(this, PostStatusUpdate.class));
	}

	public void buttonRefreshTimeline(View v)
	{
		refreshTimeline();
	}

	private void refreshTimeline()
	{
		RefreshService.startActionRefresh(this, 100);
	}

	public void buttonDumpDatabase(View v)
	{
		Cursor cursor = getContentResolver().query(StatusUpdateContract.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext())
		{
			StringBuilder builder = new StringBuilder();
			builder.append(cursor.getString(cursor.getColumnIndex(StatusUpdateContract.DataColumn.USER)));
			builder.append(" (");
			builder.append(cursor.getString(cursor.getColumnIndex(StatusUpdateContract.DataColumn.CREATED_AT)));
			builder.append(") : ");
			Log.d(TAG, builder.toString());
		}
	}
}
