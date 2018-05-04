package ir.util;

import ir.filter.Filter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Utils
{
	public static void applyFilters(List<Filter> filters, Map<Integer, ArrayList<String> > tMap)
	{
		for(Filter filter : filters)
		{
			if(filter.isEnabled())
			{
				for(Map.Entry<Integer, ArrayList<String>> entry : tMap.entrySet())
				{
					ArrayList<String> tokens = entry.getValue();
					tokens = filter.execute(tokens);
					entry.setValue(tokens);
				}
			}
		}
	}

	public static ArrayList<String> applyFilters(List<Filter> filters, ArrayList<String> tokens)
	{
		ArrayList<String> retVal = tokens;
		for(Filter filter: filters)
		{
			if(filter.isEnabled())
			{
					retVal = filter.execute(retVal);
			}
		}
		return retVal;
	}

	public static String getCRLF()
	{
		return System.getProperty("line.separator");
	}

	public static String readFile(String fileName)
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

	public static String printDocumentIndex(Map<Integer, String> documentsMap)
	{
		StringBuilder builder = new StringBuilder();
		//Print the document index
		for(Map.Entry<Integer, String> docEntry : documentsMap.entrySet())
		{
			builder.append(docEntry.getKey());
			builder.append(" -> ");
			builder.append(docEntry.getValue());
			builder.append(System.getProperty("line.separator"));
		}

		return builder.toString();
	}

	public static String printIndex(Map<String, TreeMap<Integer, ArrayList<Integer>> > index)
	{
		StringBuilder builder = new StringBuilder();

		for(Map.Entry<String, TreeMap<Integer, ArrayList<Integer>>> indexEntry : index.entrySet())
		{
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

			builder.append(System.getProperty("line.separator"));
		}

		return builder.toString();
	}

	public <T> Set<T> intersect(Set<T> a, Set<T> b)
	{
		Set<T> retVal = new HashSet<>(a);
		retVal.retainAll(b);
		return retVal;
	}
}
