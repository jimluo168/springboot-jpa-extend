package com.bms.monitor.sync.view;

import lombok.Data;

import java.io.Serializable;

/**
 * Redis缓存-站点部分信息.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusSiteCache implements Serializable {
    /**
     * 站点ID.
     */
    private Long siteId;
    /**
     * 站点名称.
     */
    private String siteName;
    /**
     * 站点顺序.
     */
    private Integer siteIndex;
    /**
     * 上下行标志 1上行 0下行.
     */
    private Integer upDown;
    /**
     * 线路ID.
     */
    private Long routeId;
    /**
     * 线路名称.
     */
    private String routeName;
}
