package com.springboot.jpa.demo.core.domain;

/**
 * 响应数据.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
public class Result<T> {
    /**
     * true:成功请求 false:失败请求
     */
    private boolean success = Boolean.TRUE;
    /**
     * 业务状态码
     */
    private int code = 200;
    /**
     * 业务数据
     */
    private T data;
    /**
     * 请求不成功时，返回的错误信息
     */
    private String msg;

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> ok() {
        return new Result<T>(null);
    }

    /**
     * 正常响应.
     *
     * @param data 业务数据
     * @param <T>  业务数据类型
     * @return 响应结果
     */
    public static <T> Result<T> ok(T data) {
        return new Result<T>(data);
    }

    /**
     * 错误响应.
     *
     * @param code 状态码
     * @param msg  错误消息
     * @param <T>  业务数据类型
     * @return 响应结果
     */
    public static <T> Result<T> failure(int code, String msg) {
        return new Result<T>(code, msg).setSuccess(Boolean.FALSE);
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
