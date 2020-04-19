package com.springboot.jpa.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.jpa.demo.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 字典表.
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "sys_dict")
public class Dictionary extends BaseEntity {
    /**
     * 0=禁用.
     */
    public static final int STATUS_DISABLED = 0;
    /**
     * 1=启用.
     */
    public static final int STATUS_ENABLED = 1;
    /**
     * 编码.
     */
    @Column(length = 50)
    private String code;
    /**
     * 数据类型.
     */
    @Column(length = 50)
    private String type;
    /**
     * 数据值.
     */
    @Column(length = 200)
    private String value;
    /**
     * 数据文本描述.
     */
    private String text;
    /**
     * 排序顺序.
     */
    @Column(name = "[index]")
    private int index;
    /**
     * 备注.
     */
    private String remark;
    /**
     * 状态(0:禁用 1:启用)
     */
    private Integer status = STATUS_ENABLED;

    @JsonIgnoreProperties({"children"})
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Dictionary parent;

    @JsonIgnoreProperties({"parent"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Dictionary> children;
}
