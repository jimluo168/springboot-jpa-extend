package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@MappedSuperclass
public abstract class OrganizationCommon extends BaseEntity {
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
    @Column(unique = true)
    private String name;
    /**
     * 级别.
     */
    private Integer level;
    /**
     * 公司编号.
     */
    @Column(name = "company_no", unique = true)
    private String companyNo;
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
     * 企业规模.
     */
    private String scale;
    /**
     * 销售额.
     */
    @Column(name = "sales_volume")
    private Integer salesVolume;
    /**
     * 员工人数.
     */
    @Column(name = "staff_number")
    private Integer staffNumber;
    /**
     * 车辆数.
     */
    @Column(name = "vehicle_number")
    private Integer vehicleNumber;
    /**
     * 车站数.
     */
    @Column(name = "station_number")
    private Integer stationNumber;
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
    private Integer status = STATUS_TO_BE_AUDIT;
    /**
     * 理由.
     */
    @Column(length = 500)
    private String reason;
    /**
     * 上级公司.
     */
    @JsonIgnoreProperties({"children", "audit_list"})
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Organization parent;
    /**
     * 子公司.
     */
    @JsonIgnoreProperties({"parent", "audit_list"})
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Organization> children;
    /**
     * 同步贵阳旧系统的主键.
     */
    @Column(name = "o_id")
    private String oId;

    @Column(name = "o_parent_id")
    private String oParentId;
    /**
     * 排序顺序.
     */
    @Column(name = "[index]")
    private Integer index;
}
