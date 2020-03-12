package com.bms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 机构表(organizations)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "organizations")
public class Organization extends BaseEntity {
    /**
     * 1=待审核.
     */
    public static final int STATUS_TO_BE_AUDIT = 1;
    /**
     * 2=通过审核.
     */
    public static final int STATUS_PASS_AUDIT = 2;
    /**
     * 3=未通过审核.
     */
    public static final int STATUS_UN_AUDIT = 3;

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
    @Column(name = "operate_route", length = 1000)
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
    /**
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    private int status = STATUS_TO_BE_AUDIT;
    /**
     * 理由.
     */
    @Column(length = 500)
    private String reason;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @JSONField(name = "audit_list")
    private List<OrganizationAudit> auditList;

}
