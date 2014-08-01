package org.justinlloyd.yambaclient;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class RefreshService extends IntentService
{
	// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
	private static final String ACTION_REFRESH = "org.justinlloyd.yambaclient.action.REFRESH";

	private static final String EXTRA_ITEMS_TO_RETRIEVE = "org.justinlloyd.yambaclient.extra.ITEMS_TO_RETRIEVE";

	/**
	 * Starts this service to perform action Refresh with the given parameters. If
	 * the service is already performing a task this action will be queued.
	 *
	 * @see IntentService
	 */
	public static void startActionRefresh(Context context, String param1)
	{
		Intent intent = new Intent(context, RefreshService.class);
		intent.setAction(ACTION_REFRESH);
		intent.putExtra(EXTRA_ITEMS_TO_RETRIEVE, param1);
		context.startService(intent);
	}

	public RefreshService()
	{
		super("RefreshService");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		if (intent != null)
		{
			final String action = intent.getAction();
			if (ACTION_REFRESH.equals(action))
			{
				final String param1 = intent.getStringExtra(EXTRA_ITEMS_TO_RETRIEVE);
				handleActionRefresh(param1);
			}
		}
	}

	/**
	 * Handle action Refresh in the provided background thread with the provided
	 * parameters.
	 */
	private void handleActionRefresh(String param1)
	{
		// TODO: Handle action Refresh
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
