package lab1;

import java.io.IOException;

import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;

public class SpellCheck {

    /**
     * SpellCheck
     */

    private static SpellChecker spellchecker;

    public static void initSpellChecker() throws IOException {
        if (spellchecker == null) {
            System.err.print("Initializing SpellChecker... \n");
            spellchecker = new SpellChecker(new RAMDirectory());
            spellchecker.indexDictionary(new PlainTextDictionary(Settings.dicPath),
                    Settings.indexWriterConfig, true);
            System.err.print("[ OK ] SpellChecker loaded \n");
        }
    }

    public static String suggest(String word) throws IOException {
        String correct = null;

        if (spellchecker == null) {
            initSpellChecker();
        }

        String[] suggests = spellchecker.suggestSimilar(word, 1);
        if (suggests != null) {
            for (String sug : suggests) {
                if (!sug.equals(word)) {

                    correct = sug;
                    //return correct;
                } else {
                    return null;
                }
            }
        }

        return correct;
    }
}

