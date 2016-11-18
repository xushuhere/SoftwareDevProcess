import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by shuxu on 11/17/16.
 */
public class parse {
    public static void main(String[] args){
        String fileName = "temp.txt";
        try {
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            FileWriter writer = new FileWriter(fileName+".bck");
            String line = "", content = "";
            while((line = reader.readLine()) != null)
            {
                content += line + "\r\n";
            }
            reader.close();
            writer.write(content);
            writer.close();
        } catch (Exception e) {//Catch exception if any
        }
    }
}
