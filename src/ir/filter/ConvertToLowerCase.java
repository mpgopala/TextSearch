package ir.filter;

import java.util.ArrayList;

public class ConvertToLowerCase extends Filter
{
	@Override
	public ArrayList<String> execute(ArrayList<String> tokens)
	{
		ArrayList<String> retVal = new ArrayList<>();
		for(String token : tokens)
		{
			token = token.toLowerCase();
			retVal.add(token);
		}
		return retVal;
	}
}
