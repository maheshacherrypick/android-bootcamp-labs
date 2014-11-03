package com.thenewcircle.yamba;

import android.content.ContentValues;
import android.net.Uri;
import android.test.ProviderTestCase2;

import java.util.Date;

/**
     * <a href="http://developer.android.com/reference/android/test/ProviderTestCase2.html">Testing Fundamentals</a>
     */

    public class ContentProviderTest extends ProviderTestCase2<StatusProvider> {

        public ContentProviderTest() {
            super(StatusProvider.class, StatusContract.AUTHORITY);
        }

        @Override
        protected void setUp() throws Exception {
            super.setUp();
            getMockContentResolver().addProvider(StatusContract.AUTHORITY, getProvider());
        }


        public void testInsertItem() {

            ContentValues values = new ContentValues();

            values.put(StatusContract.Column.ID, 100);
            values.put(StatusContract.Column.USER, "Test User");
            values.put(StatusContract.Column.MESSAGE, "My tweet.");
            values.put(StatusContract.Column.CREATED_AT, new Date().getTime());

            Uri uri = getMockContentResolver().insert(StatusContract.CONTENT_URI, values);

//            Uri uri = Uri.withAppendedPath(StatusContract.CONTENT_URI, StatusContract.TABLE);
//            Cursor cursor = getMockContentResolver().insert(uri, null, null, null, null);
//            assertEquals(1, cursor.getCount());
//            // move to first row
//            cursor.moveToNext();
//            assertEquals("My tweet", cursor.getString(cursor.getColumnIndex("message")));
        }

//        public void testQueryItem() {
//            Uri uri = Uri.withAppendedPath(StatusContract.CONTENT_URI, StatusContract.TABLE);
//            uri = Uri.withAppendedPath(uri, "???");
//            Cursor cursor = getMockContentResolver().query(uri, null, null, null, null);
//            assertEquals(1, cursor.getCount());
//            // move to first row
//            cursor.moveToNext();
//            assertEquals("My tweet", cursor.getString(cursor.getColumnIndex("message")));
//        }

    }



