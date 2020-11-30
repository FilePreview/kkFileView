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

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.ptql.ProcessFinder;

import java.io.IOException;

/**
 * {@link ProcessManager} implementation that uses the SIGAR library.
 * <p>
 * Requires the sigar.jar in the classpath and the appropriate system-specific
 * native library (e.g. <tt>libsigar-x86-linux.so</tt> on Linux x86) available
 * in the <em>java.library.path</em>.
 * <p>
 * See the <a href="http://support.hyperic.com/display/SIGAR">SIGAR site</a>
 * for documentation and downloads.
 */
public class SigarProcessManager implements ProcessManager {

    public long findPid(ProcessQuery query) throws IOException {
        Sigar sigar = new Sigar();
        try {

            /*
             * author: Qin Huihuang Date:2020-11-24
             *
             * PTQL:进程查询语言，所有的操作系统都会给运行的进行分配一个PID，但是这个进程号
             * 是一个随机数字，所以不方便用进程号来定位程序，PTQL使用的是进程的属性值来定位程序
             * State.Name.eq
             * operator eq: equal to value
             * State.Name: base name of the process executable
             */

            long[] pids = ProcessFinder.find(sigar, "State.Name.eq=" + query.getCommand());
            for (int i = 0; i < pids.length; i++) {
                String[] arguments = sigar.getProcArgs(pids[i]);
                if (arguments != null && argumentMatches(arguments, query.getArgument())) {
                    return pids[i];
                }
            }
            return PID_NOT_FOUND;
        } catch (SigarException sigarException) {
            throw new IOException("findPid failed", sigarException);
        } finally {
            sigar.close();
        }
    }

    public void kill(Process process, long pid) throws IOException {
        Sigar sigar = new Sigar();
        try {
            sigar.kill(pid, Sigar.getSigNum("KILL"));
        } catch (SigarException sigarException) {
            throw new IOException("kill failed", sigarException);
        } finally {
            sigar.close();
        }
    }

    private boolean argumentMatches(String[] arguments, String expected) {
        for (String argument : arguments) {
            if (argument.contains(expected)) {
                return true;
            }
        }
        return false;
    }

}