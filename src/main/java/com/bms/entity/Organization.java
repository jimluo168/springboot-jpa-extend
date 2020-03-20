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
 * 机构表(organizations)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "organizations")
public class Organization extends OrganizationCommon {
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("organization")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private List<OrganizationAudit> auditList;
    /**
     * 车队.
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "bus_carteam_orgs",
            joinColumns = @JoinColumn(name = "org_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private List<BusTeam> carTeamList;
}
