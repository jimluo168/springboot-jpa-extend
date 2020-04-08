package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应急信息管理
 *
 * @author zouyongcan
 * @date 2020/4/8
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_info_releases")
public class MoInfoRelease extends BaseEntity {
    /**
     * 事件名称
     */
    private String name;
    /**
     * 事件分类
     */
    private Integer type;
    /**
     * 事件级别
     */
    private Integer level;
    /**
     * 发布内容
     */
    private String content;
    /**
     * 发布途径
     */
    private Integer channel;
}
