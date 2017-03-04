package lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suquark on 16/12/11.
 */
public class Result {
    //    public List<String> contents = new ArrayList<>();
    //    public List<String> urls = new ArrayList<>();
    //    public List<String> titles = new ArrayList<>();
    //    public List<String> publishTime = new ArrayList<>();
    //    public List<String> moreLike = new ArrayList<>();
    //    public List<String> moreURL = new ArrayList<>();
    public List<Integer> docIDs = new ArrayList<>();
    public int totalDocs = 0;

    public Result() {

    }

    public Result(int total_docs) {
        totalDocs = total_docs;
    }
}
