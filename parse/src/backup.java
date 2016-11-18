import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by shuxu on 11/18/16.
 */
public class backup {
    public static void main(String [] args){
        String fileName = "temp4.txt";
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8);
            FileWriter fw = new FileWriter(fileName+".bck_new.txt");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
