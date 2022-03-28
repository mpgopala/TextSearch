# TextSearch
Has removal of stopwords, a basic stemmer and construction of index.
One can run this and after the index construction is done, can query the 
document corpus for a query string which is taken as a conjunctive boolean 
query. It can also be used to save the term index to a file. The saved index
file can be read later from the same program and the queries can be run on top 
of that. This does not yet support updating the index file.

Typing <i>exit</i> as the query will exit the program.


# Tools Used
This project uses IntelliJ IDEA latest version as IDE.

# Running the app
```java -jar IRAssignment.jar```

## Arguments
    --docs      Space separated list of documents to parse within "" ex: "doc1.txt doc2.txt"
                If the path to the index file is given, then the term index would be written to that file. 
    --indexFile Path to the index file. 
                If a set of documents is supplied, the documents are parsed and the result is written to it.
                Otherwise, if the index file exists, the term index data is read from it. 
