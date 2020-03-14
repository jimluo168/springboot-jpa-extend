package com.bms.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernate_lazy_initializer","handler"})
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
    private int deleted = DELETE_FALSE;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getLastUpdUser() {
        return lastUpdUser;
    }

    public void setLastUpdUser(Long lastUpdUser) {
        this.lastUpdUser = lastUpdUser;
    }

    public Date getLastUpdDate() {
        return lastUpdDate;
    }

    public void setLastUpdDate(Date lastUpdDate) {
        this.lastUpdDate = lastUpdDate;
    }
}
