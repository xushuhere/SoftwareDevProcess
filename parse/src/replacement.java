import java.io.*;
import java.util.*;

/**
 * Created by shuxu on 11/17/16.
 */
public class replacement {
    public static void main(String[] args){

        String fromStr = "Shu";
        String toStr = "HAO*REN";
        boolean vf = true;
        boolean vl = true;
        //fromStr = "(?i)"+ fromStr;
    try
        {File file = new File("temp2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "", oldtext = "";
        List<String> lineList = new ArrayList<>();
        int length= 0;
        while((line = reader.readLine()) != null)
        {    length += 1;
            lineList.add(line);
            oldtext += line + "\r\n";
        }
        reader.close();
        //FileWriter writer = new FileWriter(fileName);
        BufferedWriter br = new BufferedWriter(new FileWriter(file));


        if (!vf && !vl) {
            String replacedtext = oldtext.replaceAll(fromStr, toStr);
            br.write(replacedtext);
        }
        else {
            if (vf) {
                String fLine = lineList.get(0);
                lineList.set(0, fLine.replaceAll(fromStr,toStr));
            }
            if (vl){
                String lLine = lineList.get(length-1);
                lineList.set(length-1, lLine.replaceAll(fromStr,toStr));
            }
            for (int a = 0;a <length;a++){
                br.write(lineList.get(a));
                br.newLine();
            }

        }

        br.close();

    } catch (Exception e) {//Catch exception if any
        return;
    }
    }
}
