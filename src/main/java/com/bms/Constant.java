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
    /**
     * 事件统计分析-司机违规排行.
     */
    String MAPPER_BUS_VIOLATION_STATS_TYPE="bus_violation_stats_type";
    /**
     * 事件统计分析-公司违规排行.
     */
    String MAPPER_BUS_VIOLATION_STATS_COMPANY ="bus_violation_stats_company";
    /**
     * 事件统计分析-司机违规排行.
     */
    String MAPPER_BUS_VIOLATION_STATS_DRIVER ="bus_violation_stats_driver";
    /**
     * 事件统计分析-全部违规行为统计(周、月、年)-周.
     */
    String MAPPER_BUS_VIOLATION_STATS_WEEK = "bus_violation_stats_week";
    /**
     * 事件统计分析-全部违规行为统计(周、月、年)-月.
     */
    String MAPPER_BUS_VIOLATION_STATS_MONTH = "bus_violation_stats_month";
    /**
     * 事件统计分析-全部违规行为统计(周、月、年)-年.
     */
    String MAPPER_BUS_VIOLATION_STATS_YEAR = "bus_violation_stats_year";
    /**
     * 燃油消耗管理-统计数据表.
     */
    String MAPPER_BUS_ONLINE_DATA_DECLARE_STATS = "bus_online_data_declare_stats";
    /**
     * 公交路线管理-分页查询.
     */
    String MAPPER_ONLINE_DATA_DECLARE_PAGE = "online_data_declare";


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
