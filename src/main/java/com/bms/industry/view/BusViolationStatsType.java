package com.bms.industry.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * 事件统计分析-司机违规排行.
 *
 * @author luojimeng
 * @date 2020/3/24
 */
@Data
public class BusViolationStatsType {
    /**
     * 违规类型.
     */
    private Integer type;
    /**
     * 违规类型文本.
     */
    private String text;
    /**
     * 违规数量.
     */
    private Integer num;
    /**
     * 统计的开始时间.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date begin;
    /**
     * 统计的结束时间.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date end;
}
