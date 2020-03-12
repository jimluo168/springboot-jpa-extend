package com.bms.common.exception;

/**
 * 数据库异常错误.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
public class DatabaseException extends BaseException {
    public DatabaseException(int code, String message) {
        super(code, message);
    }
}
