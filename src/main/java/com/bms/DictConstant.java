package com.bms;

/**
 * 字典编码常量.
 *
 * @author luojimeng
 * @date 2020/3/20
 */
public interface DictConstant {
    /**
     * 字典的子节点 约定俗成 后缀必须是_CHILD.
     */
    String CHILD_NODE = "_CHILD";
    /**
     * 从业人员类型.
     */
    String EMPLOYMENT_TYPE = "EMPLOYMENT_TYPE";
    /**
     * 燃料类型.
     */
    String FUEL_TYPE = "FUEL_TYPE";
    /**
     * 上下行.
     */
    String UP_DOWN_TYPE = "UP_DOWN_TYPE";
    /**
     * 投诉建议类型.
     */
    String SUGGEST_TYPE = "SUGGEST_TYPE";
    /**
     * 文章类型.
     */
    String ARTICLE_TYPE = "ARTICLE_TYPE";
    /**
     * 违规行为.
     */
    String VIOLATION_TYPE = "VIOLATION_TYPE";
    /**
     * 线路类型.
     */
    String ROUTE_TYPE = "ROUTE_TYPE";

    static String buildChildType(String type) {
        return type + CHILD_NODE;
    }
}
