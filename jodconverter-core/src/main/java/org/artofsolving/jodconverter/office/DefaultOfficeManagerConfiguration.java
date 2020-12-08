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

import org.artofsolving.jodconverter.process.LinuxProcessManager;
import org.artofsolving.jodconverter.process.ProcessManager;
import org.artofsolving.jodconverter.process.PureJavaProcessManager;
import org.artofsolving.jodconverter.process.SigarProcessManager;
import org.artofsolving.jodconverter.util.PlatformUtils;

import java.io.File;

/**
 * @author 庞新程
 * OfficeManager的配置文件
 */
public class DefaultOfficeManagerConfiguration {

    /**
     * 默认重试时限
     * @author 庞新程
     */
    public static final long DEFAULT_RETRY_TIMEOUT = 120000L;
    public static final String OFFICE_HOME = "officeHome";


    /*
     * author:Qin Huihuang date:2020-11-24
     * 找到open office的安装目录
     */
    private File officeHome = OfficeUtils.getDefaultOfficeHome();

    /**
     * Office连接协议(管道/socket)[默认设置为socket](管道应该是linux下用的)
     * @author 庞新程
     */
    private OfficeConnectionProtocol connectionProtocol = OfficeConnectionProtocol.SOCKET;
    private int[] portNumbers = new int[] { 2002 };
    private String[] pipeNames = new String[] { "office" };
    private String[] runAsArgs = null;
    private File templateProfileDir = null;
    private File workDir = new File(System.getProperty("java.io.tmpdir"));
    private long taskQueueTimeout = 30000L;  // 30 seconds
    private long taskExecutionTimeout = 120000L;  // 2 minutes
    private int maxTasksPerProcess = 200;//每一个进程的最大任务数
    private long retryTimeout = DEFAULT_RETRY_TIMEOUT;//重试时间

    private ProcessManager processManager = null;  // lazily initialised

    /**
     * @author 庞新程
     * 设置officeHome
     * @param officeHome
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public DefaultOfficeManagerConfiguration setOfficeHome(String officeHome) {
        checkArgumentNotNull(OFFICE_HOME, officeHome);
        return setOfficeHome(new File(officeHome));
    }

    /**
     * @author 庞新程
     * 设置officeHome
     * @param officeHome
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public DefaultOfficeManagerConfiguration setOfficeHome(File officeHome)  {
        checkArgumentNotNull(OFFICE_HOME, officeHome);
        checkArgument(OFFICE_HOME, officeHome.isDirectory(), "must exist and be a directory");
        this.officeHome = officeHome;
        return this;
    }

    /**
     * @author 庞新程
     * 设置office的连接协议(管道/socket)
     * @param connectionProtocol
     * @return
     * @throws NullPointerException
     */
    public DefaultOfficeManagerConfiguration setConnectionProtocol(OfficeConnectionProtocol connectionProtocol) {
        checkArgumentNotNull("connectionProtocol", connectionProtocol);
        this.connectionProtocol = connectionProtocol;
        return this;
    }

    /**
     * @author 庞新程
     * 设置端口号
     * @param portNumber
     * @return
     */
    public DefaultOfficeManagerConfiguration setPortNumber(int portNumber) {
        this.portNumbers = new int[] { portNumber };
        return this;
    }

    /**
     * @author 庞新程
     * 设置多个端口号
     * @param portNumbers
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public DefaultOfficeManagerConfiguration setPortNumbers(int... portNumbers) {
        checkArgumentNotNull("portNumbers", portNumbers);
        checkArgument("portNumbers", portNumbers.length > 0, "must not be empty");
        this.portNumbers = portNumbers;
        return this;
    }

    /**
     * @author 庞新程
     * 设置管道的名字
     * @param pipeName
     * @return
     * @throws NullPointerException
     */
    public DefaultOfficeManagerConfiguration setPipeName(String pipeName)  {
        checkArgumentNotNull("pipeName", pipeName);
        this.pipeNames = new String[] { pipeName };
        return this;
    }

    /**
     * @author 庞新程
     * 设置多个管道
     * @param pipeNames
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public DefaultOfficeManagerConfiguration setPipeNames(String... pipeNames)  {
        checkArgumentNotNull("pipeNames", pipeNames);
        checkArgument("pipeNames", pipeNames.length > 0, "must not be empty");
        this.pipeNames = pipeNames;
        return this;
    }

    /**
     * @author 庞新程
     * 设置运行时参数
     * @param runAsArgs
     * @return
     */
    public DefaultOfficeManagerConfiguration setRunAsArgs(String... runAsArgs) {
		this.runAsArgs = runAsArgs;
		return this;
	}

    /**
     * @author 庞新程
     * 设置模板配置的目录
     * @param templateProfileDir
     * @return
     * @throws IllegalArgumentException
     */
    public DefaultOfficeManagerConfiguration setTemplateProfileDir(File templateProfileDir)  {
        if (templateProfileDir != null) {
            checkArgument("templateProfileDir", templateProfileDir.isDirectory(), "must exist and be a directory");
        }
        this.templateProfileDir = templateProfileDir;
        return this;
    }

    /**
     * Sets the directory where temporary office profiles will be created.
     * <p>
     * Defaults to the system temporary directory as specified by the <code>java.io.tmpdir</code> system property.
     * 
     * @param workDir
     * @return
     */
    public DefaultOfficeManagerConfiguration setWorkDir(File workDir) {
        checkArgumentNotNull("workDir", workDir);
        this.workDir = workDir;
        return this;
    }

    /**
     * @author 庞新程
     * 设置任务排队超时时间
     * @param taskQueueTimeout
     * @return
     */
    public DefaultOfficeManagerConfiguration setTaskQueueTimeout(long taskQueueTimeout) {
        this.taskQueueTimeout = taskQueueTimeout;
        return this;
    }

    /**
     * @author 庞新程
     * 设置任务执行超时时间
     * @param taskExecutionTimeout
     * @return
     */
    public DefaultOfficeManagerConfiguration setTaskExecutionTimeout(long taskExecutionTimeout) {
        this.taskExecutionTimeout = taskExecutionTimeout;
        return this;
    }

    /**
     * @author 庞新程
     * 设置每一个进程最大任务数
     * @param maxTasksPerProcess
     * @return
     */
    public DefaultOfficeManagerConfiguration setMaxTasksPerProcess(int maxTasksPerProcess) {
        this.maxTasksPerProcess = maxTasksPerProcess;
        return this;
    }

    /**
     * Provide a specific {@link ProcessManager} implementation
     * <p>
     * The default is to use {@link SigarProcessManager} if sigar.jar is
     * available in the classpath, otherwise {@link LinuxProcessManager}
     * on Linux and {@link PureJavaProcessManager} on other platforms.
     * 
     * @param processManager
     * @return
     * @throws NullPointerException
     */
    public DefaultOfficeManagerConfiguration setProcessManager(ProcessManager processManager)  {
        checkArgumentNotNull("processManager", processManager);
        this.processManager = processManager;
        return this;
    }

    /**
     * Retry timeout set in milliseconds. Used for retrying office process calls.
     * If not set, it defaults to 2 minutes
     * 
     * @param retryTimeout in milliseconds
     * @return
     */
    public DefaultOfficeManagerConfiguration setRetryTimeout(long retryTimeout) {
        this.retryTimeout = retryTimeout;
        return this;
    }

    /**
     * @author 庞新程
     * 创建一个进程池OfficeManager
     * @return
     * @throws IllegalStateException
     */
    public OfficeManager buildOfficeManager()  {
        if (officeHome == null) {
            throw new IllegalStateException("officeHome not set and could not be auto-detected");
        } else if (!officeHome.isDirectory()) {
            throw new IllegalStateException("officeHome doesn't exist or is not a directory: " + officeHome);
        } else if (!OfficeUtils.getOfficeExecutable(officeHome).isFile()) {
            throw new IllegalStateException("invalid officeHome: it doesn't contain soffice.bin: " + officeHome);
        }
        if (templateProfileDir != null && !isValidProfileDir(templateProfileDir)) {
            throw new IllegalStateException("templateProfileDir doesn't appear to contain a user profile: " + templateProfileDir);
        }
        if (!workDir.isDirectory()) {
            throw new IllegalStateException("workDir doesn't exist or is not a directory: " + workDir);
        }

        if (processManager == null) {
            processManager = findBestProcessManager();
        }

        /*
         * numInstances基本为1
         */
        int numInstances = connectionProtocol == OfficeConnectionProtocol.PIPE ? pipeNames.length : portNumbers.length;
        UnoUrl[] unoUrls = new UnoUrl[numInstances];
        for (int i = 0; i < numInstances; i++) {
            unoUrls[i] = (connectionProtocol == OfficeConnectionProtocol.PIPE) ? UnoUrl.pipe(pipeNames[i]) : UnoUrl.socket(portNumbers[i]);
        }
        /*
         * 创建一个OfficeManager负责管理OfficeTask
         */
        return new ProcessPoolOfficeManager(officeHome, unoUrls, runAsArgs, templateProfileDir, workDir, retryTimeout, taskQueueTimeout, taskExecutionTimeout, maxTasksPerProcess, processManager);
    }

    /**
     * @author 庞新程
     * 创建一个最优的进程管理器
     * @return
     */
    private ProcessManager findBestProcessManager() {
        if (isSigarAvailable()) {//Sigar是否可用
            return new SigarProcessManager();
        } else if (PlatformUtils.isLinux()) {
        	LinuxProcessManager linuxProcessManager = new LinuxProcessManager();
        	if (runAsArgs != null) {
        		linuxProcessManager.setRunAsArgs(runAsArgs);
        	}
        	return linuxProcessManager;
        } else {
            // NOTE: UnixProcessManager can't be trusted to work on Solaris
            // because of the 80-char limit on ps output there  
            return new PureJavaProcessManager();
        }
    }

    /**
     * @author 庞新程
     * 判断Sigar是否可用(Sigar有问题, 直接让它返回false, 然后使用PureJavaProcessManager(Windows)或者LinuxProcessManager(Linux))
     * @return
     */
    private boolean isSigarAvailable() {
        try {
            Class.forName("org.hyperic.sigar.Sigar", false, getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    /**
     * 检查参数是否有错
     * @param argName 提示的参数名字
     * @param argValue 待检查参数
     * @throws NullPointerException
     */
    private void checkArgumentNotNull(String argName, Object argValue)  {
        if (argValue == null) {
            throw new NullPointerException(argName + " must not be null");
        }
    }

    /**
     * 检查参数<code>condition</code>是否为true
     * @param argName
     * @param condition
     * @param message
     * @throws IllegalArgumentException
     */
    private void checkArgument(String argName, boolean condition, String message)  {
        if (!condition) {
            throw new IllegalArgumentException(argName + " " + message);
        }
    }

    /**
     * 判断参数<code>profileDir</code>是否是一个合法的目录
     * @param profileDir
     * @return
     */
    private boolean isValidProfileDir(File profileDir) {
        return new File(profileDir, "user").isDirectory();
    }

}
