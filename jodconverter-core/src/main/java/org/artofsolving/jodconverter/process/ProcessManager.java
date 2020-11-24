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
package org.artofsolving.jodconverter.process;

import java.io.IOException;

/**
 * author: Qin Huihuang date:2020-11-24
 *
 * JODConverter开始处理一个office进程时，需要使用到进程管理器。当它开始进行这项工作，
 * 就必须要检索该进程的PID，以便在需要时能够kill it。默认情况下，JODConverter会根据JODConverter
 * 运行的操作系统来选择最佳的进程管理器。但是继承了ProcessManager接口的进程管理器均可以被使用
 */
public interface ProcessManager {

    public static final long PID_NOT_FOUND = -2;
    public static final long PID_UNKNOWN = -1;

    /**
     * Process provides methods for performing input from the process, performing
     * output to the process, waiting for the process to complete,
     * checking the exit status of the process, and destroying (killing)
     * the process.
     */
    void kill(Process process, long pid) throws IOException;

    /**
     * @return the pid if found, {@link #PID_NOT_FOUND} if not,
     * or {@link #PID_UNKNOWN} if this implementation is unable to find out
     */
    long findPid(ProcessQuery query) throws IOException;

}
