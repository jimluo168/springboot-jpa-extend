package com.bms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
public class BusRouteAudit extends BusRoute {

    @JsonIgnoreProperties({"audit_list", "organization"})
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;

}
