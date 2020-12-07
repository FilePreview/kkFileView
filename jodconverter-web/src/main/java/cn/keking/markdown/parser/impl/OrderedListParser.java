package cn.keking.markdown.parser.impl;

import cn.keking.markdown.constant.CONSTANT;
import cn.keking.markdown.constant.magic.CharSymbol;
import cn.keking.markdown.mark.MARK;
import cn.keking.markdown.mark.MarkContext;
import cn.keking.markdown.mark.TagListEntity;
import cn.keking.markdown.utility.CollectionsUtility;
import cn.keking.markdown.utility.StringUtility;

import java.util.List;

/**
 * @author harry
 */
public class OrderedListParser extends AbstractListParser {

    @Override
    public boolean detectStartMark(MarkContext markContext) {
        int tempPointer = markContext.getCurrentPointer();
        String content = markContext.getContent();

        int firstBlankIndex = markContext.detectFirstBlank(this.mark(), tempPointer);
        if (firstBlankIndex == -1) {
            return false;
        }
        //the next letter must be digit
        tempPointer = firstBlankIndex + 1;
        String digit = StringUtility.getDigit(content, tempPointer);
        if (digit.length() == 0) {
            return false;
        }
        tempPointer += digit.length();
        //the next letter must be .
        if (content.charAt(tempPointer) != CharSymbol.DOT) {
            return false;
        }
        //next letter must by ' '
        tempPointer++;
        return content.charAt(tempPointer) == CharSymbol.BLANK;
    }

    @Override
    protected TagListEntity validate(MarkContext markContext, TagListEntity currentEntity, String line) {
        if (line.equals(CONSTANT.ENTER_TEXT_N)) {
            return currentEntity;
        }

        String innerLine = line.trim();
        String digit = StringUtility.getDigit(innerLine, 0);
        //not start with digit
        if (digit.length() == 0) {
            currentEntity.setContent(currentEntity.getContent() + markContext.getInnerHtml(this.mark(), innerLine));
            return currentEntity;
        }

        //all digital
        if (digit.length() == innerLine.length()) {
            currentEntity.setContent(currentEntity.getContent() + markContext.getInnerHtml(this.mark(), innerLine));
            return currentEntity;
        }

        int tempPointer=digit.length();
        //the next letter must be .
        if (tempPointer>=innerLine.length()||innerLine.charAt(tempPointer) != CharSymbol.DOT) {
            currentEntity.setContent(currentEntity.getContent() + markContext.getInnerHtml(this.mark(), innerLine));
            return currentEntity;
        }

        //the next letter must be ' '
        tempPointer++;
        if (tempPointer>=innerLine.length()||innerLine.charAt(tempPointer) != CharSymbol.BLANK) {
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
        newEntity.setTitle(digit);

        String innerContent = innerLine.substring(digit.length() + 1).trim();

        if (innerContent.length() > 2) {
            String innerHTML = markContext.getInnerHtml(this.mark(), innerContent);
            newEntity.setContent(innerHTML);
        } else {
            newEntity.setContent(innerContent);
        }
        parent.getChildren().add(newEntity);
        return newEntity;
    }

    private String parseTagList(List<TagListEntity> tags, Integer intent) {
        StringBuilder ol = new StringBuilder();
        for (TagListEntity tag : tags) {
            ol.append(String.format("<li>%1$s</li>%n", tag.getContent()));
            if (!CollectionsUtility.isNullOrEmpty(tag.getChildren())) {
                ol.append(this.parseTagList(tag.getChildren(), tag.getIndent()));
            }
        }
        if (ol.length() > 0) {
            ol.insert(0, String.format("<ol class=\"ol%1$s\">%n", intent == null ? "" : "_" + intent));
            ol.append("</ol>\n");
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
        return MARK.ORDERED_LIST;
    }
}
