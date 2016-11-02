package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by shuxu on 11/2/16.
 */
public class BuggyClassTestBC3 {
    // 100% branch coverage, not reveal the fault

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testBuggyMethod3_1() throws Exception {
        BuggyClass bc1 = new BuggyClass();
        int x = bc1.buggyMethod3(3, 4);
        assertEquals(0, x);

        BuggyClass bc2 = new BuggyClass();
        int y = bc2.buggyMethod3(4, 4);
        assertEquals(1, y);

        BuggyClass bc3 = new BuggyClass();
        int z = bc3.buggyMethod3(6, 4);
        assertEquals(6, z);
    }

}