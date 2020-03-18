package com.bms.entity;

/**
 * 场站审核
 *
 * @author zouyongcan
 * @date 2020/3/17
 */

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_terminal_audits")
public class BusTerminalAudit  extends BaseEntity {

    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private BusTerminal busTerminal;
    /**
     * 审核理由.
     */
    @Column(length = 500)
    private String reason;

}
