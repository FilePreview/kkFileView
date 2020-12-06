package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class H5Parser extends AbstractWithEndTagParser {


    @Override
    public MARK mark() {
        return MARK.H5;
    }
}
