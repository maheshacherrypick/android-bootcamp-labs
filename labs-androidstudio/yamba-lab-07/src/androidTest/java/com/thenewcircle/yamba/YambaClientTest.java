package com.thenewcircle.yamba;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.thenewcircle.yamba.client.YambaClient;
import com.thenewcircle.yamba.client.YambaClientException;
import com.thenewcircle.yamba.client.YambaStatus;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class YambaClientTest extends ApplicationTestCase<Application> {
    public YambaClientTest() {
        super(Application.class);
    }

    public void testGetTimeline() throws YambaClientException {

        // create the object under test
        YambaClient client = new YambaClient("student", "password");

        // run the method under test
        // get back the actual results
        List<YambaStatus> timeline = client.getTimeline(20);

        // get back the actual results
        int actualCount = timeline.size();

        // compare to the expected results
        assertEquals(actualCount, 20);
    }

}