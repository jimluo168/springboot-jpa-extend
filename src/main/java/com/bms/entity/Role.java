package com.bms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 角色表(roles).
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Data
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

    @JsonIgnoreProperties({"organization", "role"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> userList;

    @JsonIgnoreProperties("role_list")
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "role_menus",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private List<Menu> menuList;

}
