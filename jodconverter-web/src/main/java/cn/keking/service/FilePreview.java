package cn.keking.service;

import cn.keking.model.FileAttribute;
import org.springframework.ui.Model;

/**
 * 转换文件的接口
 */
public interface FilePreview {
    /**
     *
     * @param url 要预览的文件的url
     * @return 对应文件格式的预览界面视图名称
     */
    String filePreviewHandle(String url, Model model, FileAttribute fileAttribute) throws InterruptedException;
}
