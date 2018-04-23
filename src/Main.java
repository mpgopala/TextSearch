import ir.TextParser;
import java.io.*;

public class Main {
    private static String readFile(String fileName) throws IOException
    {
        File file = new File(fileName);
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
                file));
        byte[] buffer = new byte[(int) file.length()];
        bin.read(buffer);
        return new String(buffer);
    }

    public static void main(String [] args)
    {
        try {
            String fileName = "/Users/gsharma/Code/IRAssignment/src/test.txt";
            String contents = readFile(fileName);
            TextParser parser = new TextParser(contents);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
