package org.justinlloyd.yambaclient;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
	private static final String TAG = DBHelper.class.getName();

	public DBHelper(Context context)
	{
		super(context, "timeline.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.d(TAG, "Creating new database for Status Updates...");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.d(TAG, "Upgrading database from version " + oldVersion + " to version " + newVersion + ".");
	}
}