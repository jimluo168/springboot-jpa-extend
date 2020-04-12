package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 从业人员表.
 *
 * @author zouyongcan
 * @date 2020/3/16
 */
@Data
@MappedSuperclass
public abstract class PractitionerCommon extends BaseEntity {
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
     * 姓名.
     */
    private String name;
    /**
     * 性别.
     */
    @Column(length = 1)
    private String gender;
    /**
     * 年龄.
     */
    private Integer age;
    /**
     * 驾龄.
     */
    @Column(name = "driving_age")
    private Integer drivingAge;
    /**
     * 资格证号.
     */
    @Column(name = "cert_no", unique = true)
    private String certNo;
    /**
     * 身份证号.
     */
    @Column(name = "id_number", unique = true, nullable = false)
    private String idNumber;
    /**
     * 联系号码.
     */
    @Column(unique = true, nullable = false)
    private String phone;
    /**
     * 电子邮箱
     */
    @Column(unique = true)
    private String email;
    /**
     * 通讯地址.
     */
    private String address;
    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 车队.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private BusTeam carTeam;
    /**
     * 员工工号.
     */
    @Column(name = "staff_number")
    private String staffNumber;
    /**
     * 卡号.
     */
    @Column(name = "card_number")
    private String cardNumber;
    /**
     * 从业类型.
     */
    private Integer type;
    /**
     * 线路.
     */
//    private String line;
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;
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
     * -----------补充旧系统字段 BUS同步接口-----------
     */
    @Column(name = "o_id")
    private Integer oId;
    @Column(name = "o_org_id")
    private String oOrgId;
    @Column(name = "o_team_id")
    private String oTeamId;
    @Column(name = "o_route_id")
    private String oRouteId;
}
