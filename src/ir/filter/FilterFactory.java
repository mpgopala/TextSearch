package ir.filter;

public class FilterFactory
{
	public enum FilterType
	{
		CONVERT_TO_LOWER_CASE,
		STEMMER,
		STOP_WORD_REMOVER
	}

	public static Filter createFilter(FilterType filterType)
	{
		Filter retVal = null;
		switch(filterType)
		{
			case CONVERT_TO_LOWER_CASE:
				retVal = new ConvertToLowerCase();
				break;
			case STOP_WORD_REMOVER:
				retVal = new StopWordRemover();
				break;
			case STEMMER:
				retVal = new Stemmer();
				break;
		}
		return retVal;
	}
}
