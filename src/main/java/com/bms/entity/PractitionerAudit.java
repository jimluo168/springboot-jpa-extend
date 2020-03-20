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
 * 从业人员审核记录
 *
 * @author zouyongcan
 * @date 2020/3/17
 */

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "practitioner_audits")
public class PractitionerAudit extends PractitionerCommon {

    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "pract_id")
    private Practitioner practitioner;
}
