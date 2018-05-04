import ir.IRManager;

import java.util.*;

class Main {

	private static final String DOCS = "docs";
	private static final String INDEX_FILE = "indexFile";
	private static final Map<String, String> ARG_NAMES = new TreeMap<>();
	private static Map<String, String> parseArgs(String[] args)
	{
		Map<String, String> retVal = new HashMap<>();
		int i = 0;
		for(; i < args.length;)
		{
			if(args[i].compareToIgnoreCase(ARG_NAMES.get(DOCS)) == 0)
			{
				i++;
				retVal.put(DOCS, args[i]);
				i++;
			}
			else if(args[i].compareToIgnoreCase(ARG_NAMES.get(INDEX_FILE)) == 0)
			{
				i++;
				retVal.put(INDEX_FILE, args[i]);
				i++;
			}
		}
		return retVal;
	}

    public static void main(String [] args)
    {
    	ARG_NAMES.put(DOCS, "--docs");
    	ARG_NAMES.put(INDEX_FILE, "--indexFile");
    	Map<String, String> arguments = parseArgs(args);
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

            System.out.println("args: " + args.length);

			String[] files = null;
			String docArgs = arguments.get(DOCS);
			if(docArgs != null)
			{
				files = docArgs.split(" ");
			}

	        IRManager irManager = new IRManager(files, arguments.get(INDEX_FILE));

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
