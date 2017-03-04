package lab1;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import java.io.IOException;
import java.util.HashMap;

public class Search {
    private static HashMap<String, Result> cache = new HashMap<>();
    private static IndexSearcher searcher;
    public static Highlight highlighter;

    private static void initSearch() throws IOException {
        IndexReader reader = DirectoryReader.open(Settings.getFSindexDir());
        searcher = new IndexSearcher(reader);
        SimilarDocs.initSimilarDocs(reader, searcher);
    }


    public static Result search(String queryStr) throws IOException, InvalidTokenOffsetsException {
        /**
         * Search
         */

        if (searcher == null) initSearch();
        queryStr = queryStr.trim();

        if (queryStr.equals("")) return null;
        Query q = QParser.parse(queryStr);
        // We must update highlighter
        highlighter = new Highlight(searcher, q);

        if (cache.containsKey(queryStr)) return cache.get(queryStr);

        TopDocs topDocs = searcher.search(q, searcher.getIndexReader().maxDoc());

        //

        Result results = new Result(topDocs.totalHits);

        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            int docID = topDocs.scoreDocs[i].doc;
            results.docIDs.add(docID);
        }

        // WARNING: we assume that maxResults is a constant so that the cache will stay clean
        cache.put(queryStr, results);
        return results;
    }
}
