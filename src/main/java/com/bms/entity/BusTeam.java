package com.bms.entity;

import lombok.Cleanup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 车队管理表.
 *
 * @author luojimeng
 * @date 2020/3/17
 */
@Data
@EqualsAndHashCode(of = "id")
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_carteam")
public class BusTeam implements Serializable {

    @Id
    private Long id;
    /**
     * 旧系统的PK.
     */
    private String oid;
    /**
     * 名称.
     */
    @Column(name = "t_name")
    private String name;
    /**
     * 旧系统公司ID.
     */
    @Column(name = "o_cid")
    private String ocid;
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
    /**
     * 创建时间.
     */
    @Column(name = "insert_date")
    private Date createDate;
    /**
     * 创建人ID.
     */
    @Column(name = "insert_uid")
    private Integer createUser;
    /**
     * 状态.
     */
    private Integer status = 1;
}
