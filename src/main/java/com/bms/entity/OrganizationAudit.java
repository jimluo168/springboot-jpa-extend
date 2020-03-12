package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 机构组织审核记录.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "organization_audits")
public class OrganizationAudit extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 审核理由.
     */
    @Column(length = 500)
    private String reason;
}
