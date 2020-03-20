package com.bms.entity;

import com.bms.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 投诉建议管理.
 *
 * @author luojimeng
 * @date 2020/3/18
 */
@Data
@MappedSuperclass
public abstract class SuggestCommon extends BaseEntity {
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
     * 类型.
     */
    private Integer type;
    /**
     * 内容.
     */
    @Column(length = 2000)
    private String content;
    /**
     * 发生时间.
     */
    private Date time;
    /**
     * 投诉人.
     */
    private String promoter;
    /**
     * 照片 多个用英文 , 号隔开.
     */
    private String photos;
    /**
     * 备注.
     */
    @Column(length = 500)
    private String remark;
    /**
     * 审核理由-意见.
     */
    @Column(length = 500)
    private String reason;
    /**
     * 审核人.
     */
    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private User auditor;
    /**
     * 审核时间.
     */
    private Date auditTime;
    /**
     * 状态(1=待审核 2=通过审核 3=未通过审核).
     */
    private Integer status = STATUS_TO_BE_AUDIT;
    /**
     * 审核历史记录.
     */
    @JsonIgnoreProperties("suggest")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "suggest")
    private List<SuggestAudit> auditList;

}
