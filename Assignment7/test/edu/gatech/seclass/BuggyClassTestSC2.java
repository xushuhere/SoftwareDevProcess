package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by shuxu on 11/2/16.
 */
public class BuggyClassTestSC2 {
    //  division by zero fault
    // SC2: 100% statement coverage not reveal the fault
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testBuggyMethod2_1() throws Exception {
        BuggyClass bc = new BuggyClass();
        int x = bc.buggyMethod2(3, 4);
        assertEquals(1, x);
    }

    @Test
    public void testBuggyMethod2_2() throws Exception {
        BuggyClass bc = new BuggyClass();
        int x = bc.buggyMethod2(6, 4);
        assertEquals(1, x);
    }
}