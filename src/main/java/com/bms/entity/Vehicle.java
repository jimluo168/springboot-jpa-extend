package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_vehicles")
public class Vehicle extends VehicleCommon {
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("vehicle")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private List<VehicleAudit> auditList;
}
