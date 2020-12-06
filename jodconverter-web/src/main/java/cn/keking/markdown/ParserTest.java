package cn.keking.markdown;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParserTest {

    public static void main(String[] args) throws IOException {
//        String markContext = "\n" +
//            "- 1\n" +
//            "   - 1.1\n" +
//            "      - 1.1.1\n" +
//            "         - 1.1.1.1\n" +
//            "         - 1.1.1.1.1\n" +
//            "         - 1.1.1.1.2\n" +
//            "      - 1.1.2\n" +
//            "         - 1.1.2.1\n" +
//            "         - 1.1.2.2\n" +
//            "   - 1.2\n" +
//            "   - 1.3\n" +
//            "- 2\n" +
//            "- 3\n" +
//            "1. 1\n" +
//            "2. 2\n" +
//            "3. 3\n" +
//            "   1. 3.1\n" +
//            "       1. 3.1.1\n" +
//            "       2. 3.1.2\n" +
//            "       3. 3.1.3\n" +
//            "   2. 3.2\n" +
//            "       1. 3.2.1\n" +
//            "这时是3.2.1内容**嵌套的加粗文本**==嵌套的加粗文本==\n" +
//            "4. 4\n" +
//            "5. 5\n" +
//            "5的列表内容\n" +
//            "\n" +
//            "表头1 | 表头2\n" +
//            "---|---\n" +
//            "内容自定义<br/>回车行吗？| 2222\n" +
//            "row 2 col 1 | row 2 col 2\n" +
//            "\n" +
//            "\n" +
//            "```\n" +
//            "math 数学表达式暂不支持，当做代码处理\n" +
//            "E = mc^2\n" +
//            "\n" +
//            "E=MC^5\n" +
//            "E=mc-5\n" +
//            "```\n" +
//            "\n" +
//            "```\n" +
//            "print(\"代码段\")\n" +
//            "printf(\"引号==是红==色 不允许嵌套\")\n" +
//            "\n" +
//            "p()有括号认为是方法 \n" +
//            "class A {}\n" +
//            "```\n" +
//            "### H3\n" +
//            ">引用\n" +
//            "引用的内容\n" +
//            "引用的内容下一行\n" +
//            "\n" +
//            "引用结束...\n" +
//            "\n" +
//            "# H1\n" +
//            "回车结束...\n" +
//            "\n" +
//            "\n" +
//            "*斜体字 ++下划线++==高亮==**加粗(都支持嵌套哟)***\n" +
//            "\n" +
//            "![image](http://note.youdao.com/favicon.ico)\n" +
//            "\n" +
//            "- [ ] 这里![图标](http://note.youdao.com/favicon.ico)是*复选框*\n" +
//            "- [x] 这里![图标](http://note.youdao.com/favicon.ico)==是复选框==\n" +
//            "\n" +
//            "横线支持\n" +
//            "---\n" +
//            "\n" +
//            "\n" +
//            "    TAB\n" +
//            "\n" +
//            "    TAB END\n" +
//            "回车顶头tab 结束\n" +
//            "\n" +
//            "# h1\n" +
//            "## h2\n" +
//            "### h3\n" +
//            "#### h4 \n" +
//            "##### h5\n" +
//            "###### h6\n" +
//            "\n" +
//            ">==引用==这里也是可以==嵌套==使用的\n" +
//            "\n" +
//            "代码引用部分\n" +
//            "```\n" +
//            "  @Override public MarkEntity validate(MarkContext markContext) {\n" +
//            "        String line;\n" +
//            "        //-4 represent tab key\n" +
//            "        int pointer=markContext.getCurrentPointer()+this.mark().getStart().length()-4;\n" +
//            "        int start=pointer;\n" +
//            "        while ((line = markContext.readLine(pointer)).startsWith(\"    \")) {\n" +
//            "            pointer += line.length();\n" +
//            "        }\n" +
//            "        MarkEntity markEntity = MarkEntity.createCurrentMark(this.mark(), pointer);\n" +
//            "        markEntity.setContent(markContext.getContent().substring(start, pointer).replaceAll(\"\\n+\",\"<br/>\"));\n" +
//            "        return markEntity;\n" +
//            "    }\n" +
//            "```\n" +
//            "\n" +
//            "*我*==爱==++北京++==天安门==，**天安门**==上阳==升";
//        String res = MarkdownParser.parse(markContext);
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\PXC\\Desktop\\out.html")));
//        bufferedWriter.write(res);
//        bufferedWriter.close();
        File f = new File("D:\\Project\\kkFileView\\jodconverter-web\\src\\main\\file\\demo\\embedtest.md");
        MarkdownParser.parse(f, new File("D:\\Project\\kkFileView\\jodconverter-web\\src\\main\\file\\demo\\embedtest.html"));
    }
}
