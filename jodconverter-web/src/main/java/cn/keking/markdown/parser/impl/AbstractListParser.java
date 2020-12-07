package cn.keking.markdown.parser.impl;

import cn.keking.markdown.constant.CONSTANT;
import cn.keking.markdown.constant.magic.CharSymbol;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;
import cn.keking.markdown.mark.TagListEntity;
import cn.keking.markdown.parser.MarkParser;

public abstract class AbstractListParser implements MarkParser {
    protected abstract TagListEntity validate(MarkContext markContext, TagListEntity currentEntity, String line);


    @Override
    public MarkEntity validate(MarkContext markContext) {
        TagListEntity current = new TagListEntity();
        current.setIndent(-1);
        TagListEntity parentEntity = current;
        //skip first enter
        markContext.skipPointer(1);
        do {
            String line = markContext.readLine(markContext.getCurrentPointer());
            if (line.equals(CONSTANT.ENTER_TEXT_N) && markContext.getCurrentPointer() > 0 && markContext.getContent().charAt(markContext.getCurrentPointer() - 1) == CharSymbol.ENTER) {
                markContext.skipPointer(line.length());
                break;
            }
            if (markContext.detectNextMark(this.mark())) {
                break;
            }
            markContext.skipPointer(line.length());
            current = this.validate(markContext, current, line);
        }
        while (true);
        MarkEntity markEntity = MarkEntity.createCurrentMark(this.mark(), markContext.getCurrentPointer());
        markEntity.setTagListEntities(parentEntity.getChildren());
        markEntity.setNextEntity(markContext.getTempNextMark());
        markContext.setTempNextMark(null);
        return markEntity;
    }

    public TagListEntity getParent(TagListEntity current, Integer intent) {
        //brother
        if (intent == current.getIndent()) {
            return current.getParent();
        }
        //parent
        if (intent < current.getIndent()) {
            int count = current.getIndent() - intent;
            do {
                current = current.getParent();
            }
            while (count-- > 0);
            return current;
        }
        //children
        if (intent - current.getIndent() == 1) {
            return current;
        }
        return null;
    }
}
