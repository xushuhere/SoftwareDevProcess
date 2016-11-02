package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shuxu on 11/2/16.
 */
public class BuggyClassTestSC3 {
    // 100% statement coverage, less than 100% branch coverage, and reveal the fault

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBuggyMethod3_4() throws Exception {
        BuggyClass bc = new BuggyClass();
        bc.buggyMethod3(1, 0);
    }


}