import ir.IRManager;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
	        IRManager irManager = new IRManager(fileNames, null);
	        irManager.parse();

	        boolean quit = false;

	        do
	        {
	            System.out.print("Please your query: ");
		        Scanner scanner = new Scanner(System. in);
		        String query = scanner.nextLine();
		        if(query.compareToIgnoreCase("exit") == 0) {
			        quit = true;
			        continue;
		        }

		        Set<String> docs = irManager.query(query);
		        if(docs.size() == 0) {
			        System.out.println("The query string was not found in any documents");
			        continue;
		        }

		        StringBuilder output = new StringBuilder();
		        output.append("The query string was found in the following documents");
		        output.append(System.getProperty("line.separator"));
		        for(String doc : docs)
		        {
		        	output.append("\t");
		        	output.append(doc);
		        	output.append(System.getProperty("line.separator"));
		        }
		        System.out.println(output);
		        System.out.println();
	        }while(!quit );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
