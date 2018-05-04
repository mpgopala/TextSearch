package ir;

import ir.filter.Filter;
import ir.util.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

class TextParser {

    private ArrayList<Filter> _filters = new ArrayList<>();

	private final Map<Integer, String> documentsMap = new TreeMap<>();

    private final Map<Integer, ArrayList<String> > tokensMap = new TreeMap<>();

    private final Map<String, TreeMap<Integer, ArrayList<Integer>> > index = new TreeMap<>();

    final Map<Integer, String> getDocumentsMap() { return documentsMap; }

    final Map<String, TreeMap<Integer, ArrayList<Integer>> > getIndex() { return index; }

    TextParser(String[] files, ArrayList<Filter> filters)
    {
    	if(files == null)
    		return;

        for(String file : files)
        {
            String fileContents = Utils.readFile(file);
            String[] fileTokens = fileContents.split(" ");
            Integer id = documentsMap.size() + 1;
            documentsMap.put(id, file);
            tokensMap.put(id, new ArrayList<>(Arrays.asList(fileTokens)));
        }
        _filters = filters;
    }

    void parse()
    {
        Utils.applyFilters(_filters, tokensMap);

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
    }
}