package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 投诉建议管理.
 *
 * @author luojimeng
 * @date 2020/3/18
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "adm_suggest_audits")
public class SuggestAudit extends Suggest {
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "suggest_id")
    private Suggest suggest;
}
