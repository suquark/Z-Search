/**
 * Created by suquark on 16/12/11.
 */
package lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class Html2Text {

    public static String getTime(String url)  {
        // String reg = "[//s//S]*?/(\\d{4}-\\d{2}-\\d{2})/[//s//S]*?";
        Pattern p = Pattern.compile("[/sS]*?/(\\d{4}-\\d{2}-\\d{2})/[/sS]*?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        if (m.find()) return m.group(1);
        //reg = "[//s//S]*?/(\\d{4})(\\d{2})(\\d{2})/[//s//S]*?";
        p = Pattern.compile("[/sS]*?/(\\d{4})(\\d{2})(\\d{2})/[/sS]*?", Pattern.CASE_INSENSITIVE);
        m = p.matcher(url);
        return m.find() ? m.group(1) + "-" + m.group(2) + "-" + m.group(3) : null;
    }

    public static String getcontent(String inputString) {
        String htmlStr = inputString; // 含 html 标签的字符串
        Pattern p;
        Matcher m;
        List<String> filter = new ArrayList<String>(Arrays.asList("strong", "a", "span", "em", "iframe", "p"));
        for (String label : filter) {
            try {
                String reg = "<[/]*?" + label + ">";
                p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
                m = p.matcher(htmlStr);
                htmlStr = m.replaceAll("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }// 过滤标签
        try {
            String regEx_script = "(<meta [\\s\\S]*?>\n)|(<url>[\\s\\S]*?</url>\n)|(<title>[\\s\\S]*?</title>\n)|(<a [\\s\\S]*?>)";
            p = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m = p.matcher(htmlStr);
            htmlStr = m.replaceAll("");
        } catch (Exception e) {
            e.printStackTrace();
        }// 过滤头部信息
        return htmlStr;// 返回文本字符串
    }

    public static String matchLabel(String source, String element) {
        String result = "";
        String reg = "<" + element + "[ ^>]*?>([\\s\\S]*?)</" + element + ">";
        Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(source);
        if (m.find())
            result = m.group(1);
        return result;
    }

    public static List<String> matchDoc(String source) {
        List<String> result = new ArrayList<String>();
        String reg = "<doc[ ^>]*?>([\\s\\S]*?)</doc>";
        Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    public static String match(String source, String element) {
        String result = "";
        String reg = "<meta name=" + element + "[^<>]*?\\scontent=['\"]?(.*?)['\"]>";
        Matcher m = Pattern.compile(reg).matcher(source);
        if (m.find())
            result = m.group(1);
        return result;
    }
}
