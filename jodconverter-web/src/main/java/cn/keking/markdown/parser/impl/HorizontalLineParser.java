package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;

public class HorizontalLineParser extends AbstractWithEndTagParser {


    @Override
    public MarkEntity validate(MarkContext markContext) {
        String title = markContext.readLine(markContext.getCurrentPointer() + 1);
        String line = markContext.readLine(markContext.getCurrentPointer()+ title.length(),2);
        if (line.equals(this.mark().getEnd())) {
            MarkEntity markEntity= MarkEntity.createCurrentMark(this.mark(),markContext.getCurrentPointer()+title.length());
            markEntity.setTitle(title);
            return markEntity;
        }
        return null;
    }

    @Override
    public void parse(MarkContext markContext) {
        String title=markContext.getContent().substring(markContext.getCurrentPointer()+1,markContext.getCurrentMark().getEnd());
        markContext.append(String.format(this.mark().getFormat(), title));
        markContext.setPointer(markContext.getCurrentMark().getEnd()+this.mark().getEnd().length());
    }


    @Override
    public MARK mark() {
        return MARK.HORIZONTAL_LINE;
    }
}
