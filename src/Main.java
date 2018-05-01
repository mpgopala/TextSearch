import ir.TextParser;

class Main {


    public static void main(String [] args)
    {
        try {
            String[] fileNames = {
                    "/Users/gsharma/Code/IRAssignment/src/testdata/Doc1.txt",
                    "/Users/gsharma/Code/IRAssignment/src/testdata/Doc2.txt",
                    "/Users/gsharma/Code/IRAssignment/src/testdata/Doc3.txt",
                    "/Users/gsharma/Code/IRAssignment/src/testdata/Doc4.txt",
                    "/Users/gsharma/Code/IRAssignment/src/testdata/Doc5.txt",
                    "/Users/gsharma/Code/IRAssignment/src/testdata/Doc6.txt"
            };
            TextParser parser = new TextParser(fileNames);
            parser.parse();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
