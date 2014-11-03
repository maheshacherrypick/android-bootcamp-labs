package com.thenewcircle.yamba;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import java.util.Date;

/**
 * <a href="http://developer.android.com/reference/android/test/ProviderTestCase2.html">Testing Fundamentals</a>
 */

public class ContentProviderTest extends ProviderTestCase2<StatusProvider> {

    public ContentProviderTest() {
        super(StatusProvider.class, YambaConstants.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getMockContentResolver().addProvider(YambaConstants.AUTHORITY, getProvider());
        // setup data
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // delete data
    }


    public void testInsertItem() {

        ContentValues values = new ContentValues();

        values.put(YambaConstants.Column.ID, 100);
        values.put(YambaConstants.Column.USER, "Test User");
        values.put(YambaConstants.Column.MESSAGE, "My tweet.");
        values.put(YambaConstants.Column.CREATED_AT, new Date().getTime());

        Uri itemUri = getMockContentResolver().insert(YambaConstants.CONTENT_URI, values);

        assertNotNull(itemUri);

        Cursor cursor = getMockContentResolver().query(itemUri, null, null, null, null);
        assertEquals(1, cursor.getCount());

        // move to first row
        cursor.moveToNext();
        assertEquals("My tweet.", cursor.getString(cursor.getColumnIndex(YambaConstants.Column.MESSAGE)));
    }

    public void testQuery() {

        Uri uri = Uri.withAppendedPath(YambaConstants.CONTENT_URI, "");

        Cursor cursor = getMockContentResolver().query(uri, null, null, null, null);
        assertEquals(1, cursor.getCount());

        // move to first row
        cursor.moveToNext();
        assertEquals("My tweet.", cursor.getString(cursor.getColumnIndex(YambaConstants.Column.MESSAGE)));
    }


    public void testUpdateItem() {

        ContentValues values = new ContentValues();

        values.put(YambaConstants.Column.ID, 200);
        values.put(YambaConstants.Column.USER, "Test User");
        values.put(YambaConstants.Column.MESSAGE, "My tweet.");
        values.put(YambaConstants.Column.CREATED_AT, new Date().getTime());

        Uri itemUri = getMockContentResolver().insert(YambaConstants.CONTENT_URI, values);

        assertNotNull(itemUri);

        Cursor cursor = getMockContentResolver().query(itemUri, null, null, null, null);
        assertEquals(1, cursor.getCount());

        // move to first row
        cursor.moveToNext();
        assertEquals("My tweet.", cursor.getString(cursor.getColumnIndex(YambaConstants.Column.MESSAGE)));

        ContentValues updateValues = new ContentValues();

        //updateValues.put(YambaConstants.Column.ID, 200);
        //updateValues.put(YambaConstants.Column.USER, "Test User");
        updateValues.put(YambaConstants.Column.MESSAGE, "My tweet - updated.");
        //updateValues.put(YambaConstants.Column.CREATED_AT, new Date().getTime());

        int count = getMockContentResolver().update(itemUri, updateValues, null, null);

        cursor = getMockContentResolver().query(itemUri, null, null, null, null);
        assertEquals(1, cursor.getCount());

        // move to first row
        cursor.moveToNext();
        assertEquals("My tweet - updated.", cursor.getString(cursor.getColumnIndex(YambaConstants.Column.MESSAGE)));

    }


}



