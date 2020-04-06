package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

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
     * 1=空闲.
     */
    public static final int STATUS_FREE = 1;
    /**
     * 2=执行.
     */
    public static final int STATUS_PERFORM = 2;

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
    private Integer status = STATUS_FREE;

}
