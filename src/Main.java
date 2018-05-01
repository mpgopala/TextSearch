import ir.TextParser;

class Main {


    public static void main(String [] args)
    {
    	//src/testdata/Doc1.txt src/testdata/Doc2.txt src/testdata/Doc3.txt src/testdata/Doc4.txt src/testdata/Doc5.txt src/testdata/Doc6.txt
        try {
            String[] fileNames = {
                    "src/testdata/Doc1.txt",
                    "src/testdata/Doc2.txt",
                    "src/testdata/Doc3.txt",
                    "src/testdata/Doc4.txt",
                    "src/testdata/Doc5.txt",
                    "src/testdata/Doc6.txt"
            };


	        if(args.length > 0)
				fileNames = args;
		        TextParser parser = new TextParser(fileNames);
            parser.parse();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
