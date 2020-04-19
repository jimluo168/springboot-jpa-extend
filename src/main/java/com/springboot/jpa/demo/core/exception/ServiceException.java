package com.springboot.jpa.demo.core.exception;

/**
 * 业务异常.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public class ServiceException extends BaseException {
    public ServiceException(int code) {
        super(code);
    }

    public ServiceException(int code, String message) {
        super(code, message);
    }
}
