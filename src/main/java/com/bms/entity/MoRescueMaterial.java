package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 救援资源管理-物资.
 *
 * @author luojimeng
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "mo_rescue_materials")
public class MoRescueMaterial extends BaseEntity {
    /**
     * 1=空闲.
     */
    public static final int STATUS_FREE = 1;
    /**
     * 2=执行.
     */
    public static final int STATUS_PERFORM = 2;

    /**
     * 物资编号.
     */
    private String code;
    /**
     * 物资名称.
     */
    private String name;
    /**
     * 物资类型.
     */
    private String type;
    /**
     * 规格.
     */
    private String spec;
    /**
     * 计量单位.
     */
    private String unit;
    /**
     * 数量.
     */
    private Integer quantity;
    /**
     * 物资来源.
     */
    private String origin;
    /**
     * 参数.
     */
    private String parameter;
    /**
     * 用途.
     */
    private String purpose;
    /**
     * 存放场所 地点.
     */
    private String storePlace;
    /**
     * 单价.
     */
    private BigDecimal price;
    /**
     * 总价.
     */
    private BigDecimal totalPrice;
    /**
     * 生产商.
     */
    private String producer;
    /**
     * 使用年限.
     */
    private String usefulLife;
    /**
     * 出厂日期.
     */
    private Date productionDate;
    /**
     * 购买日期.
     */
    private Date purchaseDate;
    /**
     * 定期保修间隔.
     */
    private String maintenanceInterval;
    /**
     * 负责人.
     */
    private String principal;
    /**
     * 联系方式.
     */
    private String phone;
    /**
     * 备注.
     */
    private String remark;
    /**
     * 状态(1:空闲 2:执行任务).
     */
    private Integer status;
}
