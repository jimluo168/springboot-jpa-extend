package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 救援资源管理-车辆.
 *
 * @author luojimeng
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_rescue_vehicles")
public class MoRescueVehicle extends BaseEntity {
    /**
     * 1=待审核.
     */
    public static final int STATUS_TO_BE_AUDIT = 1;
    /**
     * 3=未通过审核.
     */
    public static final int STATUS_UN_AUDIT = 3;
    /**
     * 2=空闲=审核通过.
     */
    public static final int STATUS_FREE = 2;
    /**
     * 4=执行.
     */
    public static final int STATUS_PERFORM = 4;

    /**
     * 车辆编号.
     */
    private String code;
    /**
     * 车牌号.
     */
    private String licNo;
    /**
     * 车辆类型.
     */
    private String vehType;
    /**
     * 驾驶员.
     */
    private String driver;
    /**
     * 驾驶员电话.
     */
    private String driverPhone;
    /**
     * 所属单位.
     */
    private String orgName;
    /**
     * 线路名称.
     */
    private String routeName;
    /**
     * 备注.
     */
    private String remark;
    /**
     * 状态(1:待审核 2:空闲 3:未通过审核 4:执行).
     */
    private Integer status = STATUS_TO_BE_AUDIT;
    @Transient
    private List<Integer> statusList = new ArrayList<>();
    /**
     * 理由.
     */
    private String reason;

}
