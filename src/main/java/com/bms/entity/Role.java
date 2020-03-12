package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色表(roles).
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    /**
     * 角色名称.
     */
    private String name;
    /**
     * 描述.
     */
    private String remark;

}
