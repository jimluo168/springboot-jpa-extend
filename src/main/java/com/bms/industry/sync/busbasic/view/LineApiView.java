package com.bms.industry.sync.busbasic.view;

import com.bms.entity.BusRoute;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 线路.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@Data
public class LineApiView extends AbstractApiView {
    /**
     * 名称.
     */
    @JsonProperty(value = "l_name")
    private String name;
    /**
     * 线路编号.
     */
    @JsonProperty(value = "lineSerial")
    private String code;
    /**
     * 线路类型(字典表 ROUTE_TYPE).
     */
    @JsonProperty(value = "l_type")
    private Integer type;
    /**
     * 旧系统 车队ID.
     */
    @JsonProperty(value = "t_id")
    private String oTeamId;
    /**
     * 线路经纬度文件名.
     */
    @JsonProperty(value = "line_file")
    private String lineFile;

    @JsonProperty(value = "cross_line")
    private Integer crossLine;
    /**
     * 线路状态 0:可用 1禁用.
     */
    @JsonProperty(value = "status")
    private Integer status;
    /**
     * 上行撞点百分比.
     */
    @JsonProperty(value = "percentage")
    private Float percentage;
    @JsonProperty(value = "downlinkSchedulingRadius")
    private String downRadius;
    @JsonProperty(value = "timeIntervalScheduling")
    private String timeInterval;
    @JsonProperty(value = "uplinkSchedulingRadius")
    private String upRadius;
    @JsonProperty(value = "timeFrameScheduling")
    private String timeFrame;
    /**
     * 是否环线(0:否 1：是).
     */
    @JsonProperty(value = "whetherLink")
    private Integer loopLine = BusRoute.LOOP_LINE_FALSE;
    @JsonProperty(value = "change_lineid")
    private Integer changeLineid;
    @JsonProperty(value = "upTime")
    private String upTime;
    @JsonProperty(value = "downTime")
    private String downTime;
    @JsonProperty(value = "upkilometre")
    private String upkilometre;
    @JsonProperty(value = "downkilometre")
    private String downkilometre;
    @JsonProperty(value = "downPercentage")
    private String downPercentage;
    @JsonProperty(value = "pushInterval")
    private String pushInterval;
    @JsonProperty(value = "upkilometreDiaotou")
    private String upkilometreDiaotou;
    @JsonProperty(value = "downkilometreDiaotou")
    private String downkilometreDiaotou;
    @JsonProperty(value = "timeThrough")
    private String timeThrough;
    @JsonProperty(value = "kmThrough")
    private String kmThrough;
    @JsonProperty(value = "attendanceRate")
    private String attendanceRate;
    @JsonProperty(value = "version")
    private Integer version;
    @JsonProperty(value = "defInterval")
    private Integer defInterval;

}
