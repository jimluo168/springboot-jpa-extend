package com.bms.monitor.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * 存放Redis缓存对象.
 *
 * @author luojimeng
 * @date 2020/4/14
 */
@Data
public class MoDataForwardCache {
    /**
     * 车辆编号.
     */
    @JsonProperty(access = WRITE_ONLY)
    private String vehCode;
    private Integer move;
    private Integer online;
    private Integer currentSiteIndex;
    private Integer nextSiteIndex;
    private Float speed;
    private String practOId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer upDown;
    /**
     * 当前站点名称.
     */
    @JsonProperty(access = WRITE_ONLY)
    private String currentSiteName;
    @JsonProperty(access = WRITE_ONLY)
    private String nextSiteName;
    /**
     * 旧系统 线路ID.
     */
    private String routeOId;
}
