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

import org.artofsolving.jodconverter.process.ProcessManager;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class ProcessPoolOfficeManager implements OfficeManager {


    private final BlockingQueue<PooledOfficeManager> pool;//阻塞队列

    private final PooledOfficeManager[] pooledManagers;
    private final long taskQueueTimeout;

    private volatile boolean running = false;

    private static final Logger LOGGER = Logger.getLogger(ProcessPoolOfficeManager.class.getName());

    /**
     * @author 庞新程
     * @param officeHome office的目录
     * @param unoUrls 所有的连接
     * @param runAsArgs 运行时参数
     * @param templateProfileDir 模板配置目录
     * @param workDir 工作目录
     * @param taskQueueTimeout 排队时限
     * @param taskExecutionTimeout 任务执行时限
     * @param maxTasksPerProcess 每个进程的最大任务数
     * @param processManager 进程管理器
     */
    public ProcessPoolOfficeManager(File officeHome, UnoUrl[] unoUrls, String[] runAsArgs, File templateProfileDir, File workDir,
            long retryTimeout, long taskQueueTimeout, long taskExecutionTimeout, int maxTasksPerProcess,
            ProcessManager processManager) {
		this.taskQueueTimeout = taskQueueTimeout;
        pool = new ArrayBlockingQueue<PooledOfficeManager>(unoUrls.length);
        pooledManagers = new PooledOfficeManager[unoUrls.length];
        for (int i = 0; i < unoUrls.length; i++) {
            PooledOfficeManagerSettings settings = new PooledOfficeManagerSettings(unoUrls[i]);
            settings.setRunAsArgs(runAsArgs);
            settings.setTemplateProfileDir(templateProfileDir);
            settings.setWorkDir(workDir);
            settings.setOfficeHome(officeHome);
            settings.setRetryTimeout(retryTimeout);
            settings.setTaskExecutionTimeout(taskExecutionTimeout);
            settings.setMaxTasksPerProcess(maxTasksPerProcess);
            settings.setProcessManager(processManager);
            pooledManagers[i] = new PooledOfficeManager(settings);
        }
        LOGGER.info("ProcessManager implementation is " + processManager.getClass().getSimpleName());
    }

    public synchronized void start() throws  InterruptedException {
        for (PooledOfficeManager pooledManager : pooledManagers) {
            pooledManager.start();
            releaseManager(pooledManager);
        }
        running = true;
    }

    public void execute(OfficeTask task) throws  InterruptedException {
        if (!running) {
            throw new IllegalStateException("this OfficeManager is currently stopped");
        }
        PooledOfficeManager manager = null;
        try {
            manager = acquireManager();
            if (manager == null) {
                throw new OfficeException("no office manager available");
            }
            manager.execute(task);
        } finally {
            if (manager != null) {
                releaseManager(manager);
            }
        }
    }

    public synchronized void stop() {
        running = false;
        LOGGER.info("stopping");
        pool.clear();
        for (PooledOfficeManager pooledManager : pooledManagers) {
            pooledManager.stop();
        }
        LOGGER.info("stopped");
    }

    private PooledOfficeManager acquireManager() throws InterruptedException {
        try {
            return pool.poll(taskQueueTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException interruptedException) {
            LOGGER.warning("interrupted");
            throw interruptedException;
        }
    }

    private void releaseManager(PooledOfficeManager manager) throws InterruptedException {
        try {
            pool.put(manager);
        } catch (InterruptedException interruptedException) {
            LOGGER.warning("interrupted");
            throw interruptedException;
        }
    }

	public boolean isRunning() {
		return running;
	}

}
