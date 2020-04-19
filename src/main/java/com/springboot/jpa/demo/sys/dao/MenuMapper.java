package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.core.config.mapper.Mapper;
import com.springboot.jpa.demo.entity.Menu;

import java.util.List;

/**
 * 菜单管理.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Mapper
public interface MenuMapper {
    /**
     * 根据用户ID查询权限编码.
     */
    List<String> findPermissionCodeByUserId(Long userId);

    /**
     * 查询当前菜单下我所拥有的tab页签.
     *
     * @param userId   用户ID
     * @param parentId 上级菜单ID
     * @param type     类型
     * @return 返回父菜单下所拥有的tab页签
     */
    List<Menu> findByUserIdAndParentIdAndType(Long userId, Long parentId, int type);
}
