package com.bms.common.dao;

import java.util.Collections;
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

    private String orderString;

    public DaoCmd(String queryKey) {
        this(queryKey, new HashMap<>());
    }

    public DaoCmd(String queryKey, Map<String, Object> params) {
        this.queryKey = queryKey;
        this.params = params;
    }

    public DaoCmd(String queryKey, Map<String, Object> params, String orderString) {
        this.queryKey = queryKey;
        this.params = params;
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
}
