package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
public class Vehicle extends BaseEntity {
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


}
