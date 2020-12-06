package cn.keking.markdown.parser.impl;

import cn.keking.markdown.constant.magic.SYMBOL;
import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;

import java.util.ArrayList;
import java.util.List;

public class TableParser extends AbstractWithEndTagParser {

    @Override
    public MarkEntity validate(MarkContext markContext) {
        int pointer = markContext.getCurrentPointer() + this.mark().getStart().length();
        String title = markContext.readLine(pointer);
        if (!title.contains(SYMBOL.VERTICAL_LINE)) {
            return null;
        }
        pointer += title.length();
        String split = markContext.readLine(pointer);
        if (!split.contains(SYMBOL.VERTICAL_LINE)) {
            return null;
        }

        pointer += split.length();
        String tdLine = markContext.readLine(pointer);
        if (!tdLine.contains(SYMBOL.VERTICAL_LINE)) {
            return null;
        }

        String verticalLineSplit="\\|";
        String[] titleArray = title.trim().split(verticalLineSplit);
        String[] splitArray = split.trim().split(verticalLineSplit);
        String[] tdArray = tdLine.trim().split(verticalLineSplit);

        if (titleArray.length != splitArray.length || titleArray.length != tdArray.length) {
            return null;
        }

        String matchSplit = "---";
        for (String s : splitArray) {
            if (!matchSplit.equals(s)) {
                return null;
            }
        }

        List<String[]> tdList = new ArrayList<String[]>(32);
        tdList.add(tdArray);
        pointer += tdLine.length();
        do {
            tdLine = markContext.readLine(pointer);
            tdArray = tdLine.split(verticalLineSplit);
            if (tdArray.length != titleArray.length) {
                break;
            }
            tdList.add(tdArray);
            pointer += tdLine.length();
        }
        while (true);

        MarkEntity markEntity = MarkEntity.createCurrentMark(this.mark(), pointer);
        markEntity.setTdList(tdList);
        markEntity.setTitleArray(titleArray);
        return markEntity;
    }

    @Override
    public void parse(MarkContext markContext) {
        String[] titleArray = markContext.getCurrentMark().getTitleArray();
        List<String[]> tdList = markContext.getCurrentMark().getTdList();
        StringBuilder table = new StringBuilder(1024);
        table.append("<table><tr>");
        for (String title : titleArray) {
            table.append(String.format("<th>%1$s</th>", title));
        }
        table.append("<tr/>");
        for (String[] tdArray : tdList) {
            table.append("<tr>");
            for (String td : tdArray) {
                table.append(String.format("<td>%1$s</td>", td));
            }
            table.append("</tr>");
        }
        table.append("</table>");
        markContext.append(table.toString());
        markContext.setPointer(markContext.getCurrentMark().getEnd());
    }

    @Override
    public MARK mark() {
        return MARK.TABLE;
    }
}
