package com.bms.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_off_site_datas")
public class MoOffSiteData implements Serializable {
    @Id
    private Long id;
    /**
     * 线路编号.
     */
    @Column(name = "route_o_id")
    private String routeOId;
    private String vehCode;
    private Date arrivalDate;
    private Date offsiteDate;
    @Column(name = "is_move")
    private Integer move;
    private Integer siteIndex;
    private Integer getonNumber;
    private Integer getoffNumber;
    private Integer insideNumber;
    private String routeName;
    private String siteName;
    /**
     * 线路方向 上下行 0:下行 1:上行.
     */
    private Integer upDown;
    /**
     * 座位数量.
     */
    private Integer seatNum;
    /**
     * 公司ID.
     */
    @Column(name = "org_id")
    private Long orgId;
    /**
     * 车队ID.
     */
    @Column(name = "team_id")
    private Long teamId;
    /**
     * 线路ID.
     */
    @Column(name = "route_id")
    private Long routeId;
}
