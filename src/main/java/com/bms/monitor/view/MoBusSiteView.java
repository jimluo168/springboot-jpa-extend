package com.bms.monitor.view;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆运行监测-线路-站点列表.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusSiteView implements Serializable {
    /**
     * 站点ID.
     */
    private Long id;
    /**
     * 旧系统 站点ID.
     */
    private String oId;
    /**
     * 站点名称.
     */
    private String name;
    /**
     * 上下行标志 1上行 0下行.
     */
    private Integer upDown;
    /**
     * 线路ID.
     */
    private Long routeId;
    /**
     * 旧系统 线路ID.
     */
    private String oRouteId;
    /**
     * 线路名称.
     */
    private String routeName;
    /**
     * 站点顺序.
     */
    private Integer index;


    /**
     * 线路ID 支持多个线路 用于接收前端传入的参数.
     */
    private List<Long> routeIdList = new ArrayList<>();
}
