package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 专家知识库管理
 *
 * @author zouyongcan
 * @date 2020/4/3
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_knowledge_bases")
public class KnowledgeBase extends BaseEntity {

    /**
     * 标题.
     */
    private String title;
    /**
     * 作者.
     */
    private String author;
    /**
     * 正文 存放OSS /html/yyyMMdd/xxx.html.
     */
    @Column(length = 255)
    private String content;
    /**
     * 附件 多个以英文 , 号隔开.
     */
    @Column(length = 1000)
    private String attachs;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 行业.
     */
    private Integer industry;

}
