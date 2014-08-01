package org.justinlloyd.yambaclient;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
	private static final String TAG = DBHelper.class.getName();

	public DBHelper(Context context)
	{
		super(context, StatusUpdateContract.DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.d(TAG, "Creating new database for Status Updates...");
		String sql = "create table status ("+ BaseColumns._ID + " int primary key, user text, message text, created_at int)";
		db.execSQL(sql);
		Log.d(TAG, "Created database using the following SQL: " + sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.d(TAG, "Upgrading database from version " + oldVersion + " to version " + newVersion + ".");
		db.execSQL("drop table if exists status");
		onCreate(db);
	}
}
