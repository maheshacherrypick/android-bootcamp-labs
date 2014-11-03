package com.thenewcircle.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = DbHelper.class.getSimpleName();

	public DbHelper(Context context) {
		super(context, YambaConstants.DB_NAME, null, YambaConstants.DB_VERSION);
	}

	// Called only once first time we create the database
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String
				.format("create table %s (%s int primary key, %s text, %s text, %s int)",
						YambaConstants.TABLE, YambaConstants.Column.ID,
						YambaConstants.Column.USER,
						YambaConstants.Column.MESSAGE,
						YambaConstants.Column.CREATED_AT);
		Log.d(TAG, "onCreate with SQL: "+sql);
		db.execSQL(sql);
	}

	// Gets called whenever existing version != new version, i.e. schema changed
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Typically you do ALTER TABLE ...
		db.execSQL("drop table if exists " + YambaConstants.TABLE);
		onCreate(db);
	}

}
