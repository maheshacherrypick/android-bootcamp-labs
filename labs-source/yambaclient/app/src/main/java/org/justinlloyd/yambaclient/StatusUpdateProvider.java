package org.justinlloyd.yambaclient;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusUpdateProvider extends ContentProvider
{
	private static final String TAG = StatusUpdateProvider.class.getName();

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	private DBHelper dbHelper;

	static
	{
		sURIMatcher.addURI(StatusUpdateContract.AUTHORITY, StatusUpdateContract.TABLE_NAME, StatusUpdateContract.STATUS_UPDATE_DIR);
		sURIMatcher.addURI(StatusUpdateContract.AUTHORITY, StatusUpdateContract.TABLE_NAME + "/#", StatusUpdateContract.STATUS_UPDATE_ITEM);
	}

	public StatusUpdateProvider()
	{
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public String getType(Uri uri)
	{
		switch (sURIMatcher.match(uri))
		{
			case StatusUpdateContract.STATUS_UPDATE_DIR:
				Log.d(TAG, "gotType: " + StatusUpdateContract.STATUS_TYPE_DIR);
				return StatusUpdateContract.STATUS_TYPE_DIR;
			case StatusUpdateContract.STATUS_UPDATE_ITEM:
				Log.d(TAG, "gotType: " + StatusUpdateContract.STATUS_TYPE_ITEM);
				return StatusUpdateContract.STATUS_TYPE_ITEM;
			default:
				Log.e(TAG, "Illegal URI supplied: " + uri);
				throw new IllegalArgumentException("Illegal URI supplied: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		if (sURIMatcher.match(uri) != StatusUpdateContract.STATUS_UPDATE_DIR)
		{
			throw new IllegalArgumentException("Illegal uri: " + uri + " insert cannot handle multiple items");
		}

		Uri ret = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insertWithOnConflict(StatusUpdateContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		if (rowId != -1)
		{
			long id = values.getAsLong(StatusUpdateContract.DataColumn.ID);
			ret = ContentUris.withAppendedId(uri, id);
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return ret;
	}

	@Override
	public boolean onCreate()
	{
		Log.d(TAG, "Creating provider for status updates.");
		dbHelper = new DBHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder)
	{
		// TODO: Implement this to handle query requests from clients.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs)
	{
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
