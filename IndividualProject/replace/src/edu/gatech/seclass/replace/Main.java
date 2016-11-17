package edu.gatech.seclass.replace;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    //constructors

    public static void main(String[] args) {
        //variables for replacement
        boolean vf = false;
        boolean vb = false;
        boolean vl = false;
        boolean vi = false;
        List<String> fileList = new ArrayList<>();

        String fromStr = null;
        String toStr = null;
        String arg;
        int pointer = 0;
        InputStream inStream;
        OutputStream outStream;
        // pinter
        // 0 for options, 1 for fromString (forced),
        // 2 for to String, 3 for "--", 4 and beyond for fileList

        //String[] pointer = {"option", "fromString", "toString","--" ,"fileName"};

        try {
            for (int i = 0; i < args.length;i++ ) {
                arg = args[i];
                if (pointer == 0) {
                    if (arg.equals("-f") && !vf) {vf = true;}
                    if (arg.equals("-l") && !vb) {vb = true;}
                    if (arg.equals("-i") && !vi) {vi = true;}
                    if (arg.equals("-b") && !vb) {vb = true;}
                    if (arg.equals("--")) {pointer += 1;}
                    else if (arg.startsWith("-")) {usage();return;}
                    else {fromStr = arg;pointer += 2;}
                }
                if (pointer == 1) {fromStr = arg;pointer += 1;}
                if (pointer == 2) {toStr = arg;pointer += 1;}
                if (pointer == 3 && arg.equals("--")) {pointer += 1;}
                if (pointer == 3 && !arg.equals("--")) {usage();return;}
                if (pointer == 4) {fileList.add(arg);}
            }
        }
        catch (Exception e) {usage();return;}



        if (fileList.size() == 0) {
            usage();
            return;
        }


        //backup files before any operations
        if (vb) {
            for (int x = 0; x < fileList.size(); x++) {
                //InputStream inStream;
                //OutputStream outStream;
                String fileName = fileList.get(x);

                try {
                    File fromFile = new File(fileName);
                    File toFile = new File(fileName + ".bck");
                    inStream = new FileInputStream(fromFile);
                    outStream = new FileOutputStream(toFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    //copy the file content in bytes
                    while ((length = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }
                    inStream.close();
                    outStream.close();
                } catch (Exception e) {
                    usage();
                }

            }
        }


        fromStr = "(?i)Howdy";
        toStr = "hello";

        for (int k = 0; k < fileList.size(); k++) {
            try {
                String fileName = fileList.get(k);
                List<String> lines = new ArrayList<>();
                // read the file into lines
                BufferedReader r = new BufferedReader(new FileReader(fileName));
                String ln;
                while ((ln = r.readLine()) != null){lines.add(ln);}
                r.close();
                PrintWriter w = new PrintWriter(new FileWriter(fileName));
                // check the flags condition
                String newline;
                if (vf) {
                    newline = lines.get(0).replaceAll(fromStr, toStr);
                    lines.set(0,newline );
                }
                if (vl) {
                    newline = lines.get(lines.size() - 1).replaceAll(fromStr, toStr);
                    lines.set(lines.size() - 1, newline);
                }
                if (!vf && !vl) {
                    for (int m = 0; m < lines.size(); m++) {
                        newline = lines.get(m).replaceAll(fromStr, toStr);
                        lines.set(m, newline);
                    }
                }

                //write lines to the file
                for (int l = 0; l < lines.size() - 1; l++) {w.println(lines.get(l));}
                w.println(lines.get(lines.size() - 1)); // no last /n
                w.write("the string from is: ");
                w.write(fromStr);
                w.write("the to string is: ");
                w.write(toStr);
                w.close();
              }

              catch (Exception e) {//Catch exception if any
                    usage();
                    return;
                }
            }
        }



    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }

}

