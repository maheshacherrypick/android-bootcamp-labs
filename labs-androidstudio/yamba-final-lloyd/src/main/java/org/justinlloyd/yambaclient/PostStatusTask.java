package org.justinlloyd.yambaclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;
import com.marakana.android.yamba.clientlib.YambaClientUnauthorizedException;

public class PostStatusTask extends AsyncTask<String, Void, Long> {

    public static final long POST_SUCCESS = 0L;
    public static final long POST_FAILED = -1L;
    public static final long POST_FAILED_USERNAME_EMPTY = -2L;
    public static final long POST_FAILED_PASSWORD_EMPTY = -3L;
	public static final long POST_FAILED_UNAUTHORIZED = -4L;
    private static final String TAG = PostStatusTask.class.getName();
    private final Activity context;
    private ProgressDialog progress;

    public PostStatusTask(Activity context) {
        this.context = context;
    }

    // runs on the UI thread prior to doInBackground
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(PostStatusTask.class.getName(), "onPreExecute");
        progress = ProgressDialog.show(context, context.getResources().getString(R.string.post_status_update_progress_title), context.getResources().getString(R.string.post_status_update_progress_message));
        progress.setCancelable(true);
    }

    @Override
    protected Long doInBackground(String... params) {
        String username = PreferenceManager.getDefaultSharedPreferences(context).getString("username", "");
        if (username.isEmpty()) {
            Log.e(TAG, "Username preference is empty, cannot post to server.");
            return POST_FAILED_USERNAME_EMPTY;
        }

        Log.d(TAG, String.format("Username is set to: \"%s\"", username));
        String password = PreferenceManager.getDefaultSharedPreferences(context).getString("password", "");
        Log.d(TAG, String.format("Password is set to: \"%s\"", password));
        if (password.isEmpty()) {
            Log.e(TAG, "Password preference is empty, cannot post to server.");
            return POST_FAILED_PASSWORD_EMPTY;
        }

		YambaClient yc = new YambaClient(username, password);
        try {
            long startTime = System.currentTimeMillis();
            yc.postStatus(params[0]);
            long endTime = System.currentTimeMillis();
            final long totalTime = endTime - startTime;
            Log.d(TAG, String.format("Posted the status message in %d ms", totalTime));
            return POST_SUCCESS;
        }

		catch (YambaClientUnauthorizedException e)
		{
			Log.e(TAG, "User is not authorized. Possibly incorrect name or password.");
			return POST_FAILED_UNAUTHORIZED;
		}

		catch (YambaClientException e) {
            Log.d(TAG, e.toString());
            return POST_FAILED;
        }
    }

    // runs on the UI thread after doInBackground has finished
    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        Log.d(PostStatusTask.class.getName(), "onPostExecute");
        progress.dismiss();
        if (context != null && result != null) {
            Log.d(TAG, String.format("Post message async task completed with result code: %d", result));
            if (result == POST_SUCCESS) {

                Toast.makeText(context, context.getResources().getString(R.string.post_status_update_success), Toast.LENGTH_SHORT).show();
            } else if (result == POST_FAILED) {
                Toast.makeText(context, context.getResources().getString(R.string.post_status_update_failed), Toast.LENGTH_SHORT).show();
            } else if (result == POST_FAILED_USERNAME_EMPTY) {
                Toast.makeText(context, context.getResources().getString(R.string.post_status_update_failed_username_empty), Toast.LENGTH_SHORT).show();
            } else if (result == POST_FAILED_PASSWORD_EMPTY) {
                Toast.makeText(context, context.getResources().getString(R.string.post_status_update_failed_password_empty), Toast.LENGTH_SHORT).show();
            } else if (result == POST_FAILED_UNAUTHORIZED) {
				Toast.makeText(context, context.getResources().getString(R.string.post_status_update_failed_unauthorized), Toast.LENGTH_SHORT).show();
			}
        }
    }
}
