package com.bms.industry.view;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 能源趋势对比.
 *
 * @author luojimeng
 * @date 2020/3/31
 */
@Data
public class BusOnlineDataDeclareStatsEnergyComparison {
    /**
     * 数量.
     */
    private BigDecimal quantity;
    /**
     * 时间 年(yyyyMM) 月(yyyyMMdd).
     */
    private String fmtTime;
    /**
     * 能源类型.
     */
    private Integer type;
    /**
     * 能源类型名称.
     */
    private String typeName;
}
