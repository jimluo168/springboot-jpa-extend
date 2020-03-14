package com.bms.sys;

/**
 * 常量.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
public interface Constant {
    /**
     * 组织机构分页查询.
     */
    String MAPPER_ORGANIZATION_PAGE = "organization_page";

    /**
     * 角色分页查询.
     */
    String MAPPER_ROLE_PAGE = "role_page";

    /**
     * 用户分页查询.
     */
    String MAPPER_USER_PAGE = "user_page";
    /**
     * 根据用户ID查找权限编码.
     */
    String MAPPER_MENU_FIND_PERMISSION_CODE_BY_USERID = "menu_findPermissionCodeByUserId";
    /**
     * 操作日志分页查询.
     */
    String MAPPER_OPLOG_PAGE = "oplog_page";


}
