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

    private static Boolean cacheEnabled;
    private static String[] simText = {};
    private static String[] media = {};
    private static String officePreviewType;
    private static String ftpUsername;
    private static String ftpPassword;
    private static String ftpControlEncoding;
    private static String baseUrl;
    private static String fileDir = OfficeUtils.getHomePath() + File.separator + "file" + File.separator;
    private static CopyOnWriteArraySet<String> trustHostSet;
    private static String pdfDownloadDisable;

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

    public static boolean isCacheEnabled() {
        return cacheEnabled;
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
        ConfigConstants.cacheEnabled = cacheEnabled;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取sim卡文本
     *
     */

    public static String[] getSimText() {
        return simText;
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
        ConfigConstants.simText = simText;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取媒体
     *
     */

    public static String[] getMedia() {
        return media;
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

    public static void setMediaValue(String[] media) {
        ConfigConstants.media = media;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取office预览类型
     *
     */

    public static String getOfficePreviewType() {
        return officePreviewType;
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
        ConfigConstants.officePreviewType = officePreviewType;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ftp用户名
     *
     */

    public static String getFtpUsername() {
        return ftpUsername;
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
        ConfigConstants.ftpUsername = ftpUsername;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ftp密码
     *
     */

    public static String getFtpPassword() {
        return ftpPassword;
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
        ConfigConstants.ftpPassword = ftpPassword;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取ftp控件编码
     *
     */

    public static String getFtpControlEncoding() {
        return ftpControlEncoding;
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
        ConfigConstants.ftpControlEncoding = ftpControlEncoding;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取基本url
     *
     */

    public static String getBaseUrl() {
        return baseUrl;
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
        ConfigConstants.baseUrl = baseUrl;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取过滤器目录
     *
     */

    public static String getFileDir() {
        return fileDir;
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
        if (!DEFAULT_FILE_DIR_VALUE.equalsIgnoreCase(fileDir)) {
            if (!fileDir.endsWith(File.separator)) {
                fileDir = fileDir + File.separator;
            }
            ConfigConstants.fileDir = fileDir;
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
        if (DEFAULT_TRUST_HOST.equalsIgnoreCase(trustHost)) {
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
        return trustHostSet;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置信任主机集
     *
     */

    private static void setTrustHostSet(CopyOnWriteArraySet<String> trustHostSet) {
        ConfigConstants.trustHostSet = trustHostSet;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取pdf下载禁用
     *
     */

    public static String getPdfDownloadDisable() {
        return pdfDownloadDisable;
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
        ConfigConstants.pdfDownloadDisable = pdfDownloadDisable;
    }

}