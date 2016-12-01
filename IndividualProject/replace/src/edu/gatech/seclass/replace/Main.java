package edu.gatech.seclass.replace;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
        List<String> fromStrLst = new ArrayList<>();
        List<String> toStrLst = new ArrayList<>();
        String fromStr, toStr;
        int pointer = 0;
        // 0 for options, 1 for fromString (forced),
        // 2 for to String, 3 for "--", 4 and beyond for fileList
        //String[] pointer = {"option", "fromString", "toString","--" ,"fileName", *};
        if (args.length < 3) {
            usage();
            return;
        }
        for (String arg : args) {
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
                }
                if (arg.startsWith("-") && !arg.equals("-f") && !arg.equals("-l") &&
                        !arg.equals("-b") && !arg.equals("-i")) {
                    usage();
                    return;
                }
                if (!arg.startsWith("-")) {
                    fromStrLst.add(arg);
                    pointer = 2;
                    continue;
                }
            }
            //if (pointer == 1) {fromStr = arg;pointer = 2;continue;}
            if (pointer == 1) {
                fromStrLst.add(arg);
                pointer = 2;
                continue;
            }
            //if (pointer == 2) {toStr = arg;pointer = 3;continue;}
            if (pointer == 2) {
                toStrLst.add(arg);
                pointer = 3;
                continue;
            }
            if (pointer == 3 && arg.equals("--")) {
                pointer = 4;
                continue;
            }
            //if (pointer == 3 && !arg.equals("--")) {usage();return;}
            if (pointer == 3 && !arg.equals("--")) {
                fromStrLst.add(arg);
                pointer = 2;
                continue;
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
        //if (fromStr == "") {usage();return;}
        if (fromStrLst.size() == 0) {
            usage();
            return;
        }
        if (toStrLst.size() == 0) {
            usage();
            return;
        }
        if (fromStrLst.size() != toStrLst.size()) {
            usage();
            return;
        }


        // add regex to fromStr for case not sensentivity
        String regex = "";
        if (vi) {
            regex = "(?i)";
        }

        // loop through from String list and to String list and doing the replacement
        for (int l = 0; l < fromStrLst.size(); l++) {
            fromStr = fromStrLst.get(l);
            if (fromStr.equals("")) {usage();return;}
            toStr = toStrLst.get(l);
            for (String fileName : fileList) {
                try {
                    if (vb) {// backup files first
                        Path p = Paths.get(fileName);
                        Path pb = Paths.get(fileName + ".bck");
                        int index = fileName.lastIndexOf(File.separator);
                        String fileShortName = fileName.substring(index + 1);
                        if (Files.notExists(pb)) {
                            String content;
                            content = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
                            FileWriter fw = new FileWriter(fileName + ".bck");
                            fw.write(content);
                            fw.close();
                        } else {
                            System.err.println("Not performing replace for " + fileShortName + ": Backup file already exists");
                            continue;
                        }
                    }

                    String content = new String(Files.readAllBytes(Paths.get(fileName)),
                            StandardCharsets.UTF_8);
                    FileWriter fw = new FileWriter(fileName);
                    // if none of first or last flag is on
                    if (!vf && !vl) {content = content.replaceAll(regex + fromStr, toStr);}
                    // if first or last flag is on
                    else {
                        if (vf) {content = content.replaceFirst(regex + fromStr, toStr);}
                        if (vl) {
                            String content_r = new StringBuffer(content).reverse().toString();
                            fromStr = new StringBuffer(fromStr).reverse().toString();
                            toStr = new StringBuffer(toStr).reverse().toString();
                            content_r = content_r.replaceFirst(regex + fromStr, toStr);
                            content = new StringBuffer(content_r).reverse().toString();
                        }
                    }
                    fw.write(content);
                    fw.close();

            }

            catch (Exception e) {
                        int index = fileName.lastIndexOf(File.separator);
                        String fileShortName = fileName.substring(index + 1);
                        System.err.println("File " + fileShortName + " not found");
                    }
            }
        }

    }

    

    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }
}

