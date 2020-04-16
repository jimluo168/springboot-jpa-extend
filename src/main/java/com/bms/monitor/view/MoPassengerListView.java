package com.bms.monitor.view;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客流量动态-列表.
 *
 * @author luojimeng
 * @date 2020/4/16
 */
@Data
public class MoPassengerListView implements Serializable {
    /**
     * 上车人数.
     */
    private Integer getonNumber;
    /**
     * 下车人数.
     */
    private Integer getoffNumber;
    /**
     * 车厢总人数.
     */
    private Integer insideNumber;
    /**
     * 车座位数量.
     */
    private Integer seatNum;
    /**
     * 线路编号.
     */
    private String routeOId;
    /**
     * 站点顺序.
     */
    private Integer siteIndex;
    /**
     * 上下行标志 1上行 0下行.
     */
    private Integer upDown;
    /**
     * 站点名称.
     */
    private String siteName;
    /**
     * 时间点 每分钟.
     */
    private String fmtTime;

    /**
     * ----接收前端字段-----
     */
    /**
     * 公司ID.
     */
    private Long orgId;
    /**
     * 车队ID.
     */
    private Long teamId;
    /**
     * 线路ID.
     */
    private Long routeId;
    /**
     * 开始日期.
     */
    private Date begin;
    /**
     * 结束日期.
     */
    private Date end;
}
