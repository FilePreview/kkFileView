/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.keking.markdown.constant;

public class CONSTANT {
    private CONSTANT(){
        throw new IllegalStateException("Utility class");
    }
    public static final String DEFAULT = "default";
    public static final String ERROR = "error";
    // ***************************COMMON*********************************************//
    /**
     * 用于保存空json串;如:jsonField:jsonValue 如果jsonValue也是一个json则空串返回该常量定义
     */
    public static final String ENTER_TEXT_N="\n";

}
