package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class HyperLinkParser extends AbstractWithUrlParser {

    @Override
    public MARK mark() {
        return MARK.HYPER_LINK;
    }
}
