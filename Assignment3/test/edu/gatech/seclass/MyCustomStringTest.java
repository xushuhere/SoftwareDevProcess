package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    // This test checks whether the method countNumbers suitably count numbers of
    // the complicated string (with number, letters, space, and other symbols).
    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(7, mycustomstring.countNumbers());
    }

    // This test checks whether the method countNumbers suitably count the numbers of
    //the non-number string.
    @Test
    public void testCountNumbers2() {
        mycustomstring.setString("I'd better put no digits in this string, right?");
        assertEquals(0, mycustomstring.countNumbers());
    }

    // This test checks whether the method countNumbers suitably count the numbers of
    // the string containing numbers or space (no letters).
    @Test
    public void testCountNumbers3() {
        mycustomstring.setString("14 134 567");
        assertEquals("This is not right", 3, mycustomstring.countNumbers());
    }

    // This test checks whether method countNumbers suitably throwing the exception
    // when a null string is given in the test.
    @Test(expected = NullPointerException.class)
    public void testCountNumbers4() {
        mycustomstring.setString(null);
        mycustomstring.countNumbers();
    }

    // This test checks whether method countNumbers suitably throwing the exception
    // when non string is given in the test (MyCustomString will give a null).
    @Test(expected = NullPointerException.class)
    public void testCountNumbers5() {
        mycustomstring.countNumbers();
    }

    // This test checks whether method countNumbers suitably give the expected value 0
    // when an empty string is given in the test.
    @Test
    public void testCountNumbers6() {
        mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }


    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give the right
    // answer for a complicated string with spaces, numbers, letters and symbols with the from the start
    // order.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("d33p md1  i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give the right
    // answer for a complicated string with spaces, numbers, letters and symbols with the from the end
    // order.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("'bt t0 6snh r6rh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably throwing the
    // NullPointerException when 'null' is given in the test.
    @Test(expected = NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd3() {
        mycustomstring.setString(null);
        mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true);
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give the empty string
    // when the n number is greater than the length of the given string.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd4() {
        mycustomstring.setString("This is a test");
        assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(30, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give the empty string
    // when an empty string is given to the method.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd5() {
        mycustomstring.setString("");
        assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(30, false));
    }


    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably throwing
    // the IllegalArgumentException when the n number is zero.
    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd6() {
        mycustomstring.setString("This is a test");
        mycustomstring.getEveryNthCharacterFromBeginningOrEnd(0, true);
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably throwing
    // the IllegalArgumentException when the n number is less than zero.
    @Test(expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd7() {
        mycustomstring.setString("This is a test");
        mycustomstring.getEveryNthCharacterFromBeginningOrEnd(-2, true);
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected result when a simple string is given and only one letter should be returned
    // with the from the start from the left selection.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd8() {
        mycustomstring.setString("I'd b");
        assertEquals("d", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected result when a simple string is given and only one symbol should be returned
    // with the from the end selection.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd9() {
        mycustomstring.setString("I'd b");
        assertEquals("d", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected result when a simple string is given and only one symbol should be returned
    // with the from the end selection.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd10() {
        mycustomstring.setString("I'd b");
        assertEquals("d", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected result when a simple string is given and n = 1 with the from the end selection.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd11() {
        mycustomstring.setString("I'd ba");
        assertEquals("I'd ba", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, true));
    }

    // This test checks whether the method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected first char from the string for a string having a length of 10,  n = 2,
    // the start from the end selection.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd12() {
        mycustomstring.setString("I'd better");
        assertEquals("Idbte", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, true));
    }


    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the right end string when a simple string is given and n = 2 is given.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd13() {
        mycustomstring.setString("I'd ba");
        assertEquals("' a", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected repeated space symbols string when a string of "A a A a A a"is given, n = 2
    // and from the start is chosen.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd14() {
        mycustomstring.setString("A a A a A a");
        assertEquals("     ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected string when all number string is given, n = 2 and from the start is chosen.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd15() {
        mycustomstring.setString("12345678901234567890");
        assertEquals("2468024680", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected string when all number string is given, n = 2 and from the end is chosen.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd16() {
        mycustomstring.setString("12345678901234567890");
        assertEquals("1357913579", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, true));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected string when all letter string is given, n = 2 and from the start is chosen.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd17() {
        mycustomstring.setString("abcdefghabcdefghabcdefghabcdefgh");
        assertEquals("hhhh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(8, false));
    }

    // This test checks whether method getEveryNthCharacterFromBeginningOrEnd suitably give
    // the expected string when all letter string is given, n = 2 and from the end is chosen.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd18() {
        mycustomstring.setString("abcdefghabcdefghabcdefghabcdefgh");
        assertEquals("aaaa", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(8, true));
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably give
    // the expected string when the startPosition is a digit and the endPosition is a digit.
    @Test
    public void testConvertDigitsToNamesInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably give
    // the expected string when the startPosition is a digit and the endPosition is a space " ".
    @Test
    public void testConvertDigitsToNamesInSubstring2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 20);
        assertEquals("I'd b3tt3r put sZerome d161ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably give
    // the expected string when the startPosition is a digit and the endPosition is a comma ",".
    @Test
    public void testConvertDigitsToNamesInSubstring3() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 42);
        assertEquals("I'd b3tt3r put sZerome dOneSixOnets in this FivetrOnenSix, right?", mycustomstring.getString());
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably give
    // the expected string when the startPosition is a letter and the endPosition is a digit.
    @Test
    public void testConvertDigitsToNamesInSubstring4() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(16, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably throw exception of
    //  NullPointerException when the string is null.
    @Test(expected = NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring5() {
        mycustomstring.setString(null);
        mycustomstring.convertDigitsToNamesInSubstring(1, 2);
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably throw exception of
    //  MyIndexOutOfBoundsException when the startPosition is out of boundary.
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring6() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(0, 16);
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably throw exception of
    //  MyIndexOutOfBoundsException when the endPosition is out of boundary.
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring7() {
        // the test for endPoint out of bounds
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(15, 50);

    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably throw exception of
    //  IllegalArgumentException when the endPosition is smaller than the startPosition.
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring8() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(15,12);
    }


    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably give
    // the expected string when the startPosition and the endPosition are the same.
    public void testConvertDigitsToNamesInSubstring9() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 17);
        assertEquals("I'd b3tt3r put sZerome d161ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    // This test checks whether method testConvertDigitsToNamesInSubstring1 suitably give
    // the expected string when no conversion is supposed to do in the string.
    public void testConvertDigitsToNamesInSubstring10() {
        mycustomstring.setString("I'd better put some digits in this string, right?");
        mycustomstring.convertDigitsToNamesInSubstring(1, 49);
        assertEquals("I'd better put some digits in this string, right?", mycustomstring.getString());
    }

}
