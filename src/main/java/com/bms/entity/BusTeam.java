package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 车队管理表.
 *
 * @author luojimeng
 * @date 2020/3/17
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_carteams")
public class BusTeam extends BaseEntity {
    /**
     * 旧系统的PK.
     */
    @Column(name = "o_id")
    private String oId;
    /**
     * 名称.
     */
    @Column(name = "t_name")
    private String name;
    /**
     * 旧系统公司ID.
     */
    @Column(name = "o_org_id")
    private String oOrgId;
    /**
     * 新公司ID.
     */
    @Column(name = "org_id")
    private Long orgId;
    /**
     * 地址.
     */
    @Column(name = "t_addr")
    private String address;
    /**
     * 电话.
     */
    @Column(name = "t_tel")
    private String telephone;
    /**
     * 负责人.
     */
    @Column(name = "t_per")
    private String principal;
    /**
     * 车队编号.
     */
    @Column(name = "num")
    private String num;
//    /**
//     * 创建时间.
//     */
//    @Column(name = "insert_date")
//    private Date createDate;
//    /**
//     * 创建人ID.
//     */
//    @Column(name = "insert_uid")
//    private Integer createUser;
    /**
     * 状态.
     */
    private Integer status = 1;
}
