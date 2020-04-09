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
 * 菜单.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {

    /**
     * 1=菜单.
     */
    public static final int TYPE_MENU = 1;
    /**
     * 2=按钮.
     */
    public static final int TYPE_BTN = 2;
    /**
     * 3=tab页.
     */
    public static final int TYPE_TAB = 3;

    /**
     * 名称.
     */
    private String name;
    /**
     * 图标.
     */
    private String icon;
    /**
     * 路径.
     */
    private String path;
    /**
     * 类型(1=菜单 2=按钮).
     */
    private Integer type = TYPE_MENU;
    /**
     * 排序顺序.
     */
    @Column(name = "[index]")
    private Integer index;
    /**
     * 标记该菜单下是否有tabs选项卡(1:有tab页 0:无).
     */
    @Column(name = "has_tabs")
    private Integer hasTabs;
    /**
     * 权限编码.
     */
    @Column(name = "permission_code", length = 100)
    private String permissionCode;
    /**
     * 备注.
     */
    private String remark;

    @JsonIgnoreProperties({"children", "role_list"})
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @JsonIgnoreProperties({"parent", "role_list"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Menu> children;

    @JsonIgnoreProperties({"menu_list", "user_list"})
    @ManyToMany(mappedBy = "menuList")
    private List<Role> roleList;

}
