package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 公交路线管理.
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_route_audits")
public class BusRouteAudit extends BaseEntity {

    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;
    /**
     * 审核理由.
     */
    @Column(length = 500)
    private String reason;

}
