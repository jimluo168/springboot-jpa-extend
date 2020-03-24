package com.bms.industry.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 公司违规排行
 *
 * @author zouyongcan
 * @date 2020/3/24
 */
@Data
public class BusViolationStatsCompany {

    /**
     * 公司名.
     */
    private String company;

    /**
     * 违规量.
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
