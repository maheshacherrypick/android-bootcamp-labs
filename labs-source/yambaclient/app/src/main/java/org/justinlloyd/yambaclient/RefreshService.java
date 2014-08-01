package org.justinlloyd.yambaclient;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;
import com.marakana.android.yamba.clientlib.YambaClientUnauthorizedException;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class RefreshService extends IntentService
{
	private final static String TAG = TimelineView.class.getName();

	// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
	private static final String ACTION_REFRESH = "org.justinlloyd.yambaclient.action.REFRESH";

	private static final String EXTRA_ITEMS_TO_RETRIEVE = "org.justinlloyd.yambaclient.extra.ITEMS_TO_RETRIEVE";

	/**
	 * Starts this service to perform action Refresh with the given parameters. If
	 * the service is already performing a task this action will be queued.
	 *
	 * @see IntentService
	 */
	public static void startActionRefresh(Context context, int itemsToRetrieve)
	{
		Intent intent = new Intent(context, RefreshService.class);
		intent.setAction(ACTION_REFRESH);
		intent.putExtra(EXTRA_ITEMS_TO_RETRIEVE, itemsToRetrieve);
		context.startService(intent);
	}

	public RefreshService()
	{
		super("RefreshService");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		if (intent == null)
		{
			Log.e(TAG, "Passed a null intent.");
			return;
		}

		final String action = intent.getAction();
		if (ACTION_REFRESH.equals(action))
		{
			int itemsToRetrieve;
			try
			{
				itemsToRetrieve = Integer.parseInt(intent.getStringExtra(EXTRA_ITEMS_TO_RETRIEVE));
			}

			catch (NumberFormatException ex)
			{
				itemsToRetrieve = 100;
			}

			handleActionRefresh(itemsToRetrieve);
		}
		else
		{
		}

	}

	/**
	 * Handle action Refresh in the provided background thread with the provided
	 * parameters.
	 */
	private void handleActionRefresh(int itemsToRetrieve)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final String username = prefs.getString("username", "");
		final String password = prefs.getString("password", "");
		if (username.isEmpty() || password.isEmpty())
		{
			Log.d(TAG, "Cannot refresh time line, no username or password set in preferences.");
			Toast.makeText(this, "Cannot refresh time line, no username or password set in preferences.", Toast.LENGTH_SHORT).show();
			return;
		}

		YambaClient yambaClient = new YambaClient(username, password);
		try
		{
			List<YambaClient.Status> statusUpdates = yambaClient.getTimeline(itemsToRetrieve);
			for (YambaClient.Status statsUpdate : statusUpdates)
			{
				Log.i(TAG, "Status Update: " + statsUpdate.getUser() + " : " + statsUpdate.getCreatedAt() + " - " + statsUpdate.getMessage());
			}

		}

		catch (YambaClientUnauthorizedException e)
		{
			Log.e(TAG, "User is not authorized. Possibly incorrect name or password.");
			Toast.makeText(this, "User is not authorized. Possibly incorrect name or password.", Toast.LENGTH_SHORT).show();
		}

		catch (YambaClientException e)
		{
			Log.e(TAG, "Talking to the Yamba sever threw an exception");
			Log.e(TAG, e.toString());
		}
	}

}
