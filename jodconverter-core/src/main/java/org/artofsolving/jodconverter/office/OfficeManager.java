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

/**
 * @author 庞新程
 * An OfficeManager knows how to execute {@link OfficeTask}s.
 * <p>
 * An OfficeManager implementation will typically manage one or more
 * {@link OfficeConnection}s.
 * OfficeManager用于管理office task
 */
public interface OfficeManager {

    /**
     * @author 庞新程
     * 执行office任务
     * @param task
     * @throws OfficeException
     */
    void execute(OfficeTask task) throws InterruptedException;

    /**
     * @author 庞新程
     * 启动
     * @throws OfficeException
     */
    void start() throws InterruptedException;

    /**
     * @author 庞新程
     * 停止
     * @throws OfficeException
     */
    void stop() ;

    /**
     * @author 庞新程
     * 判断是否正在运行
     * @return
     */
    boolean isRunning();
}
