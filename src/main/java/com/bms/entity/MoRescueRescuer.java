package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 救援资源管理-人员.
 *
 * @author luojimeng
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_rescue_rescuers")
public class MoRescueRescuer extends BaseEntity {
    /**
     * 1=待审核.
     */
    public static final int STATUS_TO_BE_AUDIT = 1;
    /**
     * 3=未通过审核.
     */
    public static final int STATUS_UN_AUDIT = 3;
    /**
     * 2=空闲=审核通过.
     */
    public static final int STATUS_FREE = 2;
    /**
     * 4=执行.
     */
    public static final int STATUS_PERFORM = 4;

    /**
     * 姓名.
     */
    private String name;
    /**
     * 性别(M:男 F:女 N:未知).
     */
    private String gender;
    /**
     * 员工工号.
     */
    private String staffNo;
    /**
     * 联系电话.
     */
    private String phone;
    /**
     * 所属单位名称.
     */
    private String orgName;
    /**
     * 所属部门.
     */
    private String department;
    /**
     * 职位.
     */
    private String position;
    /**
     * 单位地址.
     */
    private String orgAddress;
    /**
     * 备注.
     */
    private String remark;
    /**
     * 状态(1:待审核 2:空闲 3:未通过 4:执行).
     */
    private Integer status = STATUS_TO_BE_AUDIT;
    /**
     * 理由.
     */
    private String reason;
}
