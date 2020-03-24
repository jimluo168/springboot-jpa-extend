package com.bms.common.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * DAO命令类.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
public class DaoCmd {
    /**
     * 查询的Key.
     */
    private String queryKey;
    /**
     * 查询的参数.
     */
    private Map<String, Object> params;
    /**
     * 排序
     * <p>field1,field2 asc</p>
     * <p>field1,field2 desc</p>
     */
    private String orderString;
    /**
     * 返回结果集的对象类型.
     */
    private Class<?> resultClass;

    public DaoCmd(String queryKey) {
        this(queryKey, new HashMap<>());
    }

    public DaoCmd(String queryKey, Map<String, Object> params) {
        this(queryKey, params, null);
    }

    public DaoCmd(String queryKey, Map<String, Object> params, Class<?> resultClass) {
        this(queryKey, params, resultClass, null);
    }

    public DaoCmd(String queryKey, Map<String, Object> params, Class<?> resultClass, String orderString) {
        this.queryKey = queryKey;
        this.params = params;
        this.resultClass = resultClass;
        this.orderString = orderString;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getOrderString() {
        return orderString;
    }

    public Class<?> getResultClass() {
        return resultClass;
    }
}
