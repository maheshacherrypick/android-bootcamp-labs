package com.thenewcircle.yamba;

import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<YambaApplication> {

    public ApplicationTest() {
        super(YambaApplication.class);
    }

    public void testYambaApplication() {
        String testValue = YambaApplication.getTestValue();
        assertEquals("test", testValue);
    }
}