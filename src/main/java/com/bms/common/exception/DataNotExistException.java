package com.bms.common.exception;

/**
 * 数据不存在数据库的异常.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
public class DataNotExistException extends BaseException {

    public DataNotExistException(int code, String message) {
        super(code, message);
    }
}
