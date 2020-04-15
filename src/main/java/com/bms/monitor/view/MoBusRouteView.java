package com.bms.monitor.view;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆综合运行监控-车辆-线路.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusRouteView implements Serializable {
    /**
     * 线路ID.
     */
    private Long id;
    /**
     * 旧系统 线路ID.
     */
    private String oId;
    /**
     * 线路名称.
     */
    private String name;
    /**
     * 上下行标志 1上行 0下行.
     */
    private String upDown;

    /**
     * 线路ID 支持多个线路 用于接收前端传入的参数.
     */
    private List<Long> routeIdList = new ArrayList<>();
}
