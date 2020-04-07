package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 专家库管理
 *
 * @author zouyongcan
 * @date 2020/4/7
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_experts")
public class MoExpert extends BaseEntity {
    /**
     * 头像.
     */
    private String photo;

    /**
     * 姓名.
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String nation;

    /**
     * 出生日期
     */
    private String birth;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 身份证
     */
    private String idNumber;
    /**
     * 政治面貌
     */
    private String politicalAffiliation;
    /**
     * 毕业学校
     */
    private String college;
    /**
     * 专业
     */
    private String major;
    /**
     * 最高学历
     */
    private String qualifications;
    /**
     * 移动电话
     */
    private String mobilePhone;
    /**
     * 办工电话
     */
    private String officePhone;
    /**
     * 传真
     */
    private String fax;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 家庭住址
     */
    private String homeAddress;
    /**
     * 所在行业
     */
    private Integer industry;
    /**
     * 专家领域
     */
    private Integer field;
    /**
     * 专家级别
     */
    private Integer level;
    /**
     * 工作单位
     */
    private String workplace;
    /**
     * 职称
     */
    private String title;
    /**
     * 工龄
     */
    private Integer workAge;
    /**
     * 单位地址
     */
    private String officeAddress;
    /**
     * 专业描述
     */
    private String majorDes;
    /**
     * 工作简历
     */
    private String resume;


}
