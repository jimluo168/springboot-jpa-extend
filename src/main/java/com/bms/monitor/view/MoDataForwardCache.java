package com.bms.monitor.view;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 存放Redis缓存对象.
 *
 * @author luojimeng
 * @date 2020/4/14
 */
@Data
public class MoDataForwardCache {
    private Integer move;
    private Integer online;
    private Integer currentSiteIndex;
    private Integer nextSiteIndex;
    private Float speed;
    private String practOId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer upDown;
}
