package com.bms.monitor.view;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆运行监测-车辆-列表.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusVehicleView implements Serializable {
    /**
     * 车辆编码.
     */
    private String vehCode;
    /**
     * 旧系统 线路ID.
     */
    private String oRouteId;
    /**
     * 新系统线路ID.
     */
    private Long routeId;
    /**
     * 线路名称.
     */
    private String routeName;
    /**
     * 从业人员姓名=司机姓名.
     */
    private String practName;
    /**
     * 电话.
     */
    private String phone;
    /**
     * 工号.
     */
    private String staffNumber;
    /**
     * 车辆运行状态 0:静止 1:运动.
     */
    private Integer move;
    /**
     * 是否在线:车辆是否已连接服务器 0:在线 1:不在线.
     */
    private Integer online;
    /**
     * 当前站点顺序.
     */
    private Integer currentSiteIndex;
    /**
     * 当前站点名称.
     */
    private String currentSiteName;
    /**
     * 瞬时速度:速度 单位 km/h.
     */
    private Float speed;
    /**
     * 当前纬度.
     */
    private BigDecimal currentLatitude;
    /**
     * 当前经度.
     */
    private BigDecimal currentLongitude;
    /**
     * 线路ID 支持多个线路 用于接收前端传入的参数.
     */
    private List<Long> routeIdList = new ArrayList<>();
}
