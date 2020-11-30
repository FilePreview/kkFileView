package cn.keking.model;

/**
 * Created by kl on 2018/1/17.
 * Content :文件类型，文本，office，压缩包等等
 */
public enum FileType {
    picture("pictureFilePreviewImpl"),
    compress("compressFilePreviewImpl"),
    office("officeFilePreviewImpl"),
    simText("simTextFilePreviewImpl"),
    pdf("pdfFilePreviewImpl"),
    other("otherFilePreviewImpl"),
    media("mediaFilePreviewImpl"),
    cad("cadFilePreviewImpl");

    private final String instanceName;
    /**
     * Author：houzheng
     * Date：11-18
     * 构造器
     *
     */

    FileType(String instanceName){
        this.instanceName=instanceName;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取实例名字
     *
     */

    public String getInstanceName() {
        return instanceName;
    }

}
