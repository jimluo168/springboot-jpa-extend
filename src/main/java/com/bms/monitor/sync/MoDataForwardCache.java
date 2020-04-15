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
     * 0:数据无更新.
     */
    public static final int UPDATE_STATUS_FALSE = 0;
    /**
     * 1:数据已更新.
     */
    public static final int UPDATE_STATUS_TRUE = 1;
    /**
     * 0:在线.
     */
    public static final int ONLINE_ON = 0;
    /**
     * 1:断开.
     */
    public static final int ONLINE_OFF = 1;

    /**
     * 车辆编号.
     */
    @JsonProperty(access = WRITE_ONLY)
    private String vehCode;
    private Integer move;
    private Integer online = ONLINE_ON;
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
    /**
     * <8>方位角:是 GPS 数据除以 2 后的数据，数据接收端使用时要将该数据乘以 2.
     */
    private Float gpsAngle;
    /**
     * 数据更新状态 是否更新 0:无更新 1:已更新.
     */
    private Integer updateStatus = UPDATE_STATUS_FALSE;
    /**
     * 最后更新时间.
     */
    private Long lastUpdDate = System.currentTimeMillis();
}
