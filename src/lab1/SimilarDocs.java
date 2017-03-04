package lab1;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by suquark on 2016/12/31.
 */
public class SimilarDocs {
    private static MoreLikeThis mlt;
    private static IndexReader reader;
    private static IndexSearcher searcher;

    private static HashMap<Integer, Document> cache = new HashMap<>();

    static void initSimilarDocs(IndexReader idxReader, IndexSearcher idxSearcher) {
        reader = idxReader;
        searcher = idxSearcher;
        // Similar Documents
        mlt = new MoreLikeThis(reader);
        mlt.setFieldNames(new String[]{"content", "keywords"}); // 相似性搜索
        mlt.setMinTermFreq(0);
        mlt.setMinDocFreq(0);
        mlt.setAnalyzer(Settings.analyzer);
    }

    public static String[] searchSimilarDoc(int docID) throws IOException {
        Document similarDoc;
        // cache
        if (cache.containsKey(docID)) {
            // System.err.print("Similar doc " + docID + " hit!\n");
            similarDoc = cache.get(docID);
        } else {
            Query more = mlt.like(docID);
            TopDocs similarDocs = searcher.search(more, 2);
            int similarDocId = similarDocs.scoreDocs[similarDocs.scoreDocs.length - 1].doc;
            similarDoc = similarDocId != docID ? reader.document(similarDocId) : null;
            cache.put(docID, similarDoc);
        }

        if (similarDoc != null) {
            return new String[]{similarDoc.getField("title").stringValue(),
                    similarDoc.getField("url").stringValue()};

        } else {
            return new String[]{null, null};
        }
    }
}

