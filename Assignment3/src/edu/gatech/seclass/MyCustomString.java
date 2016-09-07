package edu.gatech.seclass;

/**
 * Created by Shu Xu on 9/3/16.
 */

/**
 * MyString class that implements the MyStringInterface
 */
public class MyCustomString implements MyCustomStringInterface {

    //The exception messages
    private static final String NULL_STRING_EXCEPTION = "Current value of string is Null";
    private static final String Illegal_Argument_Exception = "Input argument is illegal";
    private static final String My_Index_Out_Of_Bounds_Exception = "Input index is out of the bounds ";

    // set the current string variable as currentString
    private String currentString;

    /**
     * Sets the value of the current string.
     *
     * @param 'string' The value to be set
     */
    public void setString(String str) {
        currentString = str;
    }

    /**
     * Returns the current string. If the string is null, it should return null.
     *
     * @return Current string
     */
    public String getString() {
        return currentString;
    }


    /**
     * Returns the number of numbers in the current string, where a number is defined as a
     * contiguous sequence of digits.
     *
     * If the current string is empty, the method should return 0.
     *
     * Examples:
     * - countNumbers would return 2 for string "My numbers are 11 and 96".
     *
     * @return Number of numbers in the current string
     * @throws NullPointerException If the current string is null
     */
    public int countNumbers() {
        Integer returnNum = 0;
        Boolean newNum = true;

        //if the string value is null then return NullPointerException
        if (currentString == null) {
            throw new NullPointerException(NULL_STRING_EXCEPTION);
        }
        //If the string is empty then do nothing
        else if (currentString.length() > 0) {
            char[] currentCharArray = currentString.toCharArray();
            for (int i = 0; i < currentString.length(); i++) {
                String currentChar = String.valueOf(currentCharArray[i]);
                if (currentChar.equals("0") || currentChar.equals("1")
                        || currentChar.equals("2") || currentChar.equals("3")
                        || currentChar.equals("4") || currentChar.equals("5")
                        || currentChar.equals("6") || currentChar.equals("7")
                        || currentChar.equals("8") || currentChar.equals("9")) {
                    if (newNum) {
                        returnNum = returnNum + 1;
                        newNum = false;
                    }
                } else {
                    newNum = true;
                }
            }
        }
        return returnNum;
    }

    /**
     * Returns a string that consists of all and only the characters in positions n, 2n, 3n, and
     * so on in the current string, starting either from the beginning or from the end of the
     * string. The characters in the resulting string should be in the same order and with the
     * same case as in the current string.
     *
     * If the current string is empty or has less than n characters, the method should return an
     * empty string.
     *
     * Examples:
     * - For n=2 and a string of one character, the method would return an empty string.
     * - For n=2 and startFromEnd=false, the method would return the 2nd, 4th, 6th, and so on
     *   characters in the string.
     * - For n=3 and startFromEnd=true, the method would return the 3rd from the last character
     *   in the string, 6th from the last character in the string, and so on.
     *
     * Values n and startFromEnd are passed as parameters. The starting character, whether it is
     * the first one or the last one in the string, is considered to be in Position 1.
     *
     * @param n Determines the positions of the characters to be returned
     * @return String made of characters at positions n, 2n, and so on in the current string
     * @throws IllegalArgumentException If "n" less than or equal to zero
     * @throws NullPointerException If the current string is null and "n" is greater than zero
     *
     */
    public String getEveryNthCharacterFromBeginningOrEnd(int n, boolean startFromEnd) {
        String resString = "";
        if (n <= 0){
            throw new IllegalArgumentException(Illegal_Argument_Exception);
        }
        else if (currentString == null) {
            throw new NullPointerException(NULL_STRING_EXCEPTION);
        }
        else if (currentString.length() > 0) {
            char[] currentCharArray = currentString.toCharArray();
            int length = currentString.length();
            if (!startFromEnd){
                for (int i = n; i < length + 1; i+= n) {
                    resString += Character.toString(currentCharArray[i-1]);
                }
            }
            else {
                for (int i = length + 1 - n; i >= 1 ; i-= n) {
                    resString = Character.toString(currentCharArray[i-1])
                            + resString;
                }
            }
        }
        return resString;

    }

    /**
     * Replace the individual digits in the current string, between startPosition and endPosition
     * (included), with the corresponding English names of those digits, with the first letter
     * capitalized. The first character in the string is considered to be in Position 1.
     * Unlike for the previous method, digits are converted individually, even if contiguous.
     *
     * Examples:
     * - 460 would be converted to FourSixZero
     * - 416 would be converted to FourOneSix
     *
     * @param startPosition Position of the first character to consider
     * @param endPosition   Position of the last character to consider
     * @throws IllegalArgumentException    If "startPosition" > "endPosition"
     * @throws MyIndexOutOfBoundsException If "startPosition" <= "endPosition", but either
     *                                     "startPosition" or "endPosition" are out of
     *                                     bounds (i.e., either less than 1 or greater than the
     *                                     length of the string)
     * @throws NullPointerException        If "startPosition" <= "endPosition", "startPosition" and
     *                                     "endPosition" are greater than 0, and the current
     *                                     string is null
     */
    public  void convertDigitsToNamesInSubstring(int startPosition, int endPosition){

        if (startPosition > endPosition){
            throw new IllegalArgumentException(Illegal_Argument_Exception);
        }
        else if (startPosition < 1 || endPosition > currentString.length() ){
            throw new MyIndexOutOfBoundsException(My_Index_Out_Of_Bounds_Exception);

        }
        else if (startPosition <= endPosition && startPosition > 0 && currentString == null) {
            throw new NullPointerException(NULL_STRING_EXCEPTION);
        }
        else{
            String leftString = currentString.substring(0, startPosition-1);
            String workString = currentString.substring(startPosition-1, endPosition);
            String rightString = currentString.substring(endPosition,currentString.length());
            workString = workString.replaceAll("1", "One");
            workString = workString.replaceAll("2", "Two");
            workString = workString.replaceAll("3", "Three");
            workString = workString.replaceAll("4", "Four");
            workString = workString.replaceAll("5", "Five");
            workString = workString.replaceAll("6", "Six");
            workString = workString.replaceAll("7", "Seven");
            workString = workString.replaceAll("8", "Eight");
            workString = workString.replaceAll("9", "Nine");
            workString = workString.replaceAll("0", "Zero");
            this.currentString = leftString + workString + rightString;;
            }
        }

}