package cn.keking.model;

import java.io.Serializable;

/**
 * 接口返回值结构
 * @author yudian-it
 * @date 2017/11/17
 */
public class ReturnResponse<T> implements Serializable{
    private static final long serialVersionUID = 313975329998789878L;
    /**
     * 返回状态
     * 0. 成功
     * 1. 失败
     */
    private int code;

    /**
     * 返回状态描述
     * XXX成功
     * XXX失败
     */
    private String msg;

    private T content;
    /**
     * Author：houzheng
     * Date：11-18
     * 构造器，三个属性 编码 信息 内容
     *
     */

    public ReturnResponse(int code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取编码
     *
     */

    public int getCode() {
        return code;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置编码
     *
     */

    public void setCode(int code) {
        this.code = code;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取msg
     *
     */

    public String getMsg() {
        return msg;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置msg
     *
     */

    public void setMsg(String msg) {
        this.msg = msg;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 获取内容
     *
     */

    public T getContent() {
        return content;
    }
    /**
     * Author：houzheng
     * Date：11-18
     * 设置内容
     *
     */

    public void setContent(T content) {
        this.content = content;
    }
}
