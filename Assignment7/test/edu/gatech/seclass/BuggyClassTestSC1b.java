package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Shu Xu on 11/2/16.
 */
public class BuggyClassTestSC1b {
// less than 50% statement coverage but reveal the fault

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
            //(expected = ArithmeticException.class)
    public void testBuggyMethod1() throws Exception {
        BuggyClass bc = new BuggyClass();
        int x = bc.buggyMethod1(2, 4);
        assertEquals(0, x);

    }

}