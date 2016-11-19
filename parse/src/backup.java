/**
 * Created by shuxu on 11/18/16.
 */
public class backup {
    public static void main(String [] args){
        String fromStr = "Thomas";
        String toStr = "Tom";
        String lastLine = "Thomas and his friends are thomas.";

        //String regex = "(?i)";
        String regex = "";

        lastLine = new StringBuffer(lastLine).reverse().toString();
        fromStr = new StringBuffer(fromStr).reverse().toString();
        toStr = new StringBuffer(toStr).reverse().toString();



        String newline = lastLine.replaceAll(regex+fromStr,toStr);
        newline = new StringBuffer(newline).reverse().toString();
        lastLine = new StringBuffer(lastLine).reverse().toString();

        System.out.println(newline);
        System.out.println(lastLine);

    }

}
