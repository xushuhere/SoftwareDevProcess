import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuxu on 11/17/16.
 */
public class parse {
    public static void main(String[] args){
        boolean vf = false;
        boolean vb = false;
        boolean vl = false;
        boolean vi = false;
        List<String> fileList = new ArrayList<>();

        String fromStr = null;
        String toStr = null;
        int pointer = 0;
        if (args.length <3) {usage();return;}
        for (String arg: args){
            if (pointer == 0) {
                if (arg.equals("-f")) {vf = true;continue;}
                if (arg.equals("-l")) {vl = true;continue;}
                if (arg.equals("-i")) {vi = true;continue;}
                if (arg.equals("-b")) {vb = true;continue;}
                if (arg.equals("--")) {pointer += 1;continue;}
                if (arg.startsWith("-") && !arg.equals("-f") && !arg.equals("-l") &&
                        !arg.equals("-b") &&  !arg.equals("-i")) {usage();return;}
                if (!arg.startsWith("-") ){fromStr = arg;pointer = 2;continue;}
            }
            if (pointer == 1) {fromStr = arg;pointer = 2;continue;}
            if (pointer == 2) {toStr = arg;pointer = 3;continue;}
            if (pointer == 3 && arg.equals("--")) {pointer = 4;continue;}
            if (pointer == 3 && !arg.equals("--")) {fileList.add(arg);pointer = 4;continue;}
            if (pointer == 4) {fileList.add(arg);continue;}

        }
    }
}
