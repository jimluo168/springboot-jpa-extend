package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/17
 */

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "practitioner_audits")
public class PractitionerAudit extends BaseEntity {

    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Practitioner practitioner;
    /**
     * 审核理由.
     */
    @Column(length = 500)
    private String reason;
}