package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 公交路线管理.
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_routes")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BusRoute extends BaseEntity {
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
     * 审核历史记录.
     */
    @JsonIgnoreProperties("vehicle")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "busRoute")
    private List<BusRouteAudit> auditList;

}
