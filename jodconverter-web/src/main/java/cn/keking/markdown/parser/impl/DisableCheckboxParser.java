package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class DisableCheckboxParser extends AbstractWithEndTagParser{
    @Override public MARK mark() {
        return MARK.DISABLE_CHECK_BOX;
    }
}
