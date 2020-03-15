package com.bms.common.exception;

/**
 * 异常的工厂类.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
public class ExceptionFactory {
    /**
     * 1000=记录不存在错误码.
     */
    public static final int ERR_DATA_NOT_EXIST = 1000;
    /**
     * 1001=数据库异常错误码.
     */
    public static final int ERR_DATABASE = 1001;
    /**
     * 1002=Session无效或过期.
     */
    public static final int ERR_SESSION_INVALID = 1002;
    /**
     * 文件为空.
     */
    public static final int ERR_OSS_FILE_EMPTY = 1003;
    /**
     * 用户禁用.
     */
    public static final int ERR_USER_STATUS_DISABLED = 1004;

    public static DataNotExistException dataNotExistException() {
        return new DataNotExistException(ERR_DATA_NOT_EXIST, "记录不存在");
    }

    public static DatabaseException databaseException(String message) {
        return new DatabaseException(ERR_DATABASE, message);
    }

    public static SessionInvalidException sessionInvalidException() {
        return new SessionInvalidException(ERR_SESSION_INVALID, "Session会话无效或已过期");
    }
}
