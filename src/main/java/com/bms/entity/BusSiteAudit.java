package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 公交站点管理.
 *
 * @author luojimeng
 * @date 2020/3/17
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_site_audits")
public class BusSiteAudit extends BusSite {
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "site_id")
    private BusSite busSite;
}
