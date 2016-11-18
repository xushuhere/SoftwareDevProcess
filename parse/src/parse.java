import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by shuxu on 11/17/16.
 */
public class parse {
    public static void main(String[] args){
        String fileName = "temp.txt";
        FileReader fr;
        FileWriter fw;
        try {
            fr = new FileReader(fileName);
            fw = new FileWriter(fileName+".bck");
            int  c = fr.read();
            while(c != -1)
            {
                fw.write(c);
                c =fr.read();
            }
            fr.close();
            fw.close();
        } catch (Exception e) {//Catch exception if any
        }
    }
}
