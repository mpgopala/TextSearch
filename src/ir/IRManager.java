package ir;

import ir.filter.Filter;
import ir.filter.FilterFactory;
import ir.util.Utils;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class IRManager
{
	private TextParser _textParser;

	private String _indexFile;

	private Map<Integer, String> _documentsMap;

	private Map<String, TreeMap<Integer, ArrayList<Integer>> > _termIndex;

	private ArrayList<Filter> _filters = new ArrayList<>();

	public IRManager(String[] files, String indexFile)
	{
		_filters.add(FilterFactory.createFilter(FilterFactory.FilterType.CONVERT_TO_LOWER_CASE));
		_filters.add(FilterFactory.createFilter(FilterFactory.FilterType.STOP_WORD_REMOVER));
		_filters.add(FilterFactory.createFilter(FilterFactory.FilterType.STEMMER));


		if(files != null && files.length > 0) {
			System.out.println("Parsing and indexing the supplied documents...");
			TextParser textParser = new TextParser(files, _filters);
			parse(textParser, indexFile);
		}
		else if(indexFile != null) {
			System.out.println("parsing the index file");
			_indexFile = indexFile;
			parseIndexFile(_indexFile);
		}
		else
		{
			throw new IllegalArgumentException("Need to pass either documents to parse or an index file!");
		}

		printIndexes(null);
	}

	private void parse(TextParser parser, String indexFile)
	{
		parser.parse();

		_documentsMap = parser.getDocumentsMap();
		_termIndex = parser.getIndex();

		if(indexFile != null)
		{
			try {
				FileOutputStream fos = new FileOutputStream(indexFile);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				printIndexes(osw);
			} catch(Exception e) {
				System.err.println("Exception in writing to " + indexFile);
				e.printStackTrace();
			}
		}
	}

	private void printIndexes(OutputStreamWriter writer)
	{
		if(writer == null)
		{
			writer = new OutputStreamWriter(System.out);
		}

		try {
			String documentsIndex = Utils.printDocumentIndex(_documentsMap);
			writer.write(documentsIndex);
			writer.write(Utils.getCRLF());

			String termIndex = Utils.printIndex(_termIndex);
			writer.write(termIndex);
			writer.write(Utils.getCRLF());
			writer.flush();
		} catch (Exception e) {
			System.err.println("Exception in writing to stream in printIndexes");
			e.printStackTrace();
		}
	}

	public Set<String> query(String queryString)
	{
		Set<String> retVal = new HashSet<>();
		ArrayList<String> queryParams = new ArrayList<>(Arrays.asList(queryString.split(" ")));

		queryParams = Utils.applyFilters(_filters, queryParams);

		StringBuilder output = new StringBuilder();
		output.append("Your query is: ");
		for(String s : queryParams) {
			output.append(s);
			output.append(" ");
		}

		System.out.println(output);

		Set<Integer> foundDocIds = find(queryParams);
		for(Integer id : foundDocIds)
		{
			retVal.add(_documentsMap.get(id));
		}

		return retVal;
	}

	private Set<Integer> find(List<String> queryTerms)
	{
		Map<String, TreeMap<Integer, ArrayList<Integer>> > index = _termIndex;
		Set<Integer> retVal = new HashSet<>(_documentsMap.keySet());

		for(String queryTerm : queryTerms)
		{
			if(index.get(queryTerm) != null) {
				final Set<Integer> termDocSet = new HashSet<>(index.get(queryTerm).keySet());
				retVal.retainAll(termDocSet);
			}
			else
			{
				retVal.clear();
				break;
			}
		}

		return retVal;
	}

	private void parseIndexFile(String indexFile)
	{
		String indexFileContents = Utils.readFile(indexFile);
		if(indexFileContents.length() == 0)
		{
			return;
		}

		String[] lines = indexFileContents.split(Utils.getCRLF());
		//Read the documents map.
		_documentsMap = new TreeMap<>();

		int i = 0;
		for(; i < lines.length; i++)
		{
			String line = lines[i];
			line = line.trim();

			if(line.length() == 0)
				break;
			//Split on " -> ".
			String[] pair = line.split(" -> ");
			_documentsMap.put(Integer.parseInt(pair[0]), pair[1]);
		}

		_termIndex = new TreeMap<>();

		//Parse the index file.
		for(; i < lines.length; i++)
		{
			String line = lines[i];
			if(line.length() == 0)
				continue;

			//Split on " -> "
			String[] pair = line.split(" -> ");
			String term = pair[0].trim();
			String docIndexString = pair[1].trim();

			//Split docIndexString on ;
			String[] docIndexes = docIndexString.split(";");

			TreeMap<Integer, ArrayList<Integer>> docIndexMap = new TreeMap<>();
			for(String docIndex : docIndexes)
			{
				//Parse each document index
				//Split on :
				docIndex = docIndex.trim();
				String[] docIndexesPair = docIndex.split(":");
				Integer documentId = Integer.parseInt(docIndexesPair[0].trim());
				String indexString = docIndexesPair[1].trim();
				//Split indices on ,
				String[] indices = indexString.split(",");
				ArrayList<Integer> indexArray = new ArrayList<>();
				for(String index : indices)
				{
					indexArray.add(Integer.parseInt(index.trim()));
				}

				docIndexMap.put(documentId, indexArray);
			}

			_termIndex.put(term, docIndexMap);
		}

	}
}
