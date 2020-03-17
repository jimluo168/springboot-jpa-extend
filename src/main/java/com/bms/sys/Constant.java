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
    /**
     * 字典表分页查询.
     */
    String MAPPER_DICT_PAGE = "dict_page";

    /**
     * 从业人员分页查询
     */
    String MAPPER_PRACTITIONER_PAGE = "practitioner_page";

    /**
     * 公交车辆管理-分页查询.
     */
    String MAPPER_VEHICLE_PAGE = "vehicle_page";
    /**
     * 公交路线管理-分页查询.
     */
    String MAPPER_BUS_ROUTE_PAGE = "bus_route_page";
    /**
     * 场站分页查询
     */
    String MAPPER_BUS_TERMINAL_PAGE = "busTerminal_page";

}
