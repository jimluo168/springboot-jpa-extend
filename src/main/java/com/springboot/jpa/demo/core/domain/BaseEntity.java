package com.springboot.jpa.demo.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernate_lazy_initializer", "handler"})
public abstract class BaseEntity implements Serializable {
    /**
     * 0=正常.
     */
    public static final int DELETE_FALSE = 0;
    /**
     * 1=删除.
     */
    public static final int DELETE_TRUE = 1;

    /**
     * 主键PK(id)
     */
    @Id
    private Long id;
    /**
     * 删除状态（0=正常，1=删除）(is_delete).
     */
    @Column(name = "is_delete")
    private Integer deleted = DELETE_FALSE;
    /**
     * 创建用户(create_user).
     */
    @CreatedBy
    @Column(name = "create_user")
    private Long createUser;
    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_date")
    private Date createDate;
    /**
     * 最后更新用户(last_upd_user).
     */
    @LastModifiedBy
    @Column(name = "last_upd_user")
    private Long lastUpdUser;
    /**
     * 最后更新时间(last_upd_date).
     */
    @LastModifiedDate
    @Column(name = "last_upd_date")
    private Date lastUpdDate;
}
