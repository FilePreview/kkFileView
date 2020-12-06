package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class ImageParser extends AbstractWithUrlParser {
    @Override
    public MARK mark() {
        return MARK.IMAGE;
    }
}
