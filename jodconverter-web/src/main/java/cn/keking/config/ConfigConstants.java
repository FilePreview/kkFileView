package cn.keking.config;

import org.artofsolving.jodconverter.office.OfficeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Set;

/**
 * @author: chenjh
 * @since: 2019/4/10 17:22
 */

@Component
/**
 * Author:houzheng
 * Date:11-18
 * Description:这个方法实现了常量的配置，
 */
public class ConfigConstants {

    private static Boolean CACHE_ENABLED;
    private static String[] SIM_TEXT = {};
    private static String[] MEDIA = {};
    private static String OFFICE_PREVIEW_TYPE;
    private static String FTP_USERNAME;
    private static String FTP_PASSWORD;
    private static String FTP_CONTROL_ENCODING;
    private static String BASE_URL;
    private static String FILE_DIR = OfficeUtils.getHomePath() + File.separator + "file" + File.separator;
    private static CopyOnWriteArraySet<String> TRUST_HOST_SET;
    private static String PDF_DOWNLOAD_DISABLE;

    public static final String DEFAULT_CACHE_ENABLED = "true";
    public static final String DEFAULT_TXT_TYPE = "txt,html,htm,asp,jsp,xml,json,properties,md,gitignore,log,java,py,c,cpp,sql,sh,bat,m,bas,prg,cmd";
    public static final String DEFAULT_MEDIA_TYPE = "mp3,wav,mp4,flv";
    public static final String DEFAULT_OFFICE_PREVIEW_TYPE = "image";
    public static final String DEFAULT_FTP_USERNAME = null;
    public static final String DEFAULT_FTP_PASSWORD = null;
    public static final String DEFAULT_FTP_CONTROL_ENCODING = "UTF-8";
    public static final String DEFAULT_BASE_URL = "default";
    public static final String DEFAULT_FILE_DIR_VALUE = "default";
    public static final String DEFAULT_TRUST_HOST = "default";
    public static final String DEFAULT_PDF_DOWNLOAD_DISABLE = "true";

    /**
     * Author：houzheng
     * Date：11-18
     * 是否启用缓存
     *
     */

    public static Boolean isCacheEnabled() {
        return CACHE_ENABLED;
    }

    @Value("${cache.enabled:true}")
    /**
     * Author：houzheng
     * Date：11-18
     * 已启用设置缓存
     *
     */

    public void setCacheEnabled(String cacheEnabled) {
        setCacheEnabledValueValue(Boolean.parseBoolean(cacheEnabled));
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置缓存启用值
     *
     */

    public static void setCacheEnabledValueValue(Boolean cacheEnabled) {
        CACHE_ENABLED = cacheEnabled;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取sim卡文本
     *
     */

    public static String[] getSimText() {
        return SIM_TEXT;
    }

    @Value("${simText:txt,html,htm,asp,jsp,xml,json,properties,md,gitignore,log,java,py,c,cpp,sql,sh,bat,m,bas,prg,cmd}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置sim卡文本
     *
     */

    public void setSimText(String simText) {
        String[] simTextArr = simText.split(",");
        setSimTextValue(simTextArr);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置sim卡文本值
     *
     */

    public static void setSimTextValue(String[] simText) {
        SIM_TEXT = simText;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取媒体
     *
     */

    public static String[] getMedia() {
        return MEDIA;
    }

    @Value("${media:mp3,wav,mp4,flv}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置媒体
     *
     */

    public void setMedia(String media) {
        String[] mediaArr = media.split(",");
        setMediaValue(mediaArr);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置媒体值
     *
     */

    public static void setMediaValue(String[] Media) {
        MEDIA = Media;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取office预览类型
     *
     */

    public static String getOfficePreviewType() {
        return OFFICE_PREVIEW_TYPE;
    }

    @Value("${office.preview.type:image}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置office预览类型
     *
     */

    public void setOfficePreviewType(String officePreviewType) {
        setOfficePreviewTypeValue(officePreviewType);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置office预览类型值
     *
     */

    public static void setOfficePreviewTypeValue(String officePreviewType) {
        OFFICE_PREVIEW_TYPE = officePreviewType;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ftp用户名
     *
     */

    public static String getFtpUsername() {
        return FTP_USERNAME;
    }

    @Value("${ftp.username:}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ftp用户名
     *
     */

    public void setFtpUsername(String ftpUsername) {
        setFtpUsernameValue(ftpUsername);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ftp用户名值
     *
     */

    public static void setFtpUsernameValue(String ftpUsername) {
        FTP_USERNAME = ftpUsername;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ftp密码
     *
     */

    public static String getFtpPassword() {
        return FTP_PASSWORD;
    }

    @Value("${ftp.password:}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ftp密码
     *
     */

    public void setFtpPassword(String ftpPassword) {
        setFtpPasswordValue(ftpPassword);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ftp密码值
     *
     */

    public static void setFtpPasswordValue(String ftpPassword) {
        FTP_PASSWORD = ftpPassword;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ftp控件编码
     *
     */

    public static String getFtpControlEncoding() {
        return FTP_CONTROL_ENCODING;
    }

    @Value("${ftp.control.encoding:UTF-8}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ftp控件编码
     *
     */

    public void setFtpControlEncoding(String ftpControlEncoding) {
        setFtpControlEncodingValue(ftpControlEncoding);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置ftp控件编码值
     *
     */

    public static void setFtpControlEncodingValue(String ftpControlEncoding) {
        FTP_CONTROL_ENCODING = ftpControlEncoding;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取基本url
     *
     */

    public static String getBaseUrl() {
        return BASE_URL;
    }

    @Value("${base.url:default}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置基本url
     *
     */

    public void setBaseUrl(String baseUrl) {
        setBaseUrlValue(baseUrl);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置基本url值
     *
     */

    public static void setBaseUrlValue(String baseUrl) {
        BASE_URL = baseUrl;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取过滤器目录
     *
     */

    public static String getFileDir() {
        return FILE_DIR;
    }

    @Value("${file.dir:default}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置文件目录
     *
     */

    public void setFileDir(String fileDir) {
        setFileDirValue(fileDir);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置文件目录值
     *
     */

    public static void setFileDirValue(String fileDir) {
        if (!DEFAULT_FILE_DIR_VALUE.equals(fileDir.toLowerCase())) {
            if (!fileDir.endsWith(File.separator)) {
                fileDir = fileDir + File.separator;
            }
            FILE_DIR = fileDir;
        }
    }

    @Value("${trust.host:default}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置信任主机
     *
     */

    public void setTrustHost(String trustHost) {
        setTrustHostValue(trustHost);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置信任主机值
     *
     */

    public static void setTrustHostValue(String trustHost) {
        CopyOnWriteArraySet<String> trustHostSet;
        if (DEFAULT_TRUST_HOST.equals(trustHost.toLowerCase())) {
            trustHostSet = new CopyOnWriteArraySet<>();
        } else {
            String[] trustHostArray = trustHost.toLowerCase().split(",");
            trustHostSet = new CopyOnWriteArraySet<>(Arrays.asList(trustHostArray));
            setTrustHostSet(trustHostSet);
        }
        setTrustHostSet(trustHostSet);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取信任主机集
     *
     */

    public static Set<String> getTrustHostSet() {
        return TRUST_HOST_SET;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置信任主机集
     *
     */

    private static void setTrustHostSet(CopyOnWriteArraySet<String> trustHostSet) {
        ConfigConstants.TRUST_HOST_SET = trustHostSet;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取pdf下载禁用
     *
     */

    public static String getPdfDownloadDisable() {
        return PDF_DOWNLOAD_DISABLE;
    }


    @Value("${pdf.download.disable:true}")
    /**
     * Author：houzheng
     * Date：11-18
     * 设置pdf下载禁用
     *
     */

    public void setPdfDownloadDisable(String pdfDownloadDisable) {
        setPdfDownloadDisableValue(pdfDownloadDisable);
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置pdf下载禁用值
     *
     */

    public static void setPdfDownloadDisableValue(String pdfDownloadDisable) {
        PDF_DOWNLOAD_DISABLE = pdfDownloadDisable;
    }

}
