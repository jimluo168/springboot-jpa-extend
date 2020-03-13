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
    public static final int DATA_NOT_EXIST_ERR = 1000;
    /**
     * 1001=数据库异常错误码.
     */
    public static final int DATABASE_ERR = 1001;
    /**
     * 1002=Session无效或过期.
     */
    public static final int SESSION_INVALID_ERR = 1002;
    /**
     * 文件为空.
     */
    public static final int OSS_FILE_EMPTY = 1003;



    public static DataNotExistException dataNotExistException() {
        return new DataNotExistException(DATA_NOT_EXIST_ERR, "记录不存在");
    }

    public static DatabaseException databaseException(String message) {
        return new DatabaseException(DATABASE_ERR, message);
    }

    public static SessionInvalidException sessionInvalidException() {
    return new SessionInvalidException(SESSION_INVALID_ERR, "Session会话无效或已过期");
    }
}
