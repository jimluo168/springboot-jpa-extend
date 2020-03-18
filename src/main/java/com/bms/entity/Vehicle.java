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
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_vehicles")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Vehicle extends BaseEntity {
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
    private Integer vehType;
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
     * 路线.
     */
    private String route;
    /**
     * 座位数量.
     */
    @Column(name = "seat_num")
    private Integer seatNum;
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
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("vehicle")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private List<VehicleAudit> auditList;


}
