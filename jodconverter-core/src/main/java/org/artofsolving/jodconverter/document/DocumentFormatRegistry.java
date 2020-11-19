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

import java.util.Set;

/**
 * 文件格式注册
 */
public interface DocumentFormatRegistry {

    // 通过后缀获得文件格式
    public DocumentFormat getFormatByExtension(String extension);

    public DocumentFormat getFormatByMediaType(String mediaType);

    public Set<DocumentFormat> getOutputFormats(DocumentFamily family); 

}
