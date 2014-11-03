package com.thenewcircle.yamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thenewcircle.yamba.client.YambaStatus;
import com.thenewcircle.yamba.client.YambaClient;
import com.thenewcircle.yamba.client.YambaClientException;

import java.util.List;

public class TimelineActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = TimelineActivity.class.getSimpleName();

    private String username;
    private String password;

    public void whenUserClicksButton(View view) {
        Log.d("StatusActivity", "User pressed button");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(TimelineActivity.this);

        username = prefs.getString("username", "student");
        password = prefs.getString("password", "password");

        new GetTimelineTask().execute();

        Log.d(TAG, "onCreated");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        username = sharedPreferences.getString("username", "student");
        password = sharedPreferences.getString("password", "password");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    private class GetTimelineTask extends AsyncTask<Void, Void, List<YambaStatus>> {

        private ProgressDialog dialog;

        @Override
        protected List<YambaStatus> doInBackground(Void... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isCancelled()) {
                Log.d("TimelineActivty", "Cancelling AsyncTasks");
                return null;
            }

//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StatusActivity.this);
//
//            String username = prefs.getString("username", "student");
//            String password = prefs.getString("password", "password");

            YambaClient yambaClient =
                    new YambaClient(username, password);

            List<YambaStatus> timeline = null;

            try {

                timeline = yambaClient.getTimeline(100);

                return timeline;
            } catch (YambaClientException e) {
                e.printStackTrace();
                return timeline;
            }
        }

        @Override
        protected void onPostExecute(List<YambaStatus> timeline) {
            for (YambaStatus status : timeline) {
                Log.d("TimelineActivity", "YambaStatus: " + status.getMessage());
            }
            dialog.dismiss();
            String result = "Number of items: " + timeline.size();
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            YambaStatusArrayAdapter adapter =
                    new YambaStatusArrayAdapter(TimelineActivity.this, 0, timeline);

            ListView listView = (ListView) findViewById(R.id.timeline);
            listView.setAdapter(adapter);

        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(TimelineActivity.this);
            dialog.setMessage("Getting timeline...");
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent activityIntent = new Intent(this, SettingsActivity.class);
            startActivity(activityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class YambaStatusArrayAdapter extends ArrayAdapter<YambaStatus> {

        public YambaStatusArrayAdapter(Context context, int textViewResourceId, List<YambaStatus> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            YambaStatus status = getItem(position);

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_timeline_item, null);
            }

            TextView statusUser = (TextView) convertView.findViewById(R.id.status_user);
            TextView statusMessage = (TextView) convertView.findViewById(R.id.status_message);

            statusUser.setText(status.getUser());
            statusMessage.setText(status.getMessage());

            return convertView;
        }
    }

}
