package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 公交场站 实体
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Data
@MappedSuperclass
public abstract class BusTerminalCommon extends BaseEntity {
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
     * 名称.
     */
    private String name;
    /**
     * 编号.
     */
    private String code;
    /**
     * 类型.
     */
    private Integer type;
    /**
     * 面积.
     */
    private Float area;
    /**
     * 地址.
     */
    private String address;
    /**
     * 经度.
     */
    private Float longitude;

    /**
     * 纬度.
     */
    private Float latitude;
    /**
     * 停车数.
     */
    @Column(name = "parking_number")
    private Integer parkingNumber;
    /**
     * 现场照片 多张用 , 隔开.
     */
    @Column(length = 1000)
    private String photos;
    /**
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;
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

}
