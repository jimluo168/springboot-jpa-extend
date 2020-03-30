package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
public class BusOnlineDataDeclareItem extends BaseEntity {

    /**
     * 所属申报
     */
    @ManyToOne
    @JoinColumn(name = "declare_id")
    private BusBusOnlineDataDeclare busBusOnlineDataDeclare;

    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    /**
     * 所属车队.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusTeam carTeam;

    /**
     * 所属路线.
     */
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;

    /**
     * 车辆编号.
     */
    private String vehCode;

    /**
     * 汽油数量.
     */
    private Float gasQuantity;

    /**
     * 汽油 单价.
     */
    private Float gasPrice;

    /**
     * 汽油 金额.
     */
    private Float gasBalance;

    /**
     * 柴油 数量.
     */
    private Float dieselOilQuantity;

    /**
     * 柴油 单价.
     */
    private Float dieselOilPrice;

    /**
     *柴油 金额.
     */
    private Float dieselOilBalance;

    /**
     * 天然气 数量.
     */
    private Float naturalGasQuantity;

    /**
     * 天然气 单价.
     */
    private Float naturalGasPrice;

    /**
     * 天然气 金额.
     */
    private Float naturalGasBalance;

    /**
     *电能 数量.
     */
    private Float electricQuantity;

    /**
     * 电能 单价.
     */
    private Float electricPrice;

    /**
     *电能 金额.
     */
    private Float electricBalance;
}
