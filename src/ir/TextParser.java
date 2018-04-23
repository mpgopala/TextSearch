package ir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TextParser {
    private static String[] stopWords = {
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


    private String[] tokens;

    private Map<String, ArrayList> index = new HashMap<>();
    private boolean removeStopWords()
    {
        ArrayList<String> stringTokens = new ArrayList<>(Arrays.asList(tokens));
        stringTokens.removeAll(Arrays.asList(stopWords));
        String [] t = new String [1];
        tokens = stringTokens.toArray(t);
        return true;
    }

    private void printIndex()
    {
        for(Map.Entry<String, ArrayList> entry : index.entrySet())
        {
            StringBuilder builder = new StringBuilder();
            builder.append(entry.getKey());
            builder.append(" -> ");
            boolean first = true;
            for(int i = 0; i < entry.getValue().size(); i++)
            {
                if(!first)
                    builder.append(", ");
                builder.append(entry.getValue().get(i));
                first = false;
            }
            builder.append(";");
            System.out.println(builder.toString());
        }
    }

    public TextParser(String text) {
        tokens = text.split(" ");

        removeStopWords();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            ArrayList indices;
            token = token.trim();
            if(index.containsKey(token))
            {
                indices = index.get(token);
            }
            else
            {
                indices = new ArrayList();
            }

            indices.add(i + 1);
            index.put(token, indices);
        }

        printIndex();
    }
}