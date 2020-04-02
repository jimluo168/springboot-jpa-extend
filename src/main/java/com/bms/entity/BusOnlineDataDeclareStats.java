package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 申报统计表.
 *
 * @author luojimeng
 * @date 2020/3/30
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_online_data_declare_statis")
public class BusOnlineDataDeclareStats extends BaseEntity {
    /**
     * 1=按年统计.
     */
    public static final int CATEGORY_YEAR = 1;
    /**
     * 2=按年统计.
     */
    public static final int CATEGORY_QUARTER = 2;
    /**
     * 3=按年统计.
     */
    public static final int CATEGORY_MONTH = 3;
    /**
     * 4=按年统计.
     */
    public static final int CATEGORY_WEEK = 4;

    /**
     * 1=汽油.
     */
    public static final int TYPE_GAS = 1;
    /**
     * 2=柴油.
     */
    public static final int TYPE_DIESELOIL = 2;
    /**
     * 3=天然气.
     */
    public static final int TYPE_NATURALGAS = 3;
    /**
     * 4=电能.
     */
    public static final int TYPE_ELECTRIC = 4;

    /**
     * 所属申报.
     */
    @ManyToOne
    @JoinColumn(name = "declare_id")
    private BusOnlineDataDeclare declare;

    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    private String orgName;

    /**
     * 所属车队.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusTeam carTeam;
    private String teamName;

    /**
     * 所属路线.
     */
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;
    private String routeName;

    /**
     * 车辆编号.
     */
    private String vehCode;

    /**
     * 数量.
     */
    private BigDecimal quantity;

    /**
     * 金额.
     */
    private BigDecimal balance;

    /**
     * 类别(1:年 2:季 3:月 4:周).
     */
    private Integer category;
    /**
     * 类型(1:汽油 2:柴油 3:天然气 4:电能).
     */
    private Integer type;
    /**
     * 统计时间.
     */
    private Date time;
}
