package org.justinlloyd.yambaclient;

import android.provider.BaseColumns;

public class StatusUpdateContract
{
	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "status";

	public class DataColumn
	{
		public static final String ID = BaseColumns._ID;
	}
}
