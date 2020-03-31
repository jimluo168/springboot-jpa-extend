package com.bms.industry.view;

import com.bms.entity.BusRoute;
import com.bms.entity.BusTeam;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/31
 */
@Data
public class DataDeclareRetrieval {
    private String orgName;

    /**
     * 所属车队.
     */
    private String teamName;

    /**
     * 所属路线.
     */
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

    /**
     * 总金额
     */
    private BigDecimal totalBalance;

}
