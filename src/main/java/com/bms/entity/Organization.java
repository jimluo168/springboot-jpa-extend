package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机构表(organizations)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "organizations")
public class Organization extends BaseEntity {

    /**
     * 组织名称.
     */
    private String name;
    /**
     * 级别.
     */
    private int level;
    /**
     * 省.
     */
    private String province;
    /**
     * 市.
     */
    private String city;
    /**
     * 区/县.
     */
    private String county;
    /**
     * 详细地址.
     */
    private String address;
    /**
     * 营业执照 存放执照url 逗号隔开.
     */
    @Column(name = "business_license")
    private String businessLicense;
    /**
     * 经营范围.
     */
    @Column(name = "business_scope")
    private String businessScope;
    /**
     * 运营路线.
     */
    @Column(name = "operate_route")
    private String operateRoute;
    /**
     * 负责人.
     */
    private String principal;
    /**
     * 联系方式.
     */
    private String contact;
    /**
     * 备注.
     */
    private String remark;
}
