package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * 公交站点管理.
 *
 * @author luojimeng
 * @date 2020/3/17
 */
@Data
@MappedSuperclass
public abstract class BusSiteCommon extends BaseEntity {
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
     * 线路.
     */
    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute route;
    /**
     * 排序顺序.
     */
    @Column(name = "[index]")
    private Integer index;
    /**
     * 上下行.
     */
    private Integer upDown;
    /**
     * 站点名称.
     */
    private String name;
    /**
     * 站点编号.
     */
    private String code;
    /**
     * 省.
     */
    private String province;
    /**
     * 市.
     */
    private String city;
    /**
     * 区/县.
     */
    private String county;
    /**
     * 详细地址.
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
     * GPS夹角.
     */
    private Float gpsAngle;
    /**
     * 半径.
     */
    private Float radius;
    /**
     * 现场照片 以英文 , 号隔开.
     */
    @Column(length = 1000)
    private String photos;
    /**
     * 备注.
     */
    @Column(length = 500)
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
