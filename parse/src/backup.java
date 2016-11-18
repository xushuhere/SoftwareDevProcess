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
        String fileName = "temp.txt";
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)),
                    StandardCharsets.UTF_8);
            int x = content.lastIndexOf(System.lineSeparator());
            System.out.println("The last index of line separator is:");
            System.out.println(content.substring(content.length() - 1));
            boolean last = (System.lineSeparator()== "\n");
            System.out.println(last);
            System.out.println(x);
            System.out.println("The total length of the files is :");
            System.out.println(content.length());
            FileWriter fw = new FileWriter(fileName+".bck_new.txt");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
