package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_routes")
public class BusRoute extends BusRouteCommon {
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("bus_route")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "busRoute")
    private List<BusRouteAudit> auditList;

}
