package cn.keking.web.controller;

import cn.keking.config.ConfigConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import cn.keking.model.ReturnResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author：houzheng
 * Date：11-18
 * 文件控制器
 * 执行对文件的基本操作：上传，删除，下载，搜索是否存在目标文件
 * 所有的操作都是对src/main/file/demo目录下操作
 */

@RestController
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String fileDir = ConfigConstants.getFileDir();

    private static final String DEMO_DIR = "demo";

    private static final String DEMO_PATH = DEMO_DIR + File.separator;

    /**
     * author:Qin Huihuang date:2020-11-26
     * <p>
     * 由于后台没有服务器，因此上传文件只是简单地把本地某个目录下的文件复制到
     * jodconverter-web/src/main/file/demo目录下
     */
    @PostMapping(value = "fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //判断是否为IE浏览器的文件名，IE浏览器下文件名会带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (Math.max(winSep, unixSep));
        if (pos != -1) {
            fileName = fileName.substring(pos + 1);
        }
        // 判断是否存在同名文件
        if (existsFile(fileName)) {
            return new ObjectMapper().writeValueAsString(new ReturnResponse<String>(1, "存在同名文件，请先删除原有文件再次上传", null));
        }
        File outFile = new File(fileDir + DEMO_PATH);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        logger.info("上传文件：{}", fileDir + DEMO_PATH + fileName);
        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(fileDir + DEMO_PATH + fileName)) {
            StreamUtils.copy(in, out);
            /*
             * author : Qin Huihaung date:2020-11-26
             * 以字符串的形式返回Json串
             */
            return new ObjectMapper().writeValueAsString(new ReturnResponse<String>(0, "SUCCESS", null));
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            return new ObjectMapper().writeValueAsString(new ReturnResponse<String>(1, "FAILURE", null));
        }
    }


    /**
     * Author：houzheng
     * Date：11-18
     * 删除文件
     */
    @GetMapping(value = "deleteFile")
    public String deleteFile(String fileName) throws JsonProcessingException {
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        File file = new File(fileDir + DEMO_PATH + fileName);
        logger.info("删除文件：{}", file.getAbsolutePath());
        try {
            if (file.exists()) {
                Files.delete(file.toPath());
            }
        } catch (IOException e) {
            logger.error("删除文件失败", e);
        }
        return new ObjectMapper().writeValueAsString(new ReturnResponse<String>(0, "SUCCESS", null));
    }


    /**
     * Author：houzheng
     * Date：11-18
     * 获取文件
     */
    @GetMapping(value = "listFiles")
    public String getFiles() throws JsonProcessingException {
        List<Map<String, String>> list = Lists.newArrayList();
        File file = new File(fileDir + DEMO_PATH);
        if (file.exists()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(file1 -> list.add(ImmutableMap.of("fileName", DEMO_DIR + "/" + file1.getName())));
        }
        /*
         * author : Qin Huihuang date:2020-11-26
         * 返回数据格式 : [{"fileName":"demo/Exercise4.pdf"},{"fileName":"demo/Spring Security.docx"}]
         */
        return new ObjectMapper().writeValueAsString(list);
    }

    /**
     * Author：houzheng
     * Date：11-18
     * 是否存在文件
     */
    private boolean existsFile(String fileName) {
        File file = new File(fileDir + DEMO_PATH + fileName);
        return file.exists();
    }
}