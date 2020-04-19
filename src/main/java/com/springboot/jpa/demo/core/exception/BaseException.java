package com.springboot.jpa.demo.core.exception;

/**
 * 基础的异常类.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
public class BaseException extends RuntimeException {

    /**
     * 状态码.
     */
    private int code;

    public BaseException(int code) {
        super();
        this.code = code;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public BaseException setCode(int code) {
        this.code = code;
        return this;
    }
}
