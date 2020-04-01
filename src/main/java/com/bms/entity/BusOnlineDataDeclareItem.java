package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 申报明细
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "Bus_online_data_declare_items")
public class BusOnlineDataDeclareItem extends BaseEntity {

    /**
     * 所属申报.
     */
    @JsonIgnoreProperties("item_list")
    @ManyToOne
    @JoinColumn(name = "declare_id")
    private BusOnlineDataDeclare declare;

    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    private String orgName;

    /**
     * 所属车队.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusTeam carTeam;
    private String teamName;

    /**
     * 所属路线.
     */
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;
    private String routeName;

    /**
     * 车辆编号.
     */
    private String vehCode;

    /**
     * 汽油数量.
     */
    private BigDecimal gasQuantity;

    /**
     * 汽油 单价.
     */
    private BigDecimal gasPrice;

    /**
     * 汽油 金额.
     */
    private BigDecimal gasBalance;

    /**
     * 柴油 数量.
     */
    private BigDecimal dieselOilQuantity;

    /**
     * 柴油 单价.
     */
    private BigDecimal dieselOilPrice;

    /**
     * 柴油 金额.
     */
    private BigDecimal dieselOilBalance;

    /**
     * 天然气 数量.
     */
    private BigDecimal naturalGasQuantity;

    /**
     * 天然气 单价.
     */
    private BigDecimal naturalGasPrice;

    /**
     * 天然气 金额.
     */
    private BigDecimal naturalGasBalance;

    /**
     * 电能 数量.
     */
    private BigDecimal electricQuantity;

    /**
     * 电能 单价.
     */
    private BigDecimal electricPrice;

    /**
     * 电能 金额.
     */
    private BigDecimal electricBalance;
}
