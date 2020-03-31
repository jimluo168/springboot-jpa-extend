package com.bms.industry.view;

import lombok.Data;

import java.math.BigDecimal;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/31
 */
@Data
public class DataDeclareTotal {

    /**
     * 汽油总数量.
     */
    private BigDecimal gasQuantityTotal;

    /**
     * 汽油总金额.
     */
    private BigDecimal gasBalanceTotal;

    /**
     * 柴油总数量.
     */
    private BigDecimal dieselOilQuantityTotal;

    /**
     * 柴油总金额.
     */
    private BigDecimal dieselOilBalanceTotal;

    /**
     * 天然气总数量.
     */
    private BigDecimal naturalGasQuantityTotal;

    /**
     * 天然气总金额.
     */
    private BigDecimal naturalGasBalanceTotal;

    /**
     * 电能总数量.
     */
    private BigDecimal electricQuantityTotal;


    /**
     * 电能总金额.
     */
    private BigDecimal electricBalanceTotal;

    /**
     * 总金额
     */
    private BigDecimal totalAllBalance;
}
