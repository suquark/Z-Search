package lab1;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

/**
 * Created by suquark on 2016/12/31.
 */
public class QParser {
    // Query Parser
    private static MultiFieldQueryParser mp =
            new MultiFieldQueryParser(
                    new String[]{"content", "keywords", "title", "description"},
                    Settings.analyzer
            );

    static Query parse(String queryStr) {
        try {
            return mp.parse(queryStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
