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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleDocumentFormatRegistry implements DocumentFormatRegistry {

	/**
	 * @author 庞新程
	 * 支持的各种文档格式,DocumentFormat中配置了详细的信息
	 */
	private List<DocumentFormat> documentFormats = new ArrayList<DocumentFormat>();

	public void addFormat(DocumentFormat documentFormat) {
		documentFormats.add(documentFormat);
	}

	/**
	 * @author 庞新程
	 * 通过拓展名查找文档格式的描述类
	 * @param extension 拓展名
	 * @return 查找到的文档格式描述类
	 */
	public DocumentFormat getFormatByExtension(String extension) {
        if (extension == null) {
            return null;
        }
        String lowerExtension = extension.toLowerCase();
		for (DocumentFormat format : documentFormats) {
			if (format.getExtension().equals(lowerExtension)) {
				return format;
			}
		}
		return null;
	}

	/**
	 * @author 庞新程
	 * 通过MIME查找文档格式的描述类
	 * @param mediaType MIME类型
	 * @return 查找到的文档格式描述类
	 */
	public DocumentFormat getFormatByMediaType(String mediaType) {
        if (mediaType == null) {
            return null;
        }
        for (DocumentFormat format : documentFormats) {
            if (format.getMediaType().equals(mediaType)) {
                return format;
            }
        }
	    return null;
	}

	/**
	 * @author 庞新程
	 * 通过DocumentFamily(一个枚举类,包含了四个变量, 这四个变量描述了支持什么样的文档(TEXT, SPREADSHEET, PRESENTATION, DRAWING)
	 * @param family 文档的家族
	 * @return 该文档家族对应的具体实现(例如PRESENTATION支持ppt, pptx, odp, sxi等)
	 */
	public Set<DocumentFormat> getOutputFormats(DocumentFamily family) {
	    Set<DocumentFormat> formats = new HashSet<DocumentFormat>();
        for (DocumentFormat format : documentFormats) {
            if (format.getStoreProperties(family) != null) {
                formats.add(format);
            }
        }
	    return formats;
	}

}
