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
        int pointer = 0;


        // pinter
        // 0 for options, 1 for fromString (forced),
        // 2 for to String, 3 for "--", 4 and beyond for fileList

        //String[] pointer = {"option", "fromString", "toString","--" ,"fileName", *};
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
        System.out.println(args);
        System.out.println();
        System.out.println(vf);
        System.out.println(vb);
        System.out.println(vi);
        System.out.println(vl);
        System.out.println();
        System.out.println(fromStr);
        System.out.println(toStr);
        System.out.println();
        System.out.println(fileList);

    }


    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }
}
