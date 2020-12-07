package cn.keking.markdown.parser.impl;

import cn.keking.markdown.constant.magic.CharSymbol;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;
import cn.keking.markdown.parser.MarkParser;

public abstract class AbstractWithEndTagParser implements MarkParser {

    @Override public boolean detectStartMark(MarkContext markContext) {
        if (markContext.getContent().startsWith(this.mark().getStart(), markContext.getCurrentPointer())) {
            return true;
        }
        int currentPointer = markContext.getCurrentPointer();
        String content = markContext.getContent();
        int firstBlankIndex=markContext.detectFirstBlank(this.mark(),currentPointer);
        if(firstBlankIndex==-1){
            return false;
        }
        if (!content.startsWith(this.mark().getStart(), firstBlankIndex)) {
            return false;
        }
        markContext.setPointer(firstBlankIndex);
        return true;
    }

    @Override public MarkEntity validate(MarkContext markContext) {
        int startIndex = markContext.getCurrentPointer() + this.mark().getStart().length();
        int endMarkIndex = markContext.getContent().indexOf(this.mark().getEnd(), startIndex);
        if (endMarkIndex <= 1) {
            return null;
        }
        if (markContext.getContent().charAt(startIndex) == CharSymbol.ENTER || markContext.getContent().charAt(endMarkIndex - 1) == '\n') {
            return null;
        }

        if (endMarkIndex <= startIndex) {
            return null;
        }
        String content = markContext.getContent().substring(markContext.getCurrentPointer()
            + this.mark().getStart().length(), endMarkIndex);
        if (content.contains("\n\n")) {
            return null;
        }
        MarkEntity markEntity = MarkEntity.createCurrentMark(this.mark(), endMarkIndex);
        markEntity.setContent(content);
        return markEntity;
    }

    @Override public void parse(MarkContext markContext) {
        String content = markContext.getCurrentMark().getContent();
        //如果包含复杂结构，至少需要两个字符
        if (content.length() <= 2 || MarkContext.CHILD_MARK_PARSER.get(this.mark()) == null) {
            markContext.append(String.format(this.mark().getFormat(), content));
            markContext.setPointer(markContext.getCurrentMark().getEnd());
            if (this.mark().getEnd() != null) {
                markContext.skipPointer(this.mark().getEnd().length());
            }
            return;
        }

        String innerHTML = markContext.getInnerHtml(this.mark(),content);
        markContext.append(String.format(this.mark().getFormat(), innerHTML));
        markContext.setPointer(markContext.getCurrentMark().getEnd() + this.mark().getEnd().length());
    }
}
