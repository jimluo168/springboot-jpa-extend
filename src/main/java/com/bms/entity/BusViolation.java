package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 违规信息管理.
 *
 * @author luojimeng
 * @date 2020/3/18
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_violations")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BusViolation extends BaseEntity {
    /**
     * 司机姓名.
     */
    @ManyToOne
    @JoinColumn(name = "pract_id")
    private Practitioner practitioner;
    /**
     * 违规车辆信息.
     */
    @ManyToOne
    @JoinColumn(name = "veh_id")
    private Vehicle vehicle;
    /**
     * 车辆型号(字典表).
     */
    private Integer vehType;
    /**
     * 线路.
     */
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;
    /**
     * 所属企业.
     */
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 违规类型-行为(字典表).
     */
    private Integer type;
    /**
     * 事件性质(字典表).
     */
    private Integer eventNature;
    /**
     * 严重程度(字典表).
     */
    private Integer severity;
    /**
     * 违规地点.
     */
    private String place;
    /**
     * 违规描述.
     */
    @Column(length = 500)
    private String description;
    /**
     * 取证照片.
     */
    @Column(length = 1000)
    private String photos;
    /**
     * 取证视频.
     */
    @Column(length = 1000)
    private String videos;
    /**
     * 发生时间.
     */
    private Date time;
    /**
     * 处理意见.
     */
    @Column(length = 500)
    private String dealOpinion;
    /**
     * 处理照片.
     */
    @Column(length = 1000)
    private String dealPhotos;
    /**
     * 处理人.
     */
    @Column(length = 10)
    private String dealPerson;
    /**
     * 附件 多个以英文 , 号隔开.
     */
    @Column(length = 1000)
    private String attachs;
    /**
     * 状态(1:处理中 2:已处理).
     */
    private Integer status = 1;


}
