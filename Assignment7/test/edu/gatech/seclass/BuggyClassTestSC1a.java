package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Shu Xu on 11/2/16.
 */
public class BuggyClassTestSC1a {
    // 100% statement coverage not reveal the fault

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testBuggyMethod1() throws Exception {
        BuggyClass bc = new BuggyClass();
        int x = bc.buggyMethod1(3, 4);
        assertEquals(9, x);
    }
}