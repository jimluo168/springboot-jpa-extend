package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 从业人员审核记录
 *
 * @author zouyongcan
 * @date 2020/3/17
 */

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "practitioner_audits")
public class PractitionerAudit extends Practitioner {

    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "pra_id")
    private Practitioner practitioner;
}
