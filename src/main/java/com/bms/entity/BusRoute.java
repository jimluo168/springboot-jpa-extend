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
    private Float mileage;
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
    private BusCarTeam carTeam;

    private Integer status = STATUS_TO_BE_AUDIT;
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
