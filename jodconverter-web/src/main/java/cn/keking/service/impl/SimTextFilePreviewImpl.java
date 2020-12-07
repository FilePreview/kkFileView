package cn.keking.service.impl;

import cn.keking.config.ConfigConstants;
import cn.keking.markdown.MarkdownParser;
import cn.keking.model.FileAttribute;
import cn.keking.model.ReturnResponse;
import cn.keking.service.FilePreview;
import cn.keking.utils.DownloadUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by kl on 2018/1/17.
 * Content :处理文本文件
 */
@Service
public class SimTextFilePreviewImpl implements FilePreview {
    private static final String FILE_DIR = ConfigConstants.getFileDir();

    private final DownloadUtils downloadUtils;

    public SimTextFilePreviewImpl(DownloadUtils downloadUtils) {
        this.downloadUtils = downloadUtils;
    }

    @Override
    public String filePreviewHandle(String url, Model model, FileAttribute fileAttribute){
        String fileName = fileAttribute.getName();
        ReturnResponse<String> response = downloadUtils.downLoad(fileAttribute, fileName);
        if (0 != response.getCode()) {
            model.addAttribute("msg", response.getMsg());
            model.addAttribute("fileType",fileAttribute.getSuffix());
            return "fileNotSupported";
        }
        try {
            File originFile = new File(response.getContent());
            int index = fileName.lastIndexOf(".");
            String name = fileName.substring(0,index+1);
            String suffix = fileName.substring(index+1);
            if(suffix.equals("md")){
                File previewFile = new File(FILE_DIR+name + "html");
                MarkdownParser.parse(originFile,previewFile);
                index = response.getMsg().lastIndexOf(".");
                name = response.getMsg().substring(0,index+1);
                model.addAttribute("ordinaryUrl",name+"html");
            }else{
                File previewFile = new File(FILE_DIR + name + "txt");
                if(previewFile.exists() ){
                    Files.delete(previewFile.toPath());
                }
                Files.copy(originFile.toPath(), previewFile.toPath());
                model.addAttribute("ordinaryUrl", response.getMsg());
            }
        } catch (IOException e) {
            model.addAttribute("msg", e.getLocalizedMessage());
            model.addAttribute("fileType",fileAttribute.getSuffix());
            return "fileNotSupported";
        }
        return "txt";
    }

}
