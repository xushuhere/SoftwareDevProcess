import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuxu on 11/17/16.
 */
public class parseCmd {
    public static void main(String[] args) {
        //variables for replacement
        boolean vf = false;
        boolean vb = false;
        boolean vl = false;
        boolean vi = false;
        List<String> fileList = new ArrayList<>();

        String fromStr = null;
        String toStr = null;
        String[] arg2;
        String arg;
        int pointer = 0;

        String args2[] = {"test", "Test", "--", "inputFile1.getPath()" };

        // pinter
        // 0 for options, 1 for fromString (forced),
        // 2 for to String, 3 for "--", 4 and beyond for fileList

        //String[] pointer = {"option", "fromString", "toString","--" ,"fileName", *};
        for (int i = 0; i < args2.length; i++) {
            arg = args2[i];
            if (pointer == 0) {
                if (arg.equals("-f") && !vf) {
                    vf = true;
                    continue;
                }
                if (arg.equals("-l") && !vb) {
                    vl = true;
                    continue;

                }
                if (arg.equals("-i") && !vi) {
                    vi = true;
                    continue;

                }
                if (arg.equals("-b") && !vb) {
                    vb = true;
                    continue;

                }
                if (arg.equals("--")) {
                    pointer += 1;
                    continue;

                } else if (arg.startsWith("-")) {

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
                return;
            }
            if (pointer == 4) {
                fileList.add(arg);
                continue;

            }
        }
        try {
            //vf = true;
            //vi = true;
            FileWriter writer = new FileWriter("temp2.txt");
            String line ="";
            if (vf){ line = line + "-f is true/n";}
            if (vi) {line = line + " -i is true/n";};
            if (vl) {line = line + " -l is true/n";};

            if (vb) {line = line + " -b is true/n";};
            line = line + "The from String is :" + fromStr;
            line = line + "The to String is :" + toStr;


            for (String file: fileList){
                line = line + "  "+ file;}

            writer.write(line);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
