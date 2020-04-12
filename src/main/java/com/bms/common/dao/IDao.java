package com.bms.common.dao;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;

import java.util.List;

/**
 * DAO接口.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
public interface IDao {
    /**
     * 获取单条记录.
     */
    <T> T getSingle(DaoCmd cmd);

    /**
     * 获取多条记录.
     */
    <T> List<T> getList(DaoCmd cmd);

    /**
     * 分页获取多条数据.
     */
    <T> List<T> getList(DaoCmd cmd, Integer startFrom, Integer maxResult);

    /**
     * 统计记录个数.
     */
    long getCount(DaoCmd cmd);

    /**
     * 分页查找.
     *
     * @param request 页码 页码大小请求信息
     */
    <T> PageList<T> findAll(PageRequest request, DaoCmd cmd);

    /**
     * 执行更新或删除语句的操作.
     *
     * @param cmd
     * @return int 受影响的行数
     */
    int executeUpdate(DaoCmd cmd);
}
