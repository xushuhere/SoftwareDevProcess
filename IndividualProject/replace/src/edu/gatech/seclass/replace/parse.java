package edu.gatech.seclass.replace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuxu on 11/17/16.
 */
public class parse {
        //constructors
        boolean f_flag = false;
        boolean b_flag = false;
        boolean l_flag = false;
        boolean i_flag = false;
    String fromStr;
    String toStr;
    String[] args = {"-i", "Howdy", "Hello", "--", "inputFile1.getPath()", "inputFile2.getPath()", "inputFile3.getPath()"};

    List<String> fileList = new ArrayList<>();

    public boolean getfflag(){
        return  f_flag;}

    public boolean getbflag(){ return b_flag;}

    public boolean getlflag(){return l_flag;}
        public boolean getiflag(){ return i_flag;
    }


        public void main(String[] args) {
            //variables for replacement


            int i = 0, j;

            String arg;
            //String[] pointer = {"option", "fromString", "toString","--" ,"fileName"};

            try {
                while (i < args.length && args[i].startsWith("-")) {
                    arg = args[i];

                    if (arg.equals("-f") && !f_flag) {
                        f_flag = true;
                        continue;
                    }
                    if (arg.equals("-l") && !l_flag) {
                        l_flag = true;
                        continue;
                    }
                    if (arg.equals("-i") && !i_flag) {
                        i_flag = true;
                        continue;
                    }
                    if (arg.equals("-b") && !b_flag) {
                        b_flag = true;
                        continue;
                    }
                    if (arg.equals("--")) {
                        break;
                    } else if (arg.startsWith("-")) {
                        return;
                    }
                    i += 1;
                }

                i += 1;
                arg = args[i];
                if (i_flag) {
                    fromStr = "(?i)" + arg;
                } else {
                    fromStr = arg;
                }
                i += 1;
                toStr = args[i];
                i += 1;
                if (!args[i].equals("--")) {
                    return;
                }

                for (j = i; j < args.length; j++) {
                    fileList.add(args[j]);
                }
            } catch (Exception e) {
                return;
            }

            if (fileList.size() == 0) {
                return;

            }
        }
        public void  main(){
            main(args);
            System.out.print(f_flag);

        }
}



