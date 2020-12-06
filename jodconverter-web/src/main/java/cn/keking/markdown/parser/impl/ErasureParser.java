package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class ErasureParser extends AbstractWithEndTagParser{

    @Override public MARK mark() {
        return MARK.ERASURE;
    }
}
