package cn.keking.markdown.parser.impl;

import cn.keking.markdown.constant.CONSTANT;
import cn.keking.markdown.constant.magic.CHAR_SYMBOL;
import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.TagListEntity;
import cn.keking.markdown.utility.CollectionsUtility;
import cn.keking.markdown.utility.StringUtility;

import java.util.List;

public class UnorderedListParser extends AbstractListParser {

    @Override
    public boolean detectStartMark(MarkContext markContext) {
        int tempPointer = markContext.getCurrentPointer();
        String content = markContext.getContent();

        int firstBlankIndex = markContext.detectFirstBlank(this.mark(), tempPointer);
        if (firstBlankIndex == -1) {
            return false;
        }

        //the next letter must be -
        tempPointer = firstBlankIndex + 1;
        if (tempPointer >= markContext.getContentLength() || content.charAt(tempPointer) != CHAR_SYMBOL.HORIZON_LINE) {
            return false;
        }
        //next letter must by ' '
        tempPointer++;
        if (tempPointer >= markContext.getContentLength() || content.charAt(tempPointer) != CHAR_SYMBOL.BLANK) {
            return false;
        }
        return true;
    }

    @Override
    protected TagListEntity validate(MarkContext markContext, TagListEntity currentEntity, String line) {

        if (line.equals(CONSTANT.ENTER_TEXT_N)) {
            return currentEntity;
        }

        String innerLine = line.trim();

        //the next letter must be -
        if (innerLine.charAt(0) != CHAR_SYMBOL.HORIZON_LINE) {
            currentEntity.setContent(currentEntity.getContent() + markContext.getInnerHtml(this.mark(), innerLine));
            return currentEntity;
        }
        //the next letter must be ' '
        if (innerLine.charAt(1) != CHAR_SYMBOL.BLANK) {
            currentEntity.setContent(currentEntity.getContent() + markContext.getInnerHtml(this.mark(), innerLine));
            return currentEntity;
        }

        int indent = StringUtility.getPrefixCount(line, "   ");
        TagListEntity parent = this.getParent(currentEntity, indent);
        if (parent == null) {
            currentEntity.setContent(currentEntity.getContent() + innerLine);
            return currentEntity;
        }

        TagListEntity newEntity = new TagListEntity();
        newEntity.setParent(parent);
        newEntity.setIndent(indent);
        newEntity.setTitle("");

        String innerContent = innerLine.substring(2).trim();
        String innerHTML = markContext.getInnerHtml(this.mark(), innerContent);
        newEntity.setContent(innerHTML);
        parent.getChildren().add(newEntity);
        return newEntity;
    }

    private String parseTagList(List<TagListEntity> tags, Integer intent) {
        StringBuilder ol = new StringBuilder();
        for (TagListEntity tag : tags) {
            ol.append(String.format("<li>%1$s</li>\n", tag.getContent()));
            if (!CollectionsUtility.isNullOrEmpty(tag.getChildren())) {
                ol.append(this.parseTagList(tag.getChildren(), tag.getIndent()));
            }
        }
        if (ol.length() > 0) {
            ol.insert(0, String.format("<ul class=\"ul%1$s\">\n", intent == null ? "" : "_" + intent));
            ol.append("</ul>\n");
        }
        return ol.toString();
    }

    @Override
    public void parse(MarkContext markContext) {
        List<TagListEntity> tagListEntities = markContext.getCurrentMark().getTagListEntities();
        markContext.append(this.parseTagList(tagListEntities, null));
        markContext.setPointer(markContext.getCurrentMark().getEnd());
    }

    @Override
    public MARK mark() {
        return MARK.UNORDERED_LIST;
    }
}
