package com.bms.common.config.session;

import com.bms.common.exception.BaseException;

/**
 * 读取Session的缓存异常.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
public class SessionException extends BaseException {
    public SessionException(int code, String message) {
        super(code, message);
    }
}
