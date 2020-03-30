package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 网上数据申报管理.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Data
@MappedSuperclass
public class BusOnlineDataDeclareCommon extends BaseEntity {
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
     * 所属企业.
     */
    @JsonIgnoreProperties("audit_list")
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    /**
     * 申报人
     */
    private String declarer;

    /**
     * 申报时间-开始
     */
    private Date startTime;

    /**
     * 申报时间-结束
     */
    private Date endTime;

    /**
     * 状态(1=待审核 2=通过审核 3=未通过审核).
     */
    private Integer status = STATUS_TO_BE_AUDIT;

    /**
     * 理由.
     */
    @Column(length = 500)
    private String reason;

    /**
     * 明细表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "declare")
    private List<BusOnlineDataDeclareItem> itemList;

}
