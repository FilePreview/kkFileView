package cn.keking.utils;

import cn.keking.config.ConfigConstants;
import cn.keking.hutool.URLUtil;
import cn.keking.model.FileAttribute;
import cn.keking.model.FileType;
import cn.keking.model.ReturnResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
/*
 *Author:FanPan Date:2020-11-17
 *下载文件工具类，将文件下载至file文件夹下
 */
/**
 * @author yudian-it
 */
@Component
public class DownloadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadUtils.class);

    private final String fileDir = ConfigConstants.getFileDir();

    private final FileUtils fileUtils;

    public DownloadUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    private static final String URL_PARAM_FTP_USERNAME = "ftp.username";
    private static final String URL_PARAM_FTP_PASSWORD = "ftp.password";
    private static final String URL_PARAM_FTP_CONTROL_ENCODING = "ftp.control.encoding";

    /**
     * @param fileAttribute fileAttribute
     * @param fileName 文件名
     * @return 本地文件绝对路径
     */
    public ReturnResponse<String> downLoad(FileAttribute fileAttribute, String  fileName) {
        String urlStr = fileAttribute.getUrl();
        String type = fileAttribute.getSuffix();
        ReturnResponse<String> response = new ReturnResponse<>(0, "下载成功!!!", "");
        /*
         * modified by Qin Huihuang, 如果是本地文件的话，不需要下载处理，直接进行转码
         * 因此直接将content设置为文件的绝对路径即可
         */
        if(urlStr.startsWith("D:/")||urlStr.startsWith("C:/")||urlStr.startsWith("E:/")){
            response.setContent(urlStr);
            response.setMsg(fileAttribute.getName());
            return response;
        }
        /*
         * Author FanPan Date 2020-11-22
         * UUID为通用唯一标识码
         */
        UUID uuid = UUID.randomUUID();
        if (null == fileName) {
            fileName = uuid+ "."+type;
        } else { // 文件后缀不一致时，以type为准(针对simText【将类txt文件转为txt】)
            fileName = fileName.replace(fileName.substring(fileName.lastIndexOf(".") + 1), type);
        }
        String realPath = fileDir + fileName;
        File dirFile = new File(fileDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            URL url = new URL(urlStr);
            if (url.getProtocol() != null && url.getProtocol().toLowerCase().startsWith("http")) {
                byte[] bytes = getBytesFromUrl(urlStr);
                OutputStream os = new FileOutputStream(new File(realPath));
                saveBytesToOutStream(bytes, os);
            } else if (url.getProtocol() != null && "ftp".equalsIgnoreCase(url.getProtocol())) {
                String ftpUsername = fileUtils.getUrlParameterReg(fileAttribute.getUrl(), URL_PARAM_FTP_USERNAME);
                String ftpPassword = fileUtils.getUrlParameterReg(fileAttribute.getUrl(), URL_PARAM_FTP_PASSWORD);
                String ftpControlEncoding = fileUtils.getUrlParameterReg(fileAttribute.getUrl(), URL_PARAM_FTP_CONTROL_ENCODING);
                FtpUtils.download(fileAttribute.getUrl(), realPath, ftpUsername, ftpPassword, ftpControlEncoding);
            } else {
                response.setCode(1);
                response.setContent(null);
                response.setMsg("url不能识别url" + urlStr);
            }
            response.setContent(realPath);
            response.setMsg(fileName);
            if(FileType.SIM_TEXT.equals(fileAttribute.getType())){
                convertTextPlainFileCharsetToUtf8(realPath);
            }
            return response;
        } catch (IOException e) {
            LOGGER.error("文件下载失败，url：{}", urlStr, e);
            response.setCode(1);
            response.setContent(null);
            if (e instanceof FileNotFoundException) {
                response.setMsg("文件不存在!!!");
            } else {
                response.setMsg(e.getMessage());
            }
            return response;
        }
    }

    /**
     * @author FanPan
     * @date 2020-11-22
     * @return 从url中读取的字节
     * @throws IOException
     */
    public byte[] getBytesFromUrl(String urlStr) throws IOException {
        InputStream is = getInputStreamFromUrl(urlStr);
        if (is != null) {
            return getBytesFromStream(is);
        } else {
            urlStr = URLUtil.normalize(urlStr, true);
            is = getInputStreamFromUrl(urlStr);
            if (is == null) {
                LOGGER.error("文件下载异常：url：{}", urlStr);
                throw new IOException("文件下载异常：url：" + urlStr);
            }
            return getBytesFromStream(is);
        }
    }

    /**
     * 将从流中读取的字节保存到数组中
     * @Author FanPan
     * @Date 2020-11-22
     * @param b 存储的字节数组
     * @throws IOException
     */
    public void saveBytesToOutStream(byte[] b, OutputStream os) throws IOException {
        os.write(b);
        os.close();
    }

    /**
     * @Author FanPan
     * @Date 2020-11-22
     * @param urlStr
     * @return 连接url后得到的输入流
     */
    private InputStream getInputStreamFromUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            if (connection instanceof HttpURLConnection) {
                connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            }
            return connection.getInputStream();
        } catch (IOException e) {
            LOGGER.warn("连接url异常：url：{}", urlStr);
            return null;
        }
    }

    /**
     * @Author FanPan
     * @Date 2020-11-22
     * @param is
     * @return 从流中获取到的字节数组
     * @throws IOException
     */
    private byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] b = baos.toByteArray();
        is.close();
        baos.close();
        return b;
    }

  /**
   * 转换文本文件编码为utf8
   * 探测源文件编码,探测到编码切不为utf8则进行转码
   * @param filePath 文件路径
   */
  private static void convertTextPlainFileCharsetToUtf8(String filePath)  {
    File sourceFile = new File(filePath);
    if(sourceFile.exists() && sourceFile.isFile() && sourceFile.canRead()) {
      String encoding = null;
      FileCharsetDetector.Observer observer = FileCharsetDetector.guessFileEncoding(sourceFile);
      encoding = observer.isFound()?observer.getEncoding():null;
      //为准确探测到编码,可以考虑使用GBK  大部分文件都是windows系统产生的
      if(encoding != null && !"UTF-8".equals(encoding)){
        // 不为utf8,进行转码
        File tmpUtf8File = new File(filePath+".utf8");

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(tmpUtf8File), StandardCharsets.UTF_8);
          Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), encoding))){

            char[] buf = new char[1024];
            int read;
            while ((read = reader.read(buf)) > 0) {
                writer.write(buf, 0, read);
            }
        }catch (Exception e) {
            LOGGER.info("Exception ",e);
        }
        // 删除源文件
        if(!sourceFile.delete()){
            LOGGER.info("删除源文件失败");
        }
        // 重命名
        if(!tmpUtf8File.renameTo(sourceFile)){
            LOGGER.info("重命名文件失败");
        }
      }
    }
  }
}
