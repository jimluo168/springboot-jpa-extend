package com.bms.entity;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_bus_vehicle_gps_datas")
public class MoBusVehicleGpsData implements Serializable {

    /**
     * 车辆运行状态标识 0:未启动 1:运行中.
     */
    public static final int MOVE_FALSE = 0;
    public static final int MOVE_TRUE = 1;

    @Id
    private Long id;
    @Column(name = "route_o_id")
    private String routeOId;
    private String vehCode;
    private Date gpsCreateDate;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Float speed;
    private Float gpsAngle;
    private Float height;
    /**
     * <10>车辆运行状态标识 0:未启动 1:运行中.
     */
    @Column(name = "is_move")
    private Integer move;
    /**
     * 下一站站点顺序.
     */
    private Integer nextSiteIndex;
    private Float engineTemperature;
    private Float carriageTemperature;
    private Integer mileage;
    /**
     * <15>车上人数:车辆当前的乘客人数;.
     */
    private Integer insideNumber;
    /**
     * <16>已发车或已到达终点标识:1 为始发状态，2 为终到状态;.
     */
    @Column(name = "is_start")
    private Integer start;
    /**
     * 调度日期.
     */
    private Date schDate;
    /**
     * 调度方向 1:上行 0:下行 2:未知(车辆在总站内 未启动).
     */
    private Integer upDown;
    /**
     * <20>车辆告警状态;.
     */
    private Integer vehAlarm;
    /**
     * <21>驾驶员编号:目前不在用;.
     */
    @Column(name = "pract_o_id")
    private String practOId;
    /**
     * <22>是否在线:车辆是否已连接服务器;.
     */
    @Column(name = "is_online")
    private Integer online;
    /**
     * <23>离线原因:目前不在用;.
     */
    private String offlineReason;
    private Integer tripTimes;
    private Integer shiftTimes;
    private Integer crossLine;
    private Integer offLine;
    private String practName;
    /**
     * 驾驶员是否在岗:0 表示不在岗，1 表示在岗.
     */
    @Column(name = "is_work")
    private Integer work;
    private String routeName;
    private String currentSiteName;
    private Integer currentSiteIndex;
    private String nextSiteName;
}
