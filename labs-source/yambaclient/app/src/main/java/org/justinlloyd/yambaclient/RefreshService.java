package org.justinlloyd.yambaclient;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RefreshService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_REFRESH = "org.justinlloyd.yambaclient.action.REFRESH";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "org.justinlloyd.yambaclient.extra.ITEMS_TO_RETRIEVE";

    /**
     * Starts this service to perform action Refresh with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionRefresh(Context context, String param1) {
        Intent intent = new Intent(context, RefreshService.class);
        intent.setAction(ACTION_REFRESH);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    public RefreshService() {
        super("RefreshService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REFRESH.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionRefresh(param1);
            }
        }
    }

    /**
     * Handle action Refresh in the provided background thread with the provided
     * parameters.
     */
    private void handleActionRefresh(String param1) {
        // TODO: Handle action Refresh
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
