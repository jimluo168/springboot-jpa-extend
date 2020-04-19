package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.core.config.mapper.Mapper;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.entity.Dictionary;

/**
 * 菜单管理.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Mapper
public interface DictMapper {
    PageList<Dictionary> findAll(PageRequest pageRequest, Dictionary dict);
}
