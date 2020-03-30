package com.bms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 网上数据申报审核表.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_online_data_declare_audit")
public class BusOnlineDataDeclareAudit extends BusOnlineDataDeclareCommon {
    /**
     * 关联OnlineDataDeclare.
     */
    @JsonIgnoreProperties({"audit_list"})
    @ManyToOne
    @JoinColumn(name = "declare_id")
    private BusOnlineDataDeclare declare;
}
