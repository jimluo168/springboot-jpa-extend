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
    public static final int DATABASE_ERR = 1001;


    public static DataNotExistException dataNotExist() {
        return new DataNotExistException(DATA_NOT_EXIST_ERR, "记录不存在");
    }

    public static DatabaseException databaseException(String message) {
        return new DatabaseException(DATABASE_ERR, message);
    }
}
