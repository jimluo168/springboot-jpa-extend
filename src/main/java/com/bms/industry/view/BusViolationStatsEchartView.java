package com.bms.industry.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Echart数据结构.
 *
 * @author luojimeng
 * @date 2020/3/25
 */
@Data
public class BusViolationStatsEchartView {
    /**
     * 对应echart的legend.data.
     */
    private List<String> legendData = new ArrayList<>();
    /**
     * 对应echart的series.
     */
    private List<Series> series = new ArrayList<>();

    @Data
    public static class Series {
        private String name;
        private List<Integer> data = new ArrayList<>();
    }
}
