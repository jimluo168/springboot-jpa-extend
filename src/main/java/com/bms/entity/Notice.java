package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/18
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "adm_notices")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Notice extends BaseEntity {
    /**
     * 标题.
     */
    private String title;
    /**
     * 作者.
     */
    private String author;
    /**
     * 内容 存放OSS /html/yyyMMdd/xxx.html.
     */
    @Column(length = 255)
    private String content;
    /**
     * 附件 多个以英文 , 号隔开.
     */
    @Column(length = 1000)
    private String attachs;
    /**
     * 文章类型(字典表).
     */
    private Integer type;
}
