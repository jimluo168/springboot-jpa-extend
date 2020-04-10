package com.bms.entity;


import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 应急响应管理.
 *
 * @author luojimeng
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_emergency_responses")
public class MoEmergencyResponse extends BaseEntity {
    /**
     * 1:待处理.
     */
    public static final int STATUS_PENDING = 1;
    /**
     * 5:处理中.
     */
    public static final int STATUS_PROCESSING = 5;
    /**
     * 10:待评估.
     */
    public static final int STATUS_TO_BE_ASSESSED = 10;
    /**
     * 15:已评估.
     */
    public static final int STATUS_HAS_BEEN_ASSESSED = 15;

    /**
     * 0:未生成 是否生成案例(0:未生成 1:已生成).
     */
    public static final int GENERATE_CASE_NO = 0;
    /**
     * 1:已生成 是否生成案例(0:未生成 1:已生成).
     */
    public static final int GENERATE_CASE_YES = 1;

    /**
     * 事件名称.
     */
    private String name;
    /**
     * 事发时间.
     */
    private Date startTime;
    /**
     * 事件地点.
     */
    private String place;
    /**
     * 事件描述.
     */
    private String description;
    /**
     * 预案名称.
     */
    private String preplanName;
    /**
     * 预案编号.
     */
    private String preplanCode;
    /**
     * 预案分类.
     */
    private Integer preplanType;
    /**
     * 事件等级.
     */
    private Integer level;
    /**
     * 救援方案 url /html/yyyyMMdd/xxx.html.
     */
    private String rescuePlan;
    /**
     * 附件 多个以英文 , 号隔开 /docs/yyyyMMdd/xxx.文件后缀名.
     */
    private String attachs;
    /**
     * 救援单位名称.
     */
    private String rescueCompanyName;
    /**
     * 救援起点 json结构 { keyword: ‘贵阳市人民政府',city:’’贵阳 }.
     */
    private String rescueStartPoint;
    /**
     * 救援终点 json结构 { keyword: ‘贵阳北站’,city:’’贵阳 }.
     */
    private String rescueEndPoint;
    /**
     * 备注.
     */
    private String remark;
    /**
     * 执行照片 多个以英文 , 号隔开 /images/yyyyMMdd/xxx.照片后缀名.
     */
    private String photos;
    /**
     * 执行视频 多个以英文 , 号隔开 /videos/yyyyMMdd/xxx.视频后缀名.
     */
    private String videos;
    /**
     * 执行效果 /html/yyyyMMdd/xxx.html.
     */
    private String effect;
    /**
     * 结束时间.
     */
    private Date endTime;
    /**
     * 状态(1:待处理 5:处理中 10:待评估 15:已评估).
     */
    private Integer status = STATUS_PROCESSING;
    @Transient
    private List<Integer> statusList = new ArrayList<>();
    /**
     * 是否生成案例(0:未生成 1:已生成).
     */
    private Integer generateCase = GENERATE_CASE_NO;

    /**
     * 信息采集准确度评估.
     */
    private Integer infoScore;
    /**
     * 信息采集准确度评估 补充.
     */
    private String infoRemark;
    /**
     * 处置方案效果评估.
     */
    private Integer effectScore;
    /**
     * 处置方案效果评估 补充.
     */
    private String effectRemark;
    /**
     * 调度指挥评估.
     */
    private Integer dispatchScore;
    /**
     * 调度指挥评估 补充.
     */
    private String dispatchRemark;
    /**
     * 物资供给评估.
     */
    private Integer materialScore;
    /**
     * 物资供给评估 补充.
     */
    private String materialRemark;
    /**
     * 评估-备注.
     */
    private String evaluateRemark;

    /**
     * 组长.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_response_group_leaders",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rescuer_id", referencedColumnName = "id"))
    private List<MoRescueRescuer> groupLeaderList;

    /**
     * 人员.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_response_rescuers",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rescuer_id", referencedColumnName = "id"))
    private List<MoRescueRescuer> rescuerList;

    /**
     * 应急物资.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_response_materials",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "material_id", referencedColumnName = "id"))
    private List<MoRescueMaterial> rescueMaterialList;
    /**
     * 应急资源存储json格式.
     */
    @Column(name = "rescue_material_json")
    private String rescueMaterialJson;

    /**
     * 救援车辆.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_response_vehicles",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private List<MoRescueVehicle> rescueVehicleList;

}
