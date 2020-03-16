package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 从业人员表
 *
 * @author zouyongcan
 * @date 2020/3/16
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "practitioners")
public class Practitioner extends BaseEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 资格证号
     */
    @Column(name = "certificate_number", unique = true, nullable = false)
    private String certificateNumber;

    /**
     * 身份证号
     */
    @Column(name = "ID_number",unique = true, nullable = false)
    private String IDNumber;

    /**
     * 联系号码
      */
    @Column(unique = true, nullable = false)
    private String phone;

    /**
     * 通讯地址
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
     * 车队
     */
    private String fleet;

    /**
     *员工工号
     */
    @Column(name = "staff_number")
    private String staffNumber;

    /**
     * 从业类型
     */
    private String type;

    /**
     * 线路
     */
    private String line;

    /**
     * 备注
     */
    private String remark;

}