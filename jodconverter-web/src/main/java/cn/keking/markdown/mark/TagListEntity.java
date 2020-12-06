package cn.keking.markdown.mark;

import java.util.ArrayList;
import java.util.List;

public class TagListEntity {
    private List<TagListEntity> children=new ArrayList<TagListEntity>(16);
    private int indent;
    private TagListEntity parent;
    private String title;
    private String content;

    public List<TagListEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TagListEntity> children) {
        this.children = children;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public TagListEntity getParent() {
        return parent;
    }

    public void setParent(TagListEntity parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void  addChild(TagListEntity tagListEntity){
        this.children.add(tagListEntity);
    }
}
