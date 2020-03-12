package com.bms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.bms.common.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
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
    private int type = TYPE_MENU;

    @ManyToOne
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Menu parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Menu> children;

}
