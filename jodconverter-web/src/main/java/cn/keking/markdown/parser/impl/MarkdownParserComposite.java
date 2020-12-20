package cn.keking.markdown.parser.impl;

import cn.keking.markdown.constant.magic.CharSymbol;
import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;
import cn.keking.markdown.parser.MarkParser;

public class MarkdownParserComposite implements MarkParser {
    private static MarkdownParserComposite instance = new MarkdownParserComposite();

    private MarkdownParserComposite() {
    }

    public static MarkdownParserComposite getInstance() {
        return instance;
    }

    @Override public boolean detectStartMark(MarkContext markContext) {
        return false;
    }

    @Override
    public MarkEntity validate(MarkContext mark) {
        return null;
    }

    @Override
    public void parse(MarkContext markContext) {
        //if first char is not \n then fill
        if (markContext.getParentMark() == null&&markContext.getContent().charAt(0)!= CharSymbol.ENTER) {
            markContext.setContent(CharSymbol.ENTER + markContext.getContent());
        }
        do {
            //detect start mark
            if (markContext.getCurrentMark()!=null&&markContext.getCurrentMark().getNextEntity()!= null) {
                markContext.setCurrentMark(markContext.getCurrentMark().getNextEntity());
            } else {
                markContext.setCurrentMark(null);
                markContext.detectCurrentMark(markContext.getParentMark());
            }
            if (markContext.getCurrentMark() != null) {
                MarkContext.MARK_PARSER_MAP.get(markContext.getCurrentMark().getMark()).parse(markContext);
                continue;
            }

            MarkParser literaryParse = MarkContext.MARK_PARSER_MAP.get(MARK.LITERARY);
            MarkEntity markEntity = literaryParse.validate(markContext);
            if (markEntity != null) {
                markContext.setCurrentMark(markEntity);
                //按文本处理
                literaryParse.parse(markContext);
            }
        } while (markContext.getCurrentPointer() < markContext.getContentLength());
    }

    @Override
    public MARK mark() {
        return null;
    }
}
