package edu.gatech.seclass.replace;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MyMainTest {
    @org.junit.Test
    public void main() throws Exception {
    }

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    // Some utilities
    private File myCreateTmpFile() throws IOException {
        File tmpFile = temporaryFolder.newFile();
        tmpFile.deleteOnExit();
        return tmpFile;
    }
    private File myCreateInputFile1() throws Exception {
        File file1 =  myCreateTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!");

        fileWriter.close();
        return file1;
    }
    private File myCreateInputFile2() throws Exception {
        File file1 =  myCreateTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice");

        fileWriter.close();
        return file1;
    }
    private File myCreateInputFile3() throws Exception {
        File file1 =  myCreateTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123");

        fileWriter.close();
        return file1;
    }
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    // My test cases
    @Test
    public void myMainTest01() throws Exception {
        //Test Case 1  		<error>
        //Options Size :  Improper Value
        File inputFile1 = myCreateInputFile1();
        String args[] = {"-x","Howdy1", "Hello", "--", inputFile1.getPath(),};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest02() throws Exception {
        //Test Case 2  		<error>
        //From String Size :  Empty
        File inputFile1 = myCreateInputFile1();
        String args[] = {"-b","", "Hello", "--",inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest03() throws Exception {
        //    Test Case 3  		<error>
        //    From String Quoting :  Quoted empty
        //  Note: command line is processing the quoting there.
        File inputFile2 = myCreateInputFile1();
        String args[] = {"-b","", "Hello", "--",inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest04() throws Exception {
        // New test case <error>, no inputFile
        File inputFile = myCreateInputFile2();
        inputFile.delete();
        String args[] = {"ab", "bd", "--", inputFile.getPath()};
        Main.main(args);
        assertEquals("File " + inputFile.getName() + " not found", errStream.toString().trim());
    }

    @Test
    public void myMainTest05() throws Exception {
        //    Test Case 7  		<error>
        //    File(s) Size :  0
        String args[] = {"-b","''", "Hello", "--" };
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest06() throws Exception {
        // Test Case 11 		(Key = 1.0.2.1.2.1.2.2.1.1.)
        //     Options Size          :  Empty
        //     Number of Options     :  <n/a>
        //     From String Size      :  Not Empty
        //     To String Size        :  Not Empty
        //     File(s) Size          :  1
        //     File(s) Presence      :  File(s) Present
        //     File(s) Accessibility :  File(s) Readable and Writable
        //     Number of Replacement :  0
        // test a case option is not given and one input file, and no replacement occurred after the command
        File inputFile1 = myCreateInputFile1();
        String args[] = {"Howdy1", "Hello", "--", inputFile1.getPath(), };
        Main.main(args);
        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest07() throws Exception {
        // Test frame # 12
        // test a case  option is not given and one input file and multiple replacements (2 in this test) in the file
        File inputFile1 = myCreateInputFile1();

        String args[] = {"test", "Test", "--", inputFile1.getPath(), };
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a Test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting Test cases...\n" +
                "And let's say \"howdy bill\" again!";


        String actual1 = getFileContent(inputFile1.getPath());
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

        assertEquals("The files differ!", expected1, actual1);

    }

    @Test
    public void myMainTest08() throws Exception {
        // Test frame # 13
        // test a case option is not given, and multiple input files, and no replacement occurred after the command
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"Howdy1", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest09() throws Exception {
        // Test frame # 14
        // test a case option is not given, and multiple input files, and multiple replacements occurred after the command
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"Howdy", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest10() throws Exception {
    // Test frame # 15
    // test a case option is not given, and one input file, and one replacement occurred after the command
    //    Test Case 15 		(Key = 1.0.2.1.2.2.2.2.1.1.)
    //    Options Size          :  Empty
    //    Number of Options     :  <n/a>
    //    From String Size      :  Not Empty
    //    To String Size        :  Not Empty
    //    File(s) Size          :  1
    //    File(s) Presence      :  File(s) Present
    //    File(s) Accessibility :  File(s) Readable and Writable
    //    Number of Replacement :  0
        File inputFile = myCreateInputFile1();

        String args[] = {"Howdy1", "'Hello'", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void myMainTest11() throws Exception {
        //Test frame # 16
        //    Test Case 16 		(Key = 1.0.2.1.2.2.2.2.1.2.)
        //    Options Size          :  Empty
        //    Number of Options     :  <n/a>
        //    From String Size      :  Not Empty
        //    To String Size        :  Not Empty
        //    File(s) Size          :  1
        //    File(s) Presence      :  File(s) Present
        //    File(s) Accessibility :  File(s) Readable and Writable
        //    Number of Replacement :  >=1
        // test a case option is not given, and one input file, and multiple replacements occurred after the command
        File inputFile = myCreateInputFile1();
        String args[] = {"Howdy", "Hello", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void myMainTest12() throws Exception {
        // Test frame # 17
        //    Test Case 17 		(Key = 1.0.2.1.2.2.3.2.1.1.)
        //    Options Size          :  Empty
        //    Number of Options     :  <n/a>
        //    From String Size      :  Not Empty
        //    To String Size        :  Not Empty
        //    File(s) Size          :  >1
        //    File(s) Presence      :  File(s) Present
        //    File(s) Accessibility :  File(s) Readable and Writable
        //    Number of Replacement :  0

        // test a case option is not given, and multiple input files, and no replacement occurred after the command
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"Howdy1", "'Hello'", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest13() throws Exception {
        // Test frame #18
        // test a case option is not given, and multiple input files, and multiple replacements occurred after the command
        // Test Case 18 		(Key = 1.0.2.1.2.2.3.2.1.2.)
        //   Options Size          :  Empty
        //   Number of Options     :  -b
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"-b","Howdy", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest14() throws Exception {
        // test a case option is not given, and one input file, and no replacements occurred after the command
        // Test Case 19 		(Key = 1.0.2.2.2.1.2.2.1.1.)
        //   Options Size          :  Empty
        //   Number of Options     :  <n/a>
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"Howdy1", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest15() throws Exception {
        // test a case option is not given, and input string quoted, one input file, and multiple replacements occurred after the commands
        // when "--" is give, the "-a" and "-b" is forced to assign for the from String.
        File inputFile2 = myCreateInputFile2();
        String args1[] = {"--","-a", "-1", "--", inputFile2.getPath()};
        Main.main(args1);
        String args2[] = {"--","-b", "-2", "--", inputFile2.getPath()};
        Main.main(args2);

        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-1) Item 1\n" +
                "-2) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";


        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest16() throws Exception {
        // test a case option is not given, and input string quoted, two input files, and no replacements occurred after the command
        // (e.g., Testing that the -f and -l parameters work when used together)>
        // Test Case 21 		(Key = 1.0.2.2.2.1.3.2.1.1.)
        //   Options Size          :  Empty
        //   Number of Options     :  <n/a>
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"'Howdy1'", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest17() throws Exception {
        //
        //
    File inputFile1 = myCreateInputFile1();
    File inputFile2 = myCreateInputFile2();
    String args[] = {"-f","Howdy", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
    Main.main(args);

    String expected1 = "Hello Bill,\n" +
            "This is a test file for the replace utility\n" +
            "Let's make sure it has at least a few lines\n" +
            "so that we can create some interesting test cases...\n" +
            "And let's say \"howdy bill\" again!";

    String expected2 = "Hello Bill,\n" +
            "This is another test file for the replace utility\n" +
            "that contains a list:\n" +
            "-a) Item 1\n" +
            "-b) Item 2\n" +
            "...\n" +
            "and says \"howdy Bill\" twice";

    String actual1 = getFileContent(inputFile1.getPath());
    String actual2 = getFileContent(inputFile2.getPath());

    assertEquals("The files differ!", expected1, actual1);
    assertEquals("The files differ!", expected2, actual2);

    assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
}

    @Test
    public void myMainTest18() throws Exception {
        //Test Case 23 		(Key = 1.0.2.2.2.2.2.2.1.1.)
        //   Options Size          :  Empty
        //   Number of Options     :  <n/a>
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0

        File inputFile1 = myCreateInputFile1();
        String args[] = {"'Howdy1'", "'Hello'", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest19() throws Exception {
        //Test repeated option flags
        // args Replace -f -f Howdy Hello -- fileName
        File inputFile2 = myCreateInputFile2();
        String args[] = {"-f","-f","Howdy", "Hello", "--",inputFile2.getPath()};
        Main.main(args);

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest20() throws Exception {
        //   added "--" before from String, no replacement is done
        // cmd: -- Howdy1 Hello -- file1 file2

        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"--","Howdy1", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest21() throws Exception {
        //Test three options were given, two files
        // two options, replacements in both files
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        String args[] = {"-f","-i",  "Howdy", "Hello", "--", inputFile1.getPath(),inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest22() throws Exception {
        //Test Case 27 		(Key = 2.1.2.1.2.1.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        File inputFile1 = myCreateInputFile1();
        String args[] = {"-b","Howdy1", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

        assertEquals("The files differ!", expected1, actual1);

    }

    @Test
    public void myMainTest23() throws Exception {
        //Test Case 28 		(Key = 2.1.2.1.2.1.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
    File inputFile1 = myCreateInputFile1();
    String args[] = {"-i","--","Howdy", "Hello", "--", inputFile1.getPath()};
    Main.main(args);

    String expected1 = "Hello Bill,\n" +
            "This is a test file for the replace utility\n" +
            "Let's make sure it has at least a few lines\n" +
            "so that we can create some interesting test cases...\n" +
            "And let's say \"Hello bill\" again!";
    String actual1 = getFileContent(inputFile1.getPath());

    assertEquals("The files differ!", expected1, actual1);

    assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
}

    @Test
    public void myMainTest24() throws Exception {
    //Test Case 29 		(Key = 2.1.2.1.2.1.3.2.1.1.)
    //   Options Size          :  Valid Input
    //   Number of Options     :  1(eg -b)
    //   From String Size      :  Not Empty
    //   To String Size        :  Not Empty
    //   File(s) Size          :  >1
    //   File(s) Presence      :  File(s) Not Present
    //   File(s) Accessibility :  File(s) Readable and Writable
    //   Number of Replacement :  0
    File inputFile1 = myCreateInputFile1();
    File inputFile2 = myCreateInputFile2();
    File inputFile3 = myCreateInputFile3();

    String args[] = {"-b", "Howdy1", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
    Main.main(args);

    String expected1 = "Howdy Bill,\n" +
            "This is a test file for the replace utility\n" +
            "Let's make sure it has at least a few lines\n" +
            "so that we can create some interesting test cases...\n" +
            "And let's say \"howdy bill\" again!";
    String expected2 = "Howdy Bill,\n" +
            "This is another test file for the replace utility\n" +
            "that contains a list:\n" +
            "-a) Item 1\n" +
            "-b) Item 2\n" +
            "...\n" +
            "and says \"howdy Bill\" twice";
    String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
            "It is important to know your abc and 123," +
            "so you should study it\n" +
            "and then repeat with me: abc and 123";

    String actual1 = getFileContent(inputFile1.getPath());
    String actual2 = getFileContent(inputFile2.getPath());
    String actual3 = getFileContent(inputFile3.getPath());

    assertEquals("The files differ!", expected1, actual1);
    assertEquals("The files differ!", expected2, actual2);
    assertEquals("The files differ!", expected3, actual3);

    assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
}

    @Test
    public void myMainTest25() throws Exception {
        //Test Case 30 		(Key = 2.1.2.1.2.1.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest26() throws Exception {
        //Test Case 31 		(Key = 2.1.2.1.2.2.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-b", "Howdy1", "'Hello'", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest27() throws Exception {
        //duplicated -i input, 2 replacemnt in the file
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "-i","--","Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";


        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest28() throws Exception {
        //Test Case 33 		(Key = 2.1.2.1.2.2.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-b", "Howdy1", "'Hello'", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest29() throws Exception {
        //Test Case 34 		(Key = 2.1.2.1.2.2.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-b", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest30() throws Exception {
        //Test Case 35 		(Key = 2.1.2.2.2.1.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "Howdy1", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest31() throws Exception {
        //Test Case 36 		(Key = 2.1.2.2.2.1.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile2 = myCreateInputFile2();

        String args[] = {"-b", "-f","Bill", "Will", "--", inputFile2.getPath()};
        Main.main(args);


        String expected2 = "Howdy Will,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";


        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected2, actual2);

        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void myMainTest32() throws Exception {
        //Test Case 37 		(Key = 2.1.2.2.2.1.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -i)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "'Howdy1'", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest33() throws Exception {
        //Test Case 38 		(Key = 2.1.2.2.2.1.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "Howdy ", "Hello ", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest34() throws Exception {
        //Test Case 39 		(Key = 2.1.2.2.2.2.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "Howdy1", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest35() throws Exception {
        //Test Case 40 		(Key = 2.1.2.2.2.2.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-f", "--", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";


        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest36() throws Exception {
        //Test Case 41 		(Key = 2.1.2.2.2.2.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "Howdy1", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest37() throws Exception {
        //Test Case 42 		(Key = 2.1.2.2.2.2.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  1(eg -b)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","--", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest38() throws Exception {
        //Test Case 43 		(Key = 2.2.2.1.2.1.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-b", "-i", "Howdy1", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest39() throws Exception {
        //Test Case 44 		(Key = 2.2.2.1.2.1.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1

        File inputFile1 = myCreateInputFile1();

        String args[] = {"-b", "-i", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest40() throws Exception {
        //Test Case 45 		(Key = 2.2.2.1.2.1.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-l", "Howdy1", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest41() throws Exception {
        //Test Case 46 		(Key = 2.2.2.1.2.1.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "-f", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest42() throws Exception {
        //Test Case 47 		(Key = 2.2.2.1.2.2.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-b", "-i", "Howdy1", "'Hello'", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";


        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest43() throws Exception {
        //Test Case 48 		(Key = 2.2.2.1.2.2.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "-b", "-f", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest44() throws Exception {
        //Test Case 49 		(Key = 2.2.2.1.2.2.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-b", "Howdy1", "'Hello'", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest45() throws Exception {
        //Test Case 50 		(Key = 2.2.2.1.2.2.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "-f","--", "Howdy", "Hello","--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest46() throws Exception {
        //Test Case 51 		(Key = 2.2.2.2.2.1.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i","-b", "'Howdy1'", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest47() throws Exception {
        //Test Case 52 		(Key = 2.2.2.2.2.1.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1

        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "-f","--","Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";


        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest48() throws Exception {
        //Test Case 53 		(Key = 2.2.2.2.2.1.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i", "-b", "'Howdy1'", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest49() throws Exception {
        //Test Case 54 		(Key = 2.2.2.2.2.1.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-b", "-i",  "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest50() throws Exception {
        //Test Case 55 		(Key = 2.2.2.2.2.2.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-f", "-l", "Howdy1", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest51() throws Exception {
        //   Options Size          :  Valid Input
        //   Number of Options     :  -i -f
        //   From String Size      :  Not Empty
        //   To String Size        :  Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "-f", "Howdy", "", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = " Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest52() throws Exception {
        //Test Case 57 		(Key = 2.2.2.2.2.2.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Quoting     :  Quoted
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-f", "-l", "'Howdy1'", "'Hello'", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest53() throws Exception {
        //Test Case 58 		(Key = 2.2.2.2.2.2.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  2(eg -b -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-b", "-f", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest54() throws Exception {
        //Test Case 59 		(Key = 2.3.2.1.2.1.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i", "-f","-l", "Howdy1", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    @Test
    public void myMainTest55() throws Exception {
        //Test Case 60 		(Key = 2.3.2.1.2.1.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i","-f","-l", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest56() throws Exception {
        //Test Case 61 		(Key = 2.3.2.1.2.1.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-b", "-f", "Howdy1", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest57() throws Exception {
        //Test Case 62 		(Key = 2.3.2.1.2.1.3.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-f", "--","Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(),inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest58() throws Exception {
        //Test Case 63 		(Key = 2.3.2.1.2.2.2.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //
        File inputFile1 = myCreateInputFile1();

        String args[] = {"-i","-b","-f", "Howdy1", "'Hello'", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    @Test
    public void myMainTest59() throws Exception {
        //Test Case 64 		(Key = 2.3.2.1.2.2.2.2.1.2.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  >=1
        //
        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-f","-l","-b","Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";

        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void myMainTest60() throws Exception {
        //Test Case 65 		(Key = 2.3.2.1.2.2.3.2.1.1.)
        //   Options Size          :  Valid Input
        //   Number of Options     :  3(eg -b -f -l)
        //   From String Size      :  Not Empty
        //   To String Size        :  Not Empty
        //   File(s) Size          :  >1
        //   File(s) Presence      :  File(s) Present
        //   File(s) Accessibility :  File(s) Readable and Writable
        //   Number of Replacement :  0
        //

        File inputFile1 = myCreateInputFile1();
        File inputFile2 = myCreateInputFile2();
        File inputFile3 = myCreateInputFile3();

        String args[] = {"-i","-b","-l", "Howdy1", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

}