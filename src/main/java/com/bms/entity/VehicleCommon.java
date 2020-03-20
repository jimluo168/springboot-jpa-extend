package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 公交车辆管理.
 * <p>
 * 1.修改字段“线路”：改为可选择，单选，必填字段。
 * “所属企业”改为非必填。
 * “车辆型号”改为“车型”，文本类型。
 * 2.增加字段“载客数量”、“制造商”、“生产日期”、“投产日期”、“车辆用途”、“是否助力”（是/否）、“前后置发动机”（前/后）、“是否空调”（是/否）。非必填
 * 3.调整线路、车队、所属企业的顺序。
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
@MappedSuperclass
public abstract class VehicleCommon extends BaseEntity {
    /**
     * 1=待审核.
     */
    public static final int STATUS_TO_BE_AUDIT = 1;
    /**
     * 2=通过审核.
     */
    public static final int STATUS_PASS_AUDIT = 2;
    /**
     * 3=未通过审核.
     */
    public static final int STATUS_UN_AUDIT = 3;

    /**
     * 车牌号.
     */
    @Column(length = 50)
    private String licNo;
    /**
     * VIN码.
     */
    @Column(length = 100)
    private String vin;
    /**
     * 车辆尺寸(长).
     */
    private Float length;
    /**
     * 车辆尺寸(宽).
     */
    private Float width;
    /**
     * 车辆尺寸(高).
     */
    private Float height;
    /**
     * 燃料类型(字典表).
     */
    private Integer fuelType;
    /**
     * 车辆型号(字典表).
     */
//    private Integer vehType;
    /**
     * 车型(必填) 改为文本 20200320.
     * “车辆型号”改为“车型”，文本类型.
     */
    @Column(nullable = false)
    private String vehType;
    /**
     * 上牌时间.
     */
    private Date cardTime;
    /**
     * SIM卡号.
     */
    private String sim;
    /**
     * 车载终端编号.
     */
    private String terminalNo;
    /**
     * 所属企业.
     */
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 车队.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusTeam carTeam;
    /**
     * 线路.
     */
//    private String route;
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;
    /**
     * 座位数量.
     */
    @Column(name = "seat_num")
    private Integer seatNum;
    /**
     * 载客数量.
     */
    @Column(name = "load_num")
    private Integer loadNum;
    /**
     * 制造商.
     */
    @Column(name = "factory_name")
    private String factoryName;
    /**
     * 生产日期.
     */
    @Column(name = "product_date")
    private Date productDate;
    /**
     * 投产日期.
     */
    @Column(name = "register_date")
    private Date registerDate;
    /**
     * 车辆用途.
     */
    private String purpose;
    /**
     * 是否助力(1=是  0=否).
     */
    private Integer carHelp;
    /**
     * 前后置发动机(1=前  2=后).
     */
    private Integer engine;
    /**
     * 是否空调(1=是  0=否).
     */
    private Integer airCondition;
    /**
     * 备注.
     */
    @Column(length = 500)
    private String remark;

    /**
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    private Integer status = STATUS_TO_BE_AUDIT;
    /**
     * 理由.
     */
    @Column(length = 500)
    private String reason;



}
