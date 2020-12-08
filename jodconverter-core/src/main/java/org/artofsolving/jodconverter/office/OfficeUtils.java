
//
// JODConverter - Java OpenDocument Converter
// Copyright 2004-2012 Mirko Nasato and contributors
//
// JODConverter is Open Source software, you can redistribute it and/or
// modify it under either (at your option) of the following licenses
//
// 1. The GNU Lesser General Public License v3 (or later)
//    -> http://www.gnu.org/licenses/lgpl-3.0.txt
// 2. The Apache License, Version 2.0
//    -> http://www.apache.org/licenses/LICENSE-2.0.txt
//
package org.artofsolving.jodconverter.office;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.artofsolving.jodconverter.util.PlatformUtils;

import com.sun.star.beans.PropertyValue;
import com.sun.star.uno.UnoRuntime;

public class OfficeUtils {

    public static final String SERVICE_DESKTOP = "com.sun.star.frame.Desktop";
    public static final String OFFICE_HOME_KEY = "office.home";
    public static final String DEFAULT_OFFICE_HOME_VALUE = "default";
    private static final Logger LOGGER = Logger.getLogger(OfficeUtils.class.getName());

    private OfficeUtils() {
        throw new AssertionError("utility class must not be instantiated");
    }

    public static <T> T cast(Class<T> type, Object object) {
        return UnoRuntime.queryInterface(type, object);
    }

    public static PropertyValue property(String name, Object value) {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.Name = name;
        propertyValue.Value = value;
        return propertyValue;
    }

    @SuppressWarnings("unchecked")
    public static PropertyValue[] toUnoProperties(Map<String,?> properties) {
        PropertyValue[] propertyValues = new PropertyValue[properties.size()];
        int i = 0;
        for (Map.Entry<String,?> entry : properties.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String,Object> subProperties = (Map<String,Object>) value;
                value = toUnoProperties(subProperties);
            }
            propertyValues[i++] = property(entry.getKey(), value);
        }
        return propertyValues;
    }

    public static String toUrl(File file) {
        String path = file.toURI().getRawPath();
        String url = path.startsWith("//") ? "file:" + path : "file://" + path;
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    public static File getDefaultOfficeHome() {
        Properties properties = new Properties();
        /*
         * author: Qin Huihuang date:2020-11-24
         * 配置文件的路径,具体来说就是jodconveter-web/src/main/config/application.properties
         */
        String customizedConfigPath = getCustomizedConfigPath();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(customizedConfigPath));
            properties.load(bufferedReader);
            restorePropertiesFromEnvFormat(properties);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"IOException",e);
        } finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE,"Close Reader Error",e);
                }
            }
        }
        /*
         * author:Qin Huihuang date:2020-11-24
         *
         * 从配置文件中读取的office.home属性，其值就是default
         */
        String officeHome = properties.getProperty(OFFICE_HOME_KEY);
        if (officeHome != null && !DEFAULT_OFFICE_HOME_VALUE.equals(officeHome)) {
            return new File(officeHome);
        }
        if (PlatformUtils.isWindows()) {
            // %ProgramFiles(x86)% on 64-bit machines; %ProgramFiles% on 32-bit ones
            String homePath = OfficeUtils.getHomePath();
            String programFiles = System.getenv("ProgramFiles(x86)");
            if (programFiles == null) {
                programFiles = System.getenv("ProgramFiles");
            }
            /*
             * author: Qin Huihuang Date:2020-11-24
             *
             * officeHome是openOffice的安装位置
             * 具体来说从ProgramFiles(x86)/OpenOffice 4，Program/LibreOffice 4，
             * jodconverter-web/src/main/office三个目录中找
             * 最后返回的是jodcoverter-web/src/main/office/program/soffice.bin
             */
            return findOfficeHome(
                programFiles + File.separator + "OpenOffice 4",
                programFiles + File.separator + "LibreOffice 4",
                homePath + File.separator + "office"
            );
        } else if (PlatformUtils.isMac()) {
            return findOfficeHome(
                "/Applications/OpenOffice.org.app/Contents",
                "/Applications/OpenOffice.app/Contents",
                "/Applications/LibreOffice.app/Contents"
            );
        } else {
            // Linux or other *nix variants
            return findOfficeHome(
                "/opt/openoffice.org3",
                "/opt/openoffice",
                "/opt/libreoffice",
                "/opt/openoffice4",
                "/usr/lib/openoffice",
                "/usr/lib/libreoffice"
            );
        }
    }

    private static File findOfficeHome(String... knownPaths) {
        for (String path : knownPaths) {
            File home = new File(path);
            if (getOfficeExecutable(home).isFile()) {
                return home;
            }
        }
        return null;
    }

    public static File getOfficeExecutable(File officeHome) {
        if (PlatformUtils.isMac()) {
            return new File(officeHome, "MacOS/soffice");
        } else {
            return new File(officeHome, "program/soffice.bin");
        }
    }

    /**
     * author: Qin Huihuang Date:2020-11-24
     * @return the file path of the jodconverter-WEB
     * eg: D:\Project\kkFileView\jodconverter-web\src\main
     */
    public static String getHomePath() {
        String userDir = System.getenv("KKFILEVIEW_BIN_FOLDER");
        if (userDir == null) {
            /*
             * author: Qin Huihuang Date:2020-11-24
             *
             * 获得当前工程文件路径
             */
            userDir = System.getProperty("user.dir");
        }
        if (userDir.endsWith("bin")) {
            userDir = userDir.substring(0, userDir.length() - 4);
        } else {
            String separator = File.separator;
            if (userDir.contains("jodconverter-web")) {
                userDir = userDir + separator + "src" + separator +  "main";
            } else {
                userDir = userDir + separator + "jodconverter-web" + separator + "src" + separator + "main";
            }
        }
        return userDir;
    }

    public static String getCustomizedConfigPath() {
        String homePath = OfficeUtils.getHomePath();
        String separator = java.io.File.separator;
        return homePath + separator + "config" + separator + "application.properties";
    }

    /**
     * SpringBoot application.properties 支持从环境变量获取值
     */
    public  static synchronized void restorePropertiesFromEnvFormat(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (value.trim().startsWith("${") && value.trim().endsWith("}")) {
                int beginIndex = value.indexOf(":");
                if (beginIndex < 0) {
                    beginIndex = value.length() - 1;
                }
                int endIndex = value.length() - 1;
                String envKey = value.substring(2, beginIndex);
                String envValue = System.getenv(envKey);
                if (envValue == null || "".equals(envValue.trim())) {
                    value = value.substring(beginIndex + 1, endIndex);
                } else {
                    value = envValue;
                }
                properties.setProperty(key, value);
            }
        }
    }

}

