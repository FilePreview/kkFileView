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
package org.artofsolving.jodconverter.cli;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.document.DefaultDocumentFormatRegistry;
import org.artofsolving.jodconverter.document.DocumentFormatRegistry;
import org.artofsolving.jodconverter.document.JsonDocumentFormatRegistry;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;

/**
 * Command line interface executable.
 */
public class Convert {

    public static final int STATUS_OK = 0;
    public static final int STATUS_MISSING_INPUT_FILE = 1;
    public static final int STATUS_INVALID_ARGUMENTS = 255;

    /**
     * @author 庞新程
     * 命令行相关的option
     * opt: eg: 设置为o表示在命令行支持 -o
     * longOpt: eg: 设置为output-format表示在命令行支持 --output-format
     * hasArg: 表示是否支持额外的输入
     * description: 一个描述
     */
    //输出格式的参数
    private static final Option OPTION_OUTPUT_FORMAT = new Option("o", "output-format", true, "output format (e.g. pdf)");

    /**
     * @author 庞新程
     * office的socket的端口号
     */

    private static final Option OPTION_PORT = new Option("p", "port", true, "office socket port (optional; defaults to 2002)");

    /**
     * @author 庞新程
     * 文档格式注册表配置文件（可选）???
     */
    private static final Option OPTION_REGISTRY = new Option("r", "registry", true, "document formats registry configuration file (optional)");

    /**
     * @author 庞新程
     * 最大转换时间, 默认是120s
     */
    private static final Option OPTION_TIMEOUT = new Option("t", "timeout", true, "maximum conversion time in seconds (optional; defaults to 120)");

    /**
     * @author 庞新程
     * 使用给定用户安装目录中的设置（可选）
     */
    private static final Option OPTION_USER_PROFILE = new Option("u", "user-profile", true, "use settings from the given user installation dir (optional)");

    private static final Options OPTIONS = initOptions();

    private static final int DEFAULT_OFFICE_PORT = 2002;

    private static Options initOptions() {
        Options options = new Options();
        options.addOption(OPTION_OUTPUT_FORMAT);
        options.addOption(OPTION_PORT);
        options.addOption(OPTION_REGISTRY);
        options.addOption(OPTION_TIMEOUT);
        options.addOption(OPTION_USER_PROFILE);
        return options;
    }

    public static void main(String[] arguments) throws ParseException, JSONException, IOException {

        /**
         * @author 庞新程
         * 命令行解析器
         */
        CommandLineParser commandLineParser = new PosixParser();

        /**
         * @author 庞新程
         * 使用命令行解析器解析arguments
         */
        CommandLine commandLine = commandLineParser.parse(OPTIONS, arguments);

        String outputFormat = null;

        /**
         * @author 庞新程
         * 设置了文件格式的命令
         */
        if (commandLine.hasOption(OPTION_OUTPUT_FORMAT.getOpt())) {
            outputFormat = commandLine.getOptionValue(OPTION_OUTPUT_FORMAT.getOpt());
        }

        /**
         * @author 庞新程
         * 使用offic的默认端口2002
         */
        int port = DEFAULT_OFFICE_PORT;

        /**
         * @author 庞新程
         * 配置了office端口
         */
        if (commandLine.hasOption(OPTION_PORT.getOpt())) {
            port = Integer.parseInt(commandLine.getOptionValue(OPTION_PORT.getOpt()));
        }

        /**
         * @author 庞新程
         * 未识别的参数(一个输入文件, 一个输出文件)
         */
        String[] fileNames = commandLine.getArgs();
        if ((outputFormat == null && fileNames.length != 2) || fileNames.length < 1) {
            String syntax = "java -jar jodconverter-core.jar [options] input-file output-file\n"
                    + "or [options] -o output-format input-file [input-file...]";
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(syntax, OPTIONS);
            System.exit(STATUS_INVALID_ARGUMENTS);
        }

        /**
         * @author 庞新程
         * DocumentFormatRegistry描述了支持的文档格式
         */
        DocumentFormatRegistry registry;
        if (commandLine.hasOption(OPTION_REGISTRY.getOpt())) {

            /**
             * @author 庞新程
             * 读取文件的文档格式注册表配置文件
             */
            File registryFile = new File(commandLine.getOptionValue(OPTION_REGISTRY.getOpt()));

            /**
             * @author 庞新程
             * 解析
             */
            registry = new JsonDocumentFormatRegistry(FileUtils.readFileToString(registryFile));
        } else {

            /**
             * @author 庞新程
             * 否则使用默认的文档格式注册表配置文件
             */
            registry = new DefaultDocumentFormatRegistry();
        }

        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        configuration.setPortNumber(port);

        /**
         * @author 庞新程
         * 设置了执行时间
         */
        if (commandLine.hasOption(OPTION_TIMEOUT.getOpt())) {
            int timeout = Integer.parseInt(commandLine.getOptionValue(OPTION_TIMEOUT.getOpt()));
            configuration.setTaskExecutionTimeout(timeout * 1000);
        }

        /**
         * @author 庞新程
         * 提供了用户自定义的配置文件
         */
        if (commandLine.hasOption(OPTION_USER_PROFILE.getOpt())) {
            String templateProfileDir = commandLine.getOptionValue(OPTION_USER_PROFILE.getOpt());
            configuration.setTemplateProfileDir(new File(templateProfileDir));
        }

        /**
         * @author 庞新程
         * 使用configuration创建一个officeManager
         */
        OfficeManager officeManager = configuration.buildOfficeManager();

        /**
         * @author 庞新程
         * 启动officeManager
         */
        officeManager.start();

        /**
         * @author 庞新程
         * 使用officeManager和registry创建一个转换器
         */
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager, registry);
        try {
            if (outputFormat == null) {
                File inputFile = new File(fileNames[0]);
                File outputFile = new File(fileNames[1]);

                /**
                 * @author 庞新程
                 * 没有指定输出文档格式,尝试直接转换为fileNames[1]
                 */
                converter.convert(inputFile, outputFile);
            } else {

                /**
                 * @author 庞新程
                 * 指定了文件转换格式, 将文件转换为fileNames[i].outputFormat
                 */
                for (int i = 0; i < fileNames.length; i++) {
                    File inputFile = new File(fileNames[i]);
                    String outputName = FilenameUtils.getBaseName(fileNames[i]) + "." + outputFormat;
                    File outputFile = new File(FilenameUtils.getFullPath(fileNames[i]) + outputName);
                    converter.convert(inputFile, outputFile);
                }
            }
        } finally {
            officeManager.stop();
        }
    }
    
}
