package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 公交路线管理.
 * <p>
 * 线路管理的新增页面
 * 1.新增字段：“线路类型”，可选，非必填。
 * 2.修改字段：“所属企业”，改为非必填。
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
@MappedSuperclass
public abstract class BusRouteCommon extends BaseEntity {
    /**
     * 1=待审核.
     */
    public static final int STATUS_TO_BE_AUDIT = 1;
    /**
     * 2=通过审核.
     */
    public static final int STATUS_PASS_AUDIT = 2;
    /**
     * 3=未通过审核.
     */
    public static final int STATUS_UN_AUDIT = 3;
    /**
     * 0=不是环线.
     */
    public static final int LOOP_LINE_FALSE = 0;
    /**
     * 1=是环线.
     */
    public static final int LOOP_LINE_TRUE = 0;


    /**
     * 名称.
     */
    private String name;
    /**
     * 编号.
     */
    private String code;
    /**
     * 线路类型(字典表 ROUTE_TYPE).
     */
    private Integer type;
    /**
     * 票价.
     */
    private Float price;
    /**
     * 里程.
     */
    private String mileage;
    /**
     * 首发站.
     */
    private String startSite;
    /**
     * 终点站.
     */
    private String endSite;
    /**
     * 途经站点.
     */
    @Column(name = "way_sites", length = 1000)
    private String waySites;
    /**
     * 首班时间.
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 末班时间.
     */
    @Column(name = "last_time")
    private Date lastTime;
    /**
     * 备注.
     */
    @Column(length = 1000)
    private String remark;
    /**
     * 是否环线(0:否 1：是).
     */
    private Integer loopLine = LOOP_LINE_FALSE;
    /**
     * 状态(1=待审核 2=通过审核 3=未通过审核).
     */
    private Integer status = STATUS_TO_BE_AUDIT;
    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 所属车队.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusTeam carTeam;
    /**
     * 理由.
     */
    @Column(length = 500)
    private String reason;
    /**
     * 旧系统 id.
     */
    @Column(name = "o_id")
    private String oId;
    /**
     * 旧系统 公司ID.
     */
    @Column(name = "o_org_id")
    private String oOrgId;
    /**
     * 旧系统 车队ID.
     */
    @Column(name = "o_team_id")
    private String oTeamId;
    /**
     * 线路经纬度文件名.
     */
    @Column(name = "line_file")
    private String lineFile;

    @Column(name = "cross_line")
    private Integer crossLine;
    /**
     * 上行撞点百分比.
     */
    @Column(name = "percentage")
    private Float percentage;
    @Column(name = "down_radius")
    private String downRadius;
    @Column(name = "time_interval")
    private String timeInterval;
    @Column(name = "up_radius")
    private String upRadius;
    @Column(name = "time_frame")
    private String timeFrame;
    @Column(name = "change_lineid")
    private Integer changeLineid;
    @Column(name = "up_time")
    private String upTime;
    @Column(name = "down_time")
    private String downTime;
    @Column(name = "upkilometre")
    private String upkilometre;
    @Column(name = "downkilometre")
    private String downkilometre;
    @Column(name = "down_percentage")
    private String downPercentage;
    @Column(name = "push_interval")
    private String pushInterval;
    @Column(name = "upkilometre_diaotou")
    private String upkilometreDiaotou;
    @Column(name = "downkilometre_diaotou")
    private String downkilometreDiaotou;
    @Column(name = "time_through")
    private String timeThrough;
    @Column(name = "km_through")
    private String kmThrough;
    @Column(name = "attendance_rate")
    private String attendanceRate;
    @Column(name = "version")
    private Integer version;
    @Column(name = "def_interval")
    private Integer defInterval;

}
