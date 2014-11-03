package com.thenewcircle.yamba;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class YambaApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    private static String username;
    private static String password;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        username = prefs.getString(getString(R.string.prefsUsernameKey), "student");
        password = prefs.getString(getString(R.string.prefsPasswordKey), "password");

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        username = sharedPreferences.getString(getString(R.string.prefsUsernameKey), "student");
        password = sharedPreferences.getString(getString(R.string.prefsUsernameKey), "password");
    }

}
