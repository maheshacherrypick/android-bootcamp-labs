package org.justinlloyd.yambaclient;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class StatusUpdateProvider extends ContentProvider {
	private static final String TAG = StatusUpdateProvider.class.getName();

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static
	{
		sURIMatcher.addURI(StatusUpdateContract.AUTHORITY, StatusUpdateContract.TABLE_NAME, StatusUpdateContract.STATUS_UPDATE_DIR);
		sURIMatcher.addURI(StatusUpdateContract.AUTHORITY, StatusUpdateContract.TABLE_NAME + "/#", StatusUpdateContract.STATUS_UPDATE_ITEM);
	}

    public StatusUpdateProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
		switch (sURIMatcher.match(uri))
		{
			case StatusUpdateContract.STATUS_UPDATE_DIR:
				Log.d(TAG, "gotType: " + StatusUpdateContract.STATUS_TYPE_DIR);
				return StatusUpdateContract.STATUS_TYPE_DIR;
			case StatusUpdateContract.STATUS_UPDATE_ITEM:
				Log.d(TAG, "gotType: " + StatusUpdateContract.STATUS_TYPE_ITEM);
				throw new UnsupportedOperationException("Not yet implemented");
			default:
				Log.e(TAG, "Illegal URI supplied: " + uri);
				throw new IllegalArgumentException("Illegal URI supplied: " + uri);
		}
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
