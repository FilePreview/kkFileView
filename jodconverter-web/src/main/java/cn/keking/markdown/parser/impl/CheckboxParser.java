package cn.keking.markdown.parser.impl;

import cn.keking.markdown.mark.MARK;

public class CheckboxParser extends AbstractWithEndTagParser  {

    @Override
    public MARK mark() {
        return MARK.CHECK_BOX;
    }
}
