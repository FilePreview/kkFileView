package cn.keking.markdown;

import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.parser.MarkParser;
import cn.keking.markdown.parser.impl.MarkdownParserComposite;
import cn.keking.web.controller.FileListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MarkdownParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkdownParser.class);

    private MarkdownParser(){
        throw new IllegalStateException("Utility class");
    };
    private static final String PREFIX =
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

    private static final String SUFFIX =
            "\t\t\t</article>\n" +
                    "\t\t</body>\n" +
                    "</html>\n";

    public static String parse(String md) {
        MarkContext markContext = new MarkContext(md);
        MarkParser markParser = MarkdownParserComposite.getInstance();
        markParser.parse(markContext);
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(PREFIX);
        sbuf.append(markContext.getHtml());
        sbuf.append(SUFFIX);
        return sbuf.toString();
    }

    public static boolean parse(File mdFile, File tarFile) throws FileNotFoundException {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            StringBuilder sbuf = new StringBuilder();
            bufferedReader = new BufferedReader(new FileReader(mdFile));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sbuf.append(s + "\n");
            }
            bufferedReader.close();
            String res = parse(sbuf.toString());
            bufferedWriter = new BufferedWriter(new FileWriter(tarFile));
            bufferedWriter.write(res);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("this file is not found.");
        } catch (IOException e) {
            return false;
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOGGER.error("IOException:",e);
                }
            }
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    LOGGER.error("IOException:",e);
                }
            }
        }
        return true;
    }

    public static boolean parse(String md, File tarFile) throws FileNotFoundException {
        BufferedWriter bufferedWriter = null;
        try {
            String res = parse(md);
            bufferedWriter = new BufferedWriter(new FileWriter(tarFile));
            bufferedWriter.write(res);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("this file is not found.");
        } catch (IOException e) {
            return false;
        } finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    LOGGER.error("IOException:",e);
                }
            }
        }
        return true;
    }
}
