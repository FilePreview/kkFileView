package cn.keking.markdown.parser;

import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.MarkEntity;

public interface MarkParser {
    boolean detectStartMark(MarkContext markContext);
    MarkEntity validate(MarkContext mark);
    void parse(MarkContext markContext);
    MARK mark();
}
