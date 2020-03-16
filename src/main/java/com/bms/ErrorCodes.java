package com.bms;

import com.bms.common.exception.ServiceException;

/**
 * 错误码定义.
 * <p>
 * |模块|错误码范围|<p>
 * |---|------|<p>
 * |common|1000-9999|<p>
 * |sys|10000-19999|<p>
 * |industry|20000-29999|<p>
 * |monitor|30000-39999|<p>
 * |statis|40000-49999|<p>
 *
 * @author luojimeng
 * @date 2020/3/09
 */
public enum ErrorCodes {
    /**
     * common(1000-9999).
     */
    DATA_NOT_EXIST(1000, "记录不存在"),
    DATABASE_ERR(1001, "操作数据错误"),
    SESSION_INVALID(1002, "Session会话无效或过期，请重新登录"),
    OSS_FILE_EMPTY(1003, "文件为空或无效"),
    USER_STATUS_DISABLED(1004, "用户禁用"),


    /**
     * sys(10000-19999).
     */
    PASSWD_ERR(10000, "密码错误"),
    ACCOUNT_NOT_EXIST(10001, "账号不存在");


    /**
     * industry(20000-29999).
     */


    private int code;
    private String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ServiceException build(ErrorCodes err) {
        return new ServiceException(err.code, err.message);
    }
}
