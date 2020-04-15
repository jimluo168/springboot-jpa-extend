package com.bms.monitor.sync.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Redis缓存-站点部分信息.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusSiteCache implements Serializable {

    /**
     * 缓存KEY cache:bussite:{route_o_id}:{site_index}.
     */
    public static final String CACHE_BUSSITE_KEYS = "cache:bussite:*";
    public static final String CACHE_BUSSITE_KEY = "cache:bussite:%s";
    public static final int CACHE_BUSSITE_KEY_EXP_SECONDS = 26 * 60 * 60;

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
    /**
     * 旧系统 线路ID.
     */
    @JsonProperty(access = WRITE_ONLY)
    private String routeOId;

}
