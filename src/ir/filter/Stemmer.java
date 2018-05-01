package ir.filter;

import java.util.ArrayList;

public class Stemmer extends Filter
{
	//Remove Last s, 's and s', ies
	private static final String[] toRemove = {"'s", "s'", "ies", "s", "ion", "ment", "ing"};
	private static final String ED = "ed";

	private String removeTrailing(String obj, String val)
	{
		if(obj.endsWith(val))
		{
			obj = obj.substring(0, obj.length() - val.length());
		}

		return obj;
	}

	@Override
	public ArrayList<String> execute(ArrayList<String> tokens)
	{
		ArrayList<String> retVal = new ArrayList<>();
		for(String token : tokens)
		{
			for(String remove:toRemove)
			{
				token = removeTrailing(token, remove);
			}

			if(token.endsWith(ED))
			{
				token = token.substring(0, token.length() - 1);
			}

			retVal.add(token);
		}
		return retVal;
	}
}
