package lab1;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by suquark on 2016/12/30.
 */
public class Settings {
    // Notice: absolute path, or relative path of tomcat/bin
    private static final Path indexDir = Paths.get("/Users/suquark/Code/webinfo/Index");
    static final Path dicPath = Paths.get("/Users/suquark/Code/webinfo/dictionary.dic");
    static final Analyzer analyzer = new IKAnalyzer();

    public static final int maxCountofResultsPerPage = 10;

    // expanded
    static final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

    static FSDirectory getFSindexDir() throws IOException {
        return FSDirectory.open(Settings.indexDir);
    }
}
