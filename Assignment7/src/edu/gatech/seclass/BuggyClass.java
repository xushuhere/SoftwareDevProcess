package edu.gatech.seclass;

/**
 * Created by Shu Xu on 11/2/16.
 */

public class BuggyClass {
    private int x;
    private int y;


    public int buggyMethod1(int x, int y) {
        //  division by zero fault
        // SC1a: 100% statement coverage not reveal the fault
        // SC1b: less than 50% statement coverage and reveal the fault
            x = y / (x - 2) ;
        if (x > y){
            x = x / y;
            x = x + 1;
            x = x + y;
        }
        return x;
    }

    public int buggyMethod2(int x, int y) {
        //  division by zero fault
        // SC2: 100% statement coverage not reveal the fault
        // BC2: more than 50% branch coverage and reveal the fault

        if (y != 0) {
            x = x + 1;
        }
        return x/y;
    }


    public int buggyMethod3(int x, int y) {
        // required: cotain division by zero fault
        // 1) 100% branch coverage, and not reveal the fault
        // 2) 100% statement coverage, less than 100% branch coverage, and reveals the fault

         if (x > y) {
            x = x * y;
        }

        return x/y;
    }

    public void buggyMethod4() {
        // requirement:
        //  a division by zero fault such that
        // (1) every test suite that achieves 100% statement coverage reveals the fault
        // (2) it is possible to create a test suite that achieves 100% branch coverage
        // and does not reveal the fault.

        // My answer:
        // impossible.
        // A test suite that achieves 100% branch coverage the same test suite will
        // also achieve 100% statement coverage. So if requirement 2 is satisfied, the fault will not be revealed.
        // Then it is not met the requirement 1 (EVERY test achieves 100% statement coverage reveals the fault).
        // So it is impossible to generate such a method.

    }


    public void buggyMethod5(int i) {
            //  1. public void buggyMethod5 (int i) {
            //  2.   int x;
            //  3.   [point where you can add code]
            //  4.   x = i/0;
            //  5.   [point where you can add code]
            //  6. }


        // My answer:
        // impossible.
        // i is declared as integer at the beginning
        // x is declared as integer in line 1
        // when 100% statement covered, x = i/0 should be executed.
        // In java, when i is an integer, i/0 will throw the error.
        // To get x = i/0 executed, "i" need to be float and "x" need to be set as float as well.
        // while in java, we can not change the variable to other type
        // thus this Method is impossible to generate

    }
}
