package lab1;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import java.io.*;

/**
 * Created by suquark on 16/12/11.
 * The entrypoint for Index creating
 */
public class CreateIndex {

    private static IndexWriter indexWriter;

    /**
     * Read file
     */
    private static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream is = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            String line = reader.readLine(); // read a line
            if (line == null) break;
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        is.close();
        return sb.toString();
    }

    private static void processDoc(String filename) throws IOException {
        /**======================== CreateIndex ===============================
         *
         *
         */
        String html = readFile(filename);
        for (String buffer : Html2Text.matchDoc(html)) {
            String url = Html2Text.matchLabel(buffer, "url");
            String description = Html2Text.match(buffer, "description");
            String keyword = Html2Text.match(buffer, "keywords");
            String title = Html2Text.matchLabel(buffer, "title");
            String publishid = Html2Text.match(buffer, "\"publishid\"");
            String subjectid = Html2Text.match(buffer, "\"subjectid\"");
            String content = Html2Text.getcontent(buffer);
            Document document = new Document();
            document.add(new Field("url", url, StringField.TYPE_STORED));
            document.add(new Field("description", description, StringField.TYPE_STORED));
            document.add(new Field("keywords", keyword, StringField.TYPE_STORED));
            document.add(new Field("title", title, StringField.TYPE_STORED));
            document.add(new Field("publishid", publishid, StringField.TYPE_STORED));
            document.add(new Field("subjectid", subjectid, StringField.TYPE_STORED));
            document.add(new Field("content", content, TextField.TYPE_STORED));
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }

    public static void main(String[] args) throws IOException {
        indexWriter = new IndexWriter(Settings.getFSindexDir(), Settings.indexWriterConfig);
        processDoc("新浪新闻/2012.q1.txt");
        processDoc("新浪新闻/2012.q2.txt");
        processDoc("新浪新闻/2012.q3.txt");
        processDoc("新浪新闻/2012.q4.txt");
        processDoc("新浪新闻/2013.q1.txt");
    }
}
