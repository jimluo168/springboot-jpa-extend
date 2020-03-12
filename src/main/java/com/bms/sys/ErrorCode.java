package com.bms.sys;

/**
 * 系统模块的错误码（范围10000-19999）.
 * Created by luojimeng on 2020/3/9.
 */
public interface ErrorCode {
    /**
     * 密码错误.
     */
    int PASSWD_ERR = 10000;
    /**
     * 账号不存在.
     */
    int ACCOUNT_NOT_EXIST = 10001;

}
