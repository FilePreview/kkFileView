package cn.keking.service.impl;

import cn.keking.config.ConfigConstants;
import cn.keking.model.FileAttribute;
import cn.keking.model.ReturnResponse;
import cn.keking.service.FilePreview;
import cn.keking.utils.DownloadUtils;
import cn.keking.utils.FileUtils;
import cn.keking.utils.PdfUtils;
import cn.keking.web.filter.BaseUrlFilter;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by kl on 2018/1/17.
 * Content :处理pdf文件
 */
@Service
public class PdfFilePreviewImpl implements FilePreview {

    public static final String FILE_TYPE = "fileType";
    public static final String FILE_NOT_SUPPORTED = "fileNotSupported";
    public static final String PDF_URL = "pdfUrl";
    private final FileUtils fileUtils;

    private final PdfUtils pdfUtils;

    private final DownloadUtils downloadUtils;

    private static final String FILE_DIR = ConfigConstants.getFileDir();

    public PdfFilePreviewImpl(FileUtils fileUtils,
                              PdfUtils pdfUtils,
                              DownloadUtils downloadUtils) {
        this.fileUtils = fileUtils;
        this.pdfUtils = pdfUtils;
        this.downloadUtils = downloadUtils;
    }

    @Override
    public String filePreviewHandle(String url, Model model, FileAttribute fileAttribute) {
        String suffix=fileAttribute.getSuffix();
        String fileName=fileAttribute.getName();
        String officePreviewType = model.asMap().get("officePreviewType") == null ? ConfigConstants.getOfficePreviewType() : model.asMap().get("officePreviewType").toString();
        String baseUrl = BaseUrlFilter.getBaseURL();
        String pdfName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + "pdf";
        String outFilePath = FILE_DIR + pdfName;
        if (OfficeFilePreviewImpl.OFFICE_PREVIEW_TYPE_IMAGE.equals(officePreviewType) || OfficeFilePreviewImpl.OFFICE_PREVIEW_TYPE_ALL_IMAGES.equals(officePreviewType)) {
            //当文件不存在时，就去下载
            if (!fileUtils.listConvertedFiles().containsKey(pdfName) || !ConfigConstants.isCacheEnabled()) {
                ReturnResponse<String> response = downloadUtils.downLoad(fileAttribute, fileName);
                if (0 != response.getCode()) {
                    model.addAttribute(FILE_TYPE, suffix);
                    model.addAttribute("msg", response.getMsg());
                    return FILE_NOT_SUPPORTED;
                }
                outFilePath = response.getContent();
                if (ConfigConstants.isCacheEnabled()) {
                    // 加入缓存
                    fileUtils.addConvertedFile(pdfName, fileUtils.getRelativePath(outFilePath));
                }
            }
            List<String> imageUrls = pdfUtils.pdf2jpg(outFilePath, pdfName, baseUrl);
            if (imageUrls == null || imageUrls.isEmpty()) {
                model.addAttribute("msg", "pdf转图片异常，请联系管理员");
                model.addAttribute(FILE_TYPE,fileAttribute.getSuffix());
                return FILE_NOT_SUPPORTED;
            }
            model.addAttribute("imgurls", imageUrls);
            model.addAttribute("currentUrl", imageUrls.get(0));
            if (OfficeFilePreviewImpl.OFFICE_PREVIEW_TYPE_IMAGE.equals(officePreviewType)) {
                return "officePicture";
            } else {
                return "picture";
            }
        } else {
            // 不是http开头，浏览器不能直接访问，需下载到本地
            if (url != null && !url.toLowerCase().startsWith("http")) {
                if (!fileUtils.listConvertedFiles().containsKey(pdfName) || !ConfigConstants.isCacheEnabled()) {
                    ReturnResponse<String> response = downloadUtils.downLoad(fileAttribute, pdfName);
                    if (0 != response.getCode()) {
                        model.addAttribute(FILE_TYPE, suffix);
                        model.addAttribute("msg", response.getMsg());
                        return FILE_NOT_SUPPORTED;
                    }
                    model.addAttribute(PDF_URL, fileUtils.getRelativePath(response.getContent()));
                    if (ConfigConstants.isCacheEnabled()) {
                        // 加入缓存
                        fileUtils.addConvertedFile(pdfName, fileUtils.getRelativePath(outFilePath));
                    }
                } else {
                    model.addAttribute(PDF_URL, pdfName);
                }
            } else {
                model.addAttribute(PDF_URL, url);
            }
        }
        return "pdf";
    }
}
