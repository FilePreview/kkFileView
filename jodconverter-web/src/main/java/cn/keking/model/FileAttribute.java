package cn.keking.model;

/**
 * Created by kl on 2018/1/17.
 * Content :
 */
/**
 * Author：houzheng
 * Date：11-18
 * 文件属性类
 *
 */

public class FileAttribute {

    private FileType type;

    private String suffix;

    private String name;

    private String url;
    /**
     * Author：houzheng
     * Date：11-18
     * 构造函数
     *
     */

    public FileAttribute() {
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 构造器，四个变量：文件的类型、文件后缀、文件名字和文件的url
     *
     */

    public FileAttribute(FileType type, String suffix, String name, String url) {
        this.type = type;
        this.suffix = suffix;
        this.name = name;
        this.url = url;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取文件类型
     *
     */

    public FileType getType() {
        return type;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置文件类型
     *
     */

    public void setType(FileType type) {
        this.type = type;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取文件后缀
     *
     */

    public String getSuffix() {
        return suffix;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置文件后缀
     *
     */

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取文件名字
     *
     */

    public String getName() {
        return name;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置文件名字
     *
     */

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取文件url
     *
     */

    public String getUrl() {
        return url;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置文件url
     *
     */

    public void setUrl(String url) {
        this.url = url;
    }
}
