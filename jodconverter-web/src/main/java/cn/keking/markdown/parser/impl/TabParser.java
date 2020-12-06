package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;

public class TabParser extends AbstractWithEndTagParser {

    @Override public MarkEntity validate(MarkContext markContext) {
        String line;
        //-4 represent tab key
        int pointer=markContext.getCurrentPointer()+this.mark().getStart().length()-4;
        int start=pointer;
        while ((line = markContext.readLine(pointer)).startsWith("    ")) {
            pointer += line.length();
        }
        MarkEntity markEntity = MarkEntity.createCurrentMark(this.mark(), pointer);
        markEntity.setContent(markContext.getContent().substring(start, pointer).replaceAll("\n+","<br/>"));
        return markEntity;
    }

    @Override
    public MARK mark() {
        return MARK.TAB;
    }
}
