package edu.gatech.seclass.replace;

import java.io.*;
import java.util.*;

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
        // pinter
        // 0 for options, 1 for fromString (forced),
        // 2 for to String, 3 for "--", 4 and beyond for fileList

        //String[] pointer = {"option", "fromString", "toString","--" ,"fileName", *};
        for (int i = 0; i < args.length; i++) {
            arg = args[i];
            if (pointer == 0) {
                if (arg.equals("-f") && !vf) {vf = true;continue;}
                if (arg.equals("-l") && !vb) {vl = true;continue;}
                if (arg.equals("-i") && !vi) {vi = true;continue;}
                if (arg.equals("-b") && !vb) {vb = true;continue;}
                if (arg.equals("--")) {pointer += 1;continue;}
                else if (arg.startsWith("-")) {usage();return;}
                else {fromStr = arg;pointer = 2;continue;}
            }
            if (pointer == 1) {fromStr = arg;pointer = 2;continue;}
            if (pointer == 2) {toStr = arg;pointer = 3;continue;}
            if (pointer == 3 && arg.equals("--")) {pointer = 4;continue;}
            if (pointer == 3 && !arg.equals("--")) {usage();return;}
            if (pointer == 4) {fileList.add(arg);continue;}
        }


        if (fileList.size() == 0) {
            usage();
            return;
        }
        if (fromStr ==""){
            usage();
            return;
        }


        //vb = true;
        //backup files before any operations, should be working now
        if (vb) {
            for (int x = 0; x < fileList.size(); x++) {
                String fileName = fileList.get(x);
                try {
                    //File file = new File(fileName);
                    FileReader fr;
                    FileWriter fw;

                    fr = new FileReader(fileName);
                    fw = new FileWriter(fileName + ".bck");
                    int c = fr.read();
                    while (c != -1) {
                        fw.write(c);
                        c = fr.read();
                    }
                    fr.close();
                    fw.close();

                }catch (Exception e) {//Catch exception if any
                    usage();
                    return;
                }
            }
        }



            // add regex to fromStr for case not sensentivity
            if (vi) {
                fromStr = "(?i)" + fromStr;
            }


            // replace
            for (int k = 0; k < fileList.size(); k++) {
                String fileName = fileList.get(k);
                try {
                    File file = new File(fileName);
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = "";//, oldtext = "";
                    List<String> lineList = new ArrayList<>();
                    int length= 0;
                    while((line = reader.readLine()) != null) { length += 1;
                        lineList.add(line);
                        //oldtext += line + "\r\n";
                    }

                    reader.close();
                    //FileWriter writer = new FileWriter(fileName);
                    BufferedWriter br = new BufferedWriter(new FileWriter(file));

                    String currentLine;
                    if (!vf && !vl) {
                        for (int b= 0;b < length; b++){
                            currentLine = lineList.get(b).replaceAll(fromStr, toStr);
                            lineList.set(b, currentLine);
                        }
                        //String replacedtext = oldtext
                        //String replacedtext = "blank blank";
                        //br.write(replacedtext);
                    }
                    else {
                        if (vf) {
                            String fLine = lineList.get(0);
                            lineList.set(0, fLine.replaceAll(fromStr, toStr));
                        }
                        if (vl) {
                            String lLine = lineList.get(length - 1);
                            lineList.set(length - 1, lLine.replaceAll(fromStr, toStr));
                        }
                    }
                        for (int a = 0;a <length-1;a++){
                            br.write(lineList.get(a));
                            br.newLine();
                        }
                        br.write(lineList.get(length-1));


                    br.close();

                } catch (Exception e) {//Catch exception if any
                    usage();
                    return;
                }
            }
        }



    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }

}

