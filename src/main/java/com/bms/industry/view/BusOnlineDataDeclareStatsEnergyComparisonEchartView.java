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
public class BusOnlineDataDeclareStatsEnergyComparisonEchartView {

    public static final String[] DEFAULT_TYPE_NAMES = new String[]{"汽油", "柴油", "天然气", "电能"};

    /**
     * 对应echart的legend.data.
     */
    private List<String> legendData = new ArrayList<>();
    /**
     * 对应echart的series.
     */
    private List<BusOnlineDataDeclareStatsEnergyComparisonEchartView.Series> series = new ArrayList<>();

    @Data
    public static class Series {
        private String name;
        private List<BigDecimal> data = new ArrayList<>();
    }
}
