package com.bms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 网上数据申报.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bus_online_data_declares")
public class BusOnlineDataDeclare extends BusOnlineDataDeclareCommon {
    /**
     * 审核历史记录.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "declare")
    private List<BusOnlineDataDeclareAudit> auditList;
    /**
     * oss文件地址.
     */
    @Transient
    private String file;

}
