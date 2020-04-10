package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "bus_sites")
public class BusSite extends BusSiteCommon {
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("bus_site")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "busSite")
    private List<BusSiteAudit> auditList;
}
