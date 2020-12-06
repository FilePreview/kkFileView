package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class QuoteParser extends AbstractWithEndTagParser {
    @Override public MARK mark() {
        return MARK.QUOTE;
    }
}
