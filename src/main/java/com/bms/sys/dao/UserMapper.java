package com.bms.sys.dao;

import com.bms.common.config.mapper.Mapper;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.entity.User;

import java.util.Map;

/**
 * User Mapper.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Mapper
public interface UserMapper {

    PageList<User> findAll(PageRequest pageRequest, User user);
}
