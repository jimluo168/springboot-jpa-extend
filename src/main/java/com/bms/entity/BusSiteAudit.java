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
 * 公交站点管理.
 *
 * @author luojimeng
 * @date 2020/3/17
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_site_audits")
public class BusSiteAudit extends BusSiteCommon {
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "site_id")
    private BusSite busSite;
}
