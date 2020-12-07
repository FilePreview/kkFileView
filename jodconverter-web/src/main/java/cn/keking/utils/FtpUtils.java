package cn.keking.utils;

import cn.keking.config.ConfigConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
/*
 *Author:FanPan Date:2020-11-17
 *ftp操作工具类
 *
/**
 * @auther: chenjh
 * @since: 2019/6/18 14:36
 */
public class FtpUtils {

    private  FtpUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtils.class);

    /**
     * @Author FanPan
     * @Date 2020-11-19
     * Description: 连接FTP服务器
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param controlEncoding 设置编码集
     * @return FTPClient
     */
    public static FTPClient connect(String host, int port, String username, String password, String controlEncoding) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host, port);
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            ftpClient.login(username, password);
        }
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
        }
        ftpClient.setControlEncoding(controlEncoding);
        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        return ftpClient;
    }

    /**
     * @Author FanPan
     * @Date 2020-11-19
     * Description: 从FTP服务器下载文件
     * @param ftpUrl ftp中文件的url
     * @param localFilePath 保存到本地的地址
     * @param ftpUsername FTP登录账号
     * @param ftpPassword FTP登录密码
     * @param ftpControlEncoding 设置编码集
     */
    public static void download(String ftpUrl, String localFilePath, String ftpUsername, String ftpPassword, String ftpControlEncoding) throws IOException {
        String username = StringUtils.isEmpty(ftpUsername) ? ConfigConstants.getFtpUsername() : ftpUsername;
        String password = StringUtils.isEmpty(ftpPassword) ? ConfigConstants.getFtpPassword() : ftpPassword;
        String controlEncoding = StringUtils.isEmpty(ftpControlEncoding) ? ConfigConstants.getFtpControlEncoding() : ftpControlEncoding;
        URL url = new URL(ftpUrl);
        String host = url.getHost();
        int port = (url.getPort() == -1) ? url.getDefaultPort() : url.getPort();
        String remoteFilePath = url.getPath();
        LOGGER.debug("FTP connection url:{}, username:{}, password:{}, controlEncoding:{}, localFilePath:{}", ftpUrl, username, password, controlEncoding, localFilePath);
        FTPClient ftpClient = connect(host, port, username, password, controlEncoding);

        try( OutputStream outputStream = new FileOutputStream(localFilePath);) {

            ftpClient.enterLocalPassiveMode();
            /*
             * Author:FanPan Date:2020-11-19
             * 绑定输出流下载文件,需要设置编码集，不然可能出现文件为空的情况
             */
            boolean downloadResult = ftpClient.retrieveFile(new String(remoteFilePath.getBytes(controlEncoding), StandardCharsets.ISO_8859_1), outputStream);
            LOGGER.debug("FTP download result {}", downloadResult);
            outputStream.flush();
        }catch (Exception e){
            LOGGER.info("Exception ",e);
        }
        ftpClient.logout();
        ftpClient.disconnect();
    }
}
