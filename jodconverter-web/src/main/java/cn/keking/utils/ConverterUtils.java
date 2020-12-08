package cn.keking.utils;

import com.sun.star.document.UpdateDocMode;
import cn.keking.extend.ControlDocumentFormatRegistry;
import org.apache.commons.lang3.StringUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.artofsolving.jodconverter.office.OfficeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/*
 *Author:FanPan Date:2020-11-17
 *获取文件转换器
 */
/**
 * 创建文件转换器
 *
 * @author yudian-it
 * @date 2017/11/13
 */
@Component
public class ConverterUtils {

    public static final String SOFFICE_BIN = "soffice.bin";
    private final Logger logger = LoggerFactory.getLogger(ConverterUtils.class);

    private OfficeManager officeManager;

    /*
     *Author:FanPan Date:2020-11-17
     *被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
     *PostConstruct在构造函数之后执行，init（）方法之前执行。
     *PreDestroy（）方法在destroy（）方法执行之后执行
     *
     *如果想在生成对象时完成某些初始化操作，而偏偏这些初始化操作又依赖于依赖注入，那么久无法在构造函数中实现。
     *为此，可以使用@PostConstruct注解一个方法来完成初始化，@PostConstruct注解的方法将会在依赖注入完成后被自动调用。
     */

    /**
     * 启动OpenOffice，并在启动前杀死残留的进程
     * @Author FanPan
     * @Date 2020-11-17
     */
    @PostConstruct
    public void initOfficeManager() throws InterruptedException {
        File officeHome;
        /*
         *Author:FanPan Date:2020-11-17
         *OpenOffice安装在本地环境的目录
         */
        officeHome = OfficeUtils.getDefaultOfficeHome();
        if (officeHome == null) {
            throw new RuntimeException("找不到office组件，请确认'office.home'配置是否有误");
        }
        boolean killOffice = killProcess();
        if (killOffice) {
            logger.warn("检测到有正在运行的office进程，已自动结束该进程");
        }
        try {
            DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
            configuration.setOfficeHome(officeHome);
            configuration.setPortNumber(8100);
            // 设置任务执行超时为5分钟
            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);
            // 设置任务队列超时为24小时
            //configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
            officeManager = configuration.buildOfficeManager();
            officeManager.start();
        } catch (Exception e) {
            logger.error("启动office组件失败，请检查office组件是否可用");
            throw e;
        }
    }

    /**
     * 获得文件的转换器
     * @Author FanPan
     * @Date 2020-11-17
     * @return OfficeDocumentConverter
     *
     */
    public OfficeDocumentConverter getDocumentConverter() {
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager, new ControlDocumentFormatRegistry());
        converter.setDefaultLoadProperties(getLoadProperties());
        return converter;
    }

    /**
     * 设置转换器的属性
     * @Author FanPan
     * @Date 2020-11-17
     * @return
     *
     */
    private Map<String,?> getLoadProperties() {
        Map<String,Object> loadProperties = new HashMap<>(10);
        loadProperties.put("Hidden", true);
        loadProperties.put("ReadOnly", true);
        loadProperties.put("UpdateDocMode", UpdateDocMode.QUIET_UPDATE);
        loadProperties.put("CharacterSet", StandardCharsets.UTF_8.name());
        return loadProperties;
    }

    /**
     * 杀死残留的office进程
     * @Author FanPan
     * @Date 2020-11-17
     * @return
     *
     */
    private boolean killProcess() {
        boolean flag = false;
        /*
         *Author:FanPan Date:2020-11-17
         *获取系统参数
         */
        Properties props = System.getProperties();

        try {
            if (props.getProperty("os.name").toLowerCase().contains("windows")) {

                /*
                 *Author:FanPan Date:2020-11-17
                 *执行tasklist命令，用来显示运行在本地或远程计算机上的所有进程
                 */
                Process p = Runtime.getRuntime().exec("cmd /c tasklist ");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream os = p.getInputStream();
                byte[] b = new byte[256];
                while (os.read(b) > 0) {
                    baos.write(b);
                }
                String s = baos.toString();
                if (s.contains(SOFFICE_BIN)) {
                    /*
                     *Author:FanPan Date:2020-11-17
                     *杀死正在运行的office进程
                     */
                    Runtime.getRuntime().exec("taskkill /im " + SOFFICE_BIN + " /f");
                    flag = true;
                }
            } else {
                Process p = Runtime.getRuntime().exec(new String[]{"sh","-c","ps -ef | grep " + SOFFICE_BIN});
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream os = p.getInputStream();
                byte[] b = new byte[256];
                while (os.read(b) > 0) {
                    baos.write(b);
                }
                String s = baos.toString();
                if (StringUtils.ordinalIndexOf(s, SOFFICE_BIN, 3) > 0) {
                    String[] cmd ={"sh","-c","kill -15 `ps -ef|grep " + SOFFICE_BIN + "|awk 'NR==1{print $2}'`"};
                    Runtime.getRuntime().exec(cmd);
                    flag = true;
                }
            }
        } catch (IOException e) {
            logger.error("检测office进程异常", e);
        }
        return flag;
    }
    /**
     * 关闭officeManager
     * @Author FanPan
     * @Date 2020-11-17
     * @return
     *
     */
    @PreDestroy
    public void destroyOfficeManager(){
        if (null != officeManager && officeManager.isRunning()) {
            officeManager.stop();
        }
    }

}
