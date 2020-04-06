package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.bms.entity.Menu;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 应急预案管理.
 *
 * @author luojimeng
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_emergency_preplans")
public class MoEmergencyPreplan extends BaseEntity {

    /**
     * 名称.
     */
    private String name;
    /**
     * 编号.
     */
    private String code;
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
     * 救援起点 json结构 { keyword: '贵阳市人民政府',city:'贵阳' }.
     */
    private String rescueStartPoint;
    /**
     * 救援终点 json结构 { keyword: '贵阳北站',city:'贵阳'  }.
     */
    private String rescueEndPoint;
    /**
     * 救援详情.
     */
    private String rescueDesc;
    /**
     * 备注.
     */
    private String remark;

    /**
     * 组长.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_preplan_group_leaders",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rescuer_id", referencedColumnName = "id"))
    private List<MoRescueRescuer> groupLeaderList;

    /**
     * 人员.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_preplan_rescuers",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rescuer_id", referencedColumnName = "id"))
    private List<MoRescueRescuer> rescuerList;

    /**
     * 应急物资.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_preplan_materials",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "material_id", referencedColumnName = "id"))
    private List<MoRescueMaterial> rescueMaterialList;

    /**
     * 救援车辆.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "mo_emergency_preplan_vehicles",
            joinColumns = @JoinColumn(name = "preplan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private List<MoRescueVehicle> rescueVehicleList;


}
