package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * 用户.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    /**
     * 用户状态-启用
     */
    public static final int STATUS_ENABLE = 1;

    /**
     * 用户状态-禁用
     */
    public static final int STATUS_DISABLE = 0;

    /**
     * 账号.
     */
    @Column(unique = true, nullable = false)
    private String account;
    /**
     * 密码.
     */
    @JsonProperty(access = WRITE_ONLY)
    private String passwd;
    /**
     * 盐 干扰码.
     */
    @JsonIgnore
    private String salt;
    /**
     * 真实姓名.
     */
    @Column(name = "real_name")
    private String realName;
    /**
     * 备注.
     */
    private String remark;

    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 所属角色.
     */
    @JsonIgnoreProperties({"user_list", "menu_list"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * 用户状态
     */
    private int status = STATUS_ENABLE;
}
