package ir.filter;

import java.util.ArrayList;
import java.util.Arrays;

public class StopWordRemover extends Filter
{
    private static final String[] stopWords = {
            "a",
            "an",
            "as",
            "the",
            "to",
            "of",
            "and",
            "in",
            "&"
    };

    @Override
    public ArrayList<String> execute(ArrayList<String> tokens)
    {
        tokens.removeAll(Arrays.asList(stopWords));
        return tokens;
    }
}
