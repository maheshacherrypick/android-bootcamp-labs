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
		super(context, StatusUpdateContract.DB_NAME, null, StatusUpdateContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.d(TAG, "Creating new database for Status Updates...");
		String sql = String.format("create table %s (%s int primary key, %s text, %s text, %s int)",
				StatusUpdateContract.TABLE_NAME,
				StatusUpdateContract.DataColumn.ID,
				StatusUpdateContract.DataColumn.USER,
				StatusUpdateContract.DataColumn.MESSAGE,
				StatusUpdateContract.DataColumn.CREATED_AT);
		db.execSQL(sql);
		Log.d(TAG, "Created database using the following SQL: " + sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.d(TAG, "Upgrading database from version " + oldVersion + " to version " + newVersion + ".");
		db.execSQL("drop table if exists " + StatusUpdateContract.TABLE_NAME);
		onCreate(db);
	}
}
