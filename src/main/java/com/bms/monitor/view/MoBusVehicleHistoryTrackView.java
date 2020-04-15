package com.bms.monitor.view;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 车辆历史轨迹.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusVehicleHistoryTrackView implements Serializable {
    /**
     * 轨迹ID.
     */
    private Long id;
    /**
     * 旧系统 线路ID.
     */
    private String routeOId;
    /**
     * 车辆编号.
     */
    private String vehCode;
    /**
     * GPS定位时间.
     */
    private Date gpsCreateDate;
    /**
     * 当前纬度.
     */
    private BigDecimal latitude;
    /**
     * 当前经度.
     */
    private BigDecimal longitude;
    /**
     * 瞬时速度:速度，单位 km/h.
     */
    private Float speed;
    /**
     * 旧系统 从业人员ID.
     */
    private String practOId;
    /**
     * 车辆运行状态 0:静止 1:运动.
     */
    private Integer move;
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


    /***--- 下面是用于接收前端的时间 不作为业务字段使用 ***/
    /**
     * 开始时间.
     */
    private Date begin;
    /**
     * 结束时间.
     */
    private Date end;
}
