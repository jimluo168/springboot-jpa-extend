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
    @JsonProperty(access = WRITE_ONLY)
    private BigDecimal latitude;
    @JsonProperty(access = WRITE_ONLY)
    private BigDecimal longitude;
    /**
     * 存放经纬度 单位：分.
     */
    private String latitudeFen;
    private String longitudeFen;
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
