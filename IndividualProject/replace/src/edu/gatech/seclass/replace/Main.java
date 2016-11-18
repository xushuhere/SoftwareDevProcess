package edu.gatech.seclass.replace;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        // pinter
        // 0 for options, 1 for fromString (forced),
        // 2 for to String, 3 for "--", 4 and beyond for fileList

        //String[] pointer = {"option", "fromString", "toString","--" ,"fileName", *};
        for (int i = 0; i < args.length; i++) {
            arg = args[i];
            if (pointer == 0) {
                if (arg.equals("-f")) {
                    vf = true;
                    continue;
                }
                if (arg.equals("-l")) {
                    vl = true;
                    continue;
                }
                if (arg.equals("-i")) {
                    vi = true;
                    continue;
                }
                if (arg.equals("-b")) {
                    vb = true;
                    continue;
                }
                if (arg.equals("--")) {
                    pointer += 1;
                    continue;
                } else if (arg.startsWith("-")) {
                    usage();
                    return;
                } else {
                    fromStr = arg;
                    pointer = 2;
                    continue;
                }
            }
            if (pointer == 1) {
                fromStr = arg;
                pointer = 2;
                continue;
            }
            if (pointer == 2) {
                toStr = arg;
                pointer = 3;
                continue;
            }
            if (pointer == 3 && arg.equals("--")) {
                pointer = 4;
                continue;
            }
            if (pointer == 3 && !arg.equals("--")) {
                usage();
                return;
            }
            if (pointer == 4) {
                fileList.add(arg);
                continue;
            }
        }


        if (fileList.size() == 0) {
            usage();
            return;
        }
        if (fromStr == "" || fromStr == "\"\"") {
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
                    String content;
                    content = new String(Files.readAllBytes(Paths.get(fileName)),
                            StandardCharsets.UTF_8);
                    FileWriter fw = new FileWriter(fileName + ".bck");
                    fw.write(content);
                    fw.close();

                } catch (Exception e) {
                    int index = fileName.lastIndexOf(File.separator);
                    String fileShortName = fileName.substring(index + 1);
                    System.err.println("File "+ fileShortName + " not found");
                }
            }
        }


        // add regex to fromStr for case not sensentivity
        if (vi) {
            fromStr = "(?i)" + fromStr;
        }


        // replace when -f and -l tags are off
        if (!vf && !vl) {
            for (int k = 0; k < fileList.size(); k++) {
                String fileName = fileList.get(k);
                String content;
                try {
                    content = new String(Files.readAllBytes(Paths.get(fileName)),
                            StandardCharsets.UTF_8);
                    FileWriter fw = new FileWriter(fileName);
                    content = content.replaceAll(fromStr, toStr);
                    fw.write(content);
                    fw.close();
                } catch (Exception e) {
                    int index = fileName.lastIndexOf(File.separator);
                    String fileShortName = fileName.substring(index + 1);
                    System.err.println("File "+ fileShortName + " not found");
                }
            }
        }

        // replace when either -f or -l is on
        else {
            for (int m = 0; m < fileList.size(); m++) {
                String fileName = fileList.get(m);
                try {
                    File file = new File(fileName);
                    String content;
                    content = new String(Files.readAllBytes(Paths.get(fileName)),
                            StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = "";//, oldtext = "";
                    List<String> lineList = new ArrayList<>();
                    int length = 0;
                    while ((line = reader.readLine()) != null) {
                        length += 1;
                        lineList.add(line);
                    }
                    reader.close();
                    //FileWriter writer = new FileWriter(fileName);
                    BufferedWriter br = new BufferedWriter(new FileWriter(file));
                    if (vf) {
                        String fLine = lineList.get(0);
                        lineList.set(0, fLine.replaceAll(fromStr, toStr));
                    }
                    if (vl) {
                        String lLine = lineList.get(length - 1);
                        lineList.set(length - 1, lLine.replaceAll(fromStr, toStr));
                    }
                    for (int a = 0; a < length - 1; a++) {
                        br.write(lineList.get(a));
                        br.newLine();
                    }
                    br.write(lineList.get(length - 1));
                    if (content.lastIndexOf(System.lineSeparator())+ 1  == content.length()){
                        br.newLine();
                    }
                    br.close();

                } catch (Exception e) {
                    int index = fileName.lastIndexOf(File.separator);
                    String fileShortName = fileName.substring(index + 1);
                    System.err.println("File "+ fileShortName + " not found");

                }
            }

        }
    }


    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }

}

