package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class H2Parser extends AbstractWithEndTagParser {

    @Override public MARK mark() {
        return MARK.H2;
    }
}
