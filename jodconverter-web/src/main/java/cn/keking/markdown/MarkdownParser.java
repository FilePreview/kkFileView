package cn.keking.markdown;

import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.parser.MarkParser;
import cn.keking.markdown.parser.impl.MarkdownParserComposite;

import java.io.*;

public class MarkdownParser {

    private static final String prefix =
            "<!doctype html>\n" +
                     "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"utf-8\">\n" +
                    "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, minimal-ui\">\n" +
                    "\t\t<title>GitHub Markdown CSS demo</title>\n" +
                    "\t\t<link rel=\"stylesheet\" href=\"css/github-markdown.css\">\n" +
                    "\t\t<style>\n" +
                    "\t\t\tbody {\n" +
                    "\t\t\t\tbox-sizing: border-box;\n" +
                    "\t\t\t\tmin-width: 200px;\n" +
                    "\t\t\t\tmax-width: 980px;\n" +
                    "\t\t\t\tmargin: 0 auto;\n" +
                    "\t\t\t\tpadding: 45px;\n" +
                    "\t\t\t}\n" +
                    "\t\t</style>\n" +
                    "\t</head>\n" +
                    "\t\t<body>\n" +
                    "\t\t\t<article class=\"markdown-body\">\n";

    private static final String suffix =
            "\t\t\t</article>\n" +
                    "\t\t</body>\n" +
                    "</html>\n";

    public static String parse(String md) {
        MarkContext markContext = new MarkContext(md);
        MarkParser markParser = MarkdownParserComposite.getInstance();
        markParser.parse(markContext);
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(prefix);
        sbuf.append(markContext.getHtml());
        sbuf.append(suffix);
        return sbuf.toString();
    }

    public static boolean parse(File mdFile, File tarFile) throws FileNotFoundException {
        try {
            StringBuilder sbuf = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(mdFile));
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                sbuf.append(s + "\n");
            }
            bufferedReader.close();
            String res = parse(sbuf.toString());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tarFile));
            bufferedWriter.write(res);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("this file is not found.");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean parse(String md, File tarFile) throws FileNotFoundException {
        try {
            String res = parse(md);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tarFile));
            bufferedWriter.write(res);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("this file is not found.");
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
