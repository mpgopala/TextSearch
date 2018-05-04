package ir;

import ir.filter.Filter;
import ir.filter.FilterFactory;
import ir.util.Utils;

import java.util.*;

public class IRManager
{
	private TextParser _textParser;

	private String _indexFile;

	private ArrayList<Filter> _filters = new ArrayList<>();

	public IRManager(String[] files, String indexFile)
	{
		_filters.add(FilterFactory.createFilter(FilterFactory.FilterType.CONVERT_TO_LOWER_CASE));
		_filters.add(FilterFactory.createFilter(FilterFactory.FilterType.STOP_WORD_REMOVER));
		_filters.add(FilterFactory.createFilter(FilterFactory.FilterType.STEMMER));

		_textParser = new TextParser(files, _filters);
		_indexFile = indexFile;
	}

	public void parse()
	{
		_textParser.parse();

		String documentsIndex = Utils.printDocumentIndex(_textParser.getDocumentsMap());
		System.out.println(documentsIndex);

		String termIndex = Utils.printIndex(_textParser.getIndex());
		System.out.println(termIndex);
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
			retVal.add(_textParser.getDocumentsMap().get(id));
		}

		return retVal;
	}

	private Set<Integer> find(List<String> queryTerms)
	{
		Map<String, TreeMap<Integer, ArrayList<Integer>> > index = _textParser.getIndex();
		Set<Integer> retVal = new HashSet<>(_textParser.getDocumentsMap().keySet());

		for(String queryTerm : queryTerms)
		{
			if(index.get(queryTerm) != null) {
				final Set<Integer> termDocSet = new HashSet<>(index.get(queryTerm).keySet());
				retVal.retainAll(termDocSet);
			}
		}

		return retVal;
	}
}
