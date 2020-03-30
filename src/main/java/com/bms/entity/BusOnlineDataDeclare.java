package com.bms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "online_data_declare")
public class BusOnlineDataDeclare extends BusOnlineDataDeclareCommon {
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("bus_route")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "busRoute")
    private List<BusOnlineDataDeclareAudit> auditList;

}
