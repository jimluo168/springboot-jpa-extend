package com.springboot.jpa.demo.entity;

import com.springboot.jpa.demo.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 操作日志.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "operation_logs")
public class OperationLog extends BaseEntity {
    /**
     * 用户名.
     */
    private String account;
    /**
     * 真实姓名.
     */
    @Column(name = "real_name")
    private String realName;
    /**
     * 模块.
     */
    private String module;
    /**
     * 功能.
     */
    @Column(name = "func_name")
    private String funcName;
    /**
     * 请求url 包括参数.
     */
    private String url;
    /**
     * 参数.
     */
    @Column(length = 2000)
    private String params;
    /**
     * IP.
     */
    @Column(length = 64)
    private String ip;

    /**
     * 前端传入的开始日期查询条件.
     */
    @Transient
    private Date begin;
    /**
     * 前端传入的结束日期查询条件
     */
    @Transient
    private Date end;
}
