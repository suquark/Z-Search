package lab1;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by suquark on 2016/12/31.
 */
public class Highlight {
    private Highlighter highlighter;
    private static IndexSearcher searcher;

    public Highlight(IndexSearcher s, Query q) {
        searcher = s;
        // highlight keywords
        SimpleHTMLFormatter simpleHTMLFormatter
                = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(q));
        highlighter.setTextFragmenter(new SimpleFragmenter(300));
    }

    public String format(Document doc, String fieldName) throws IOException, InvalidTokenOffsetsException {
        String fieldContent = doc.getField(fieldName).stringValue();
        String fragFieldContent = highlighter.getBestFragment(Settings.analyzer, fieldName, fieldContent);
        return fragFieldContent == null ? fieldContent : fragFieldContent;
    }

    public String docFormatTitle(int docID) throws IOException, InvalidTokenOffsetsException {
        Document doc = searcher.doc(docID);
        return format(doc, "title");
    }

    public HashMap<String, String> docFormat(int docID) throws IOException, InvalidTokenOffsetsException {
        Document doc = searcher.doc(docID);
        HashMap<String, String> docInfo = new HashMap<>();

        String url = doc.getField("url").stringValue();
        docInfo.put("url", url);
        docInfo.put("publishTime", Html2Text.getTime(url));
        // highlighting
        docInfo.put("content", format(doc, "content"));
        docInfo.put("title", format(doc, "title"));
        return docInfo;
    }
}
