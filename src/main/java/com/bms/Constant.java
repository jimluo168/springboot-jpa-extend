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
     * 用户管理-用户分页查询.
     */
    String MAPPER_USER_PAGE = "user_page";
    /**
     * 菜单管理-根据用户ID查找权限编码.
     */
    String MAPPER_MENU_FIND_PERMISSION_CODE_BY_USERID = "menu_findPermissionCodeByUserId";
    /**
     * 菜单管理-查询当前菜单下我所拥有的tab页签.
     */
    String MAPPER_MENU_FIND_TABS_BY_USERID_AND_PARENT_ID = "menu_findTabsByUserIdAndParentId";
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
    String MAPPER_BUS_VIOLATION_STATS_TYPE = "bus_violation_stats_type";
    /**
     * 事件统计分析-公司违规排行.
     */
    String MAPPER_BUS_VIOLATION_STATS_COMPANY = "bus_violation_stats_company";
    /**
     * 事件统计分析-司机违规排行.
     */
    String MAPPER_BUS_VIOLATION_STATS_DRIVER = "bus_violation_stats_driver";
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
     * 燃油消耗管理-能源趋势对比.
     */
    String MAPPER_BUS_ONLINE_DATA_DECLARE_STATS_ENERGYCOMPARISON = "bus_online_data_declare_stats_energycomparison";
    /**
     * 网上申报-分页查询.
     */
    String MAPPER_ONLINE_DATA_DECLARE_PAGE = "online_data_declare_page";
    /**
     * 申报明细-统计查询.
     */
    String MAPPER_ONLINE_DATA_DECLARE_RETRIEVAL = "bus_online_data_declare_retrieval";
    /**
     * 申报明细-总计.
     */
    String MAPPER_ONLINE_DATA_DECLARE_TOTAL = "bus_online_data_declare_total";

    /**
     * 专家知识库-分页查询.
     */
    String MAPPER_KNOWLEDGE_BASE_PAGE = "mo_knowledge_base_page";
    /**
     * 专家库-分页查询.
     */
    String MAPPER_EXPERT_PAGE = "mo_expert_page";
    /**
     * 救援资源管理-人员.
     */
    String MAPPER_MO_RESCUE_RESCUER_PAGE = "mo_rescue_rescuer_page";
    /**
     * 救援资源管理-人员-公司.
     */
    String MAPPER_MO_RESCUE_RESCUER_ORGNAME_PAGE = "mo_rescue_rescuer_orgname_page";
    /**
     * 救援资源管理-人员-职位.
     */
    String MAPPER_MO_RESCUE_RESCUER_POSITION_PAGE = "mo_rescue_rescuer_position_page";
    /**
     * 救援资源管理-车辆.
     */
    String MAPPER_MO_RESCUE_VEHICLE_PAGE = "mo_rescue_vehicle_page";
    /**
     * 救援资源管理-车辆-公司.
     */
    String MAPPER_MO_RESCUE_VEHICLE_ORGNAME_PAGEE = "mo_rescue_vehicle_orgname_page";
    /**
     * 救援资源管理-车辆-线路.
     */
    String MAPPER_MO_RESCUE_VEHICLE_ROUTENAME_PAGE = "mo_rescue_vehicle_routename_page";
    /**
     * 救援资源管理-物资.
     */
    String MAPPER_MO_RESCUE_MATERIAL_PAGE = "mo_rescue_material_page";
    /**
     * 救援资源管理-物资-公司.
     */
    String MAPPER_MO_RESCUE_MATERIAL_ORGNAME_PAGE = "mo_rescue_material_orgname_page";
    /**
     * 救援资源管理-物资-物资编号.
     */
    String MAPPER_MO_RESCUE_MATERIAL_CODE_PAGE = "mo_rescue_material_code_page";
    /**
     * 救援资源管理-物资-物资类型.
     */
    String MAPPER_MO_RESCUE_MATERIAL_TYPE_PAGE = "mo_rescue_material_type_page";
    /**
     * 救援资源管理-物资-物资来源.
     */
    String MAPPER_MO_RESCUE_MATERIAL_ORIGIN_PAGE = "mo_rescue_material_origin_page";
    /**
     * 应急响应处理-分页查询.
     */
    String MAPPER_MO_EMERGENCY_RESPONSE_PAGE = "mo_emergency_response_page";
    /**
     * 应急信息发布
     */
    String MAPPER_MO_INFO_RELEASE_PAGE = "mo_info_release_page";
    /**
     * 应急预案管理
     */
    String MAPPER_MO_EMERGENCY_PREPLAN_PAGE = "mo_emergency_preplan_page";
    /**
     * 应急预案管理
     */
    String MAPPER_MO_EMERGENCY_PREPLAN_NAME_PAGE = "mo_emergency_preplan_name_page";
    /**
     * 根据线路编码和站点顺序查找线路名称和站点名称.
     */
    String MAPPER_MO_DATA_FORWARD_FIND_BUS_ROUTE_SITE_BY_ROUTEID_AND_SITEINDEX = "mo_data_forward_find_bus_route_site_by_routeid_and_siteindex";
    /**
     * 根据车辆编号更新车辆信息.
     */
    String MAPPER_MO_DATA_FORWARD_UPDATE_BUS_VEHICLE_BY_CODE = "mo_data_forward_update_bus_vehicle_by_code";

    String MAPPER_BUS_TERMINAL_MENU_ORG = "bus_terminal_menu_org";

    String MAPPER_BUS_TERMINAL_MENU_TERMINAL = "bus_terminal_menu_terminal";
    /**
     * 车辆运行监测-车辆-车辆列表.
     */
    String MAPPER_MO_BUS_VEHICLE_LIST_BY_ROUTE_ID_LIST = "mo_bus_vehicle_list_by_route_id_list";
    /**
     * 车辆运行监测-车辆-历史轨迹列表.
     */
    String MAPPER_MO_BUS_VEHICLE_HISTORY_TRACK_LIST_BY_VEHCODE = "mo_bus_vehicle_history_track_list_by_vehcode";
    /**
     * 车辆运行监测-车辆-历史轨迹点.
     */
    String MAPPER_MO_BUS_VEHICLE_HISTORY_TRACK_POINT_BY_VEHCODE = "mo_bus_vehicle_history_track_point_by_vehcode";
    /**
     * 车辆运行监测-线路-左边列表.
     */
    String MAPPER_MO_BUS_VEHICLE_ROUTE_LIST_BY_ROUTE_ID_LIST = "mo_bus_vehicle_route_list_by_route_id_list";
    /**
     * 车辆运行监测-线路-站点列表.
     */
    String MAPPER_MO_BUS_VEHICLE_SITE_LIST_BY_ROUTE_ID_LIST = "mo_bus_vehicle_site_list_by_route_id_list";

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
     * 日期格式=2019-03-26 10:57:30
     */
    String DATE_FORMAT_YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式=yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    String DATE_FORMAT_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * 时区 GTM+8.
     */
    String TIME_ZONE_GMT8 = "GMT+8";
    /**
     * 时区 UTC.
     */
    String TIME_ZONE_UTC = "UTC";
    /**
     * 导出Excel最大行数.
     */
    int EXPORT_EXCEL_MAX_LINE = 65530;

    /***--------------------------基本常量定义结束--------------------------------------***/
}
