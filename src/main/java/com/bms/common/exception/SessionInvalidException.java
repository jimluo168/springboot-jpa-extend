package com.bms.common.exception;

/**
 * Session会话无效或过期错误.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
public class SessionInvalidException extends BaseException {
    public SessionInvalidException(int code, String message) {
        super(code, message);
    }
}
