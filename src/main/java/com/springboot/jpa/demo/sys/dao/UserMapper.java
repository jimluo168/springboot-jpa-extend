package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.core.config.mapper.Mapper;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.entity.User;

/**
 * 用户管理.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Mapper
public interface UserMapper {
    /**
     * 分页查询.
     *
     * @param pageRequest 分页信息.
     * @param user        用户过滤条件.
     * @return 用户分页信息
     */
    PageList<User> findAll(PageRequest pageRequest, User user);
}
