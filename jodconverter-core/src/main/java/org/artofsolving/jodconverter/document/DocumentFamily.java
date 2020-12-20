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
package org.artofsolving.jodconverter.document;

/**
 * @author 庞新程
 * 文档家族(表示了什么样的文档, 但并不代表某一种具体实现(eg: 演示文稿可以是ppt, pptx, odp, sxi...))
 * 一个枚举类,包含了一些格式
 * TEXT: 纯文本
 * SPREADSHEET: 表格
 * PRESENTATION: 演示文稿
 * DRAWING:绘制的图纸
 */
public enum DocumentFamily {

    TEXT, SPREADSHEET, PRESENTATION, DRAWING

}
