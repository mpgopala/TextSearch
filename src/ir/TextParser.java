package ir;

import ir.filter.Filter;
import ir.filter.Stemmer;
import ir.filter.StopWordRemover;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class TextParser {

    private final ArrayList<Filter> filters = new ArrayList<>();

	private final Map<String, Integer> documentsMap = new TreeMap<>();

    private final Map<Integer, ArrayList<String> > tokensMap = new TreeMap<>();

    private final Map<String, TreeMap<Integer, ArrayList<Integer>> > index = new TreeMap<>();

    private static String readFile(String fileName)
    {
        String retVal = "";

        try {
            File file = new File(fileName);
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
                    file));
            byte[] buffer = new byte[(int) file.length()];
            if(bin.read(buffer) == 0)
            {
            	System.out.println("Could not read file " + fileName);
            }
            else
	            retVal = new String(buffer);
        }
        catch(Exception e)
        {
			System.err.println("Could not read from file " + fileName);
			e.printStackTrace();
        }
        return retVal;
    }

    private void applyFilters()
    {
        for(int i = 0; i < filters.size(); i++)
        {
            Filter filter = filters.get(i);
            if(filter.isEnabled())
            {
                for(Map.Entry<Integer, ArrayList<String>> entry : tokensMap.entrySet())
                {
                    ArrayList<String> tokens = entry.getValue();
                    tokens = filter.execute(tokens);
                    entry.setValue(tokens);
                }
            }
        }
    }

    private void printIndex()
    {
    	//Print the document index
	    for(Map.Entry<String, Integer> docEntry : documentsMap.entrySet())
	    {
	    	System.out.println(docEntry.getValue() + " -> " + docEntry.getKey());
	    }

    	for(Map.Entry<String, TreeMap<Integer, ArrayList<Integer>>> indexEntry : index.entrySet())
    	{
		    StringBuilder builder = new StringBuilder();
		    builder.append(indexEntry.getKey());
		    builder.append(" -> ");
		    for(Map.Entry<Integer, ArrayList<Integer>> entry : indexEntry.getValue().entrySet())
		    {
			    builder.append(entry.getKey());
			    builder.append(": ");
			    boolean first = true;
			    for(int i = 0; i < entry.getValue().size(); i++) {
				    if(!first)
					    builder.append(", ");
				    builder.append(entry.getValue().get(i));
				    first = false;
			    }
			    builder.append("; ");
		    }

		    System.out.println(builder.toString());
	    }
    }

    public TextParser(String[] files)
    {
        for(String file : files)
        {
            String fileContents = readFile(file);
            String[] fileTokens = fileContents.split(" ");
            documentsMap.put(file, documentsMap.size() + 1);
            tokensMap.put(documentsMap.get(file), new ArrayList<>(Arrays.asList(fileTokens)));
        }
        filters.add(new StopWordRemover());
        filters.add(new Stemmer());
    }

    public void parse()
    {
        applyFilters();

        for(Map.Entry<Integer, ArrayList<String>> entry : tokensMap.entrySet())
        {
        	ArrayList<String> tokens = entry.getValue();
	        for(int i = 0; i < tokens.size(); i++)
	        {
		        String token = tokens.get(i);
		        token = token.trim();
		        TreeMap<Integer, ArrayList<Integer>> docIndices;
		        ArrayList<Integer> indices;
		        if(index.containsKey(token)) {
			        docIndices = index.get(token);
			        if(docIndices.containsKey(entry.getKey()))
			        {
			        	indices = docIndices.get(entry.getKey());
			        }
			        else
			        {
			        	indices = new ArrayList<>();
			        }

		        } else {
			        docIndices = new TreeMap<>();
			        indices = new ArrayList<>();
		        }

		        indices.add(i + 1);
		        docIndices.put(entry.getKey(), indices);
		        index.put(token, docIndices);
	        }
        }


        printIndex();
    }
}