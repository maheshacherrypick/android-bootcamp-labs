package org.justinlloyd.yambaclient;

import android.net.Uri;
import android.provider.BaseColumns;

public class StatusUpdateContract
{
	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "status";

	public static final String AUTHORITY = "org.justinlloyd.yambaclient.StatusUpdateProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	public static final int STATUS_UPDATE_ITEM = 1;
	public static final int STATUS_UPDATE_DIR = 2;
	public static final String STATUS_TYPE_ITEM = "vnd.android.cursor.item/vnd.org.justinlloyd.yambaclient.provider.status";
	public static final String STATUS_TYPE_DIR = "vnd.android.cursor.dir/vnd.org.justinlloyd.yambaclient.provider.status";

	public class DataColumn
	{
		public static final String ID = BaseColumns._ID;
		public static final String USER = "user";
		public static final String MESSAGE = "message";
		public static final String CREATED_AT = "created_at";
	}
}
