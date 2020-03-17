package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 公交场站 实体
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "bus_terminals")
public class BusTerminal extends BaseEntity {

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
    private String type;
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

}
