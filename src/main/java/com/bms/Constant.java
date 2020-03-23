package com.bms;

/**
 * 常量.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
public interface Constant {
    /***--------------------------MAPPER SQL常量定义开始--------------------------------***/

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
     * 从业人员分页查询.
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
     * 场站分页查询.
     */
    String MAPPER_BUS_TERMINAL_PAGE = "bus_terminal_page";
    /**
     * 公交车队管理-分页查询.
     */
    String MAPPER_BUS_CAR_TEAM_PAGE = "bus_car_team_page";
    /**
     * 公交站点管理-分页查询.
     */
    String MAPPER_BUS_SITE_PAGE = "bus_site_page";
    /**
     * 行政管理-分页查询.
     */
    String MAPPER_NOTICE_PAGE = "notice_page";
    /**
     * 投诉建议管理-分页查询.
     */
    String MAPPER_SUGGEST_PAGE = "suggest_page";
    /**
     * 违规信息管理-分页查询.
     */
    String MAPPER_BUS_VIOLATION_PAGE = "bus_violation_page";


    /***--------------------------MAPPER SQL常量定义结束--------------------------------***/

    /***--------------------------基本常量定义开始--------------------------------------***/

    /**
     * 日期格式=yyyyMMdd.
     */
    String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    /**
     * 日期格式=yyyy-MM-dd.
     */
    String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 导出Excel最大行数.
     */
    int EXPORT_EXCEL_MAX_LINE = 65530;

    /***--------------------------基本常量定义结束--------------------------------------***/
}
