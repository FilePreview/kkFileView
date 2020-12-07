package cn.keking.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "fileManager")
public class FileListController {
    @Value("${filemanager.root}")
    private String root;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileListController.class);

    @PostMapping("list")
    public Object list(@RequestBody JSONObject json) {
        // 需要显示的目录路径
        String path = json.getString("path");
        // 返回的结果集
        List<JSONObject> fileItems = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(root, path))) {
            String dateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dt = new SimpleDateFormat(dateFormat);
            for (Path pathObj : directoryStream) {
                // 获取文件基本属性
                BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);
                // 封装返回JSON数据
                JSONObject fileItem = new JSONObject();
                fileItem.put("name", pathObj.getFileName().toString());
                fileItem.put("date", dt.format(new Date(attrs.lastModifiedTime().toMillis())));
                fileItem.put("size", attrs.size());
                fileItem.put("type", attrs.isDirectory() ? "dir" : "file");
                fileItems.add(fileItem);
            }
        } catch (IOException e) {
            LOGGER.error("IOException:", e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", fileItems);
        return jsonObject;
    }
}
