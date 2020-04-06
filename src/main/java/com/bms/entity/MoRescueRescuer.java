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
     * 1=空闲.
     */
    public static final int STATUS_FREE = 1;
    /**
     * 2=执行.
     */
    public static final int STATUS_PERFORM = 2;

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
     * 状态(1:空闲 2:执行任务).
     */
    private Integer status;
}
