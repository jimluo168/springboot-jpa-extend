package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;

/**
 * 操作日志.
 *
 * @author luojimeng
 * @date 2020/3/14
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
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
     * 企业名称.
     */
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 组织结构名称.
     */
    @Column(name = "org_name")
    private String orgName;
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
}
