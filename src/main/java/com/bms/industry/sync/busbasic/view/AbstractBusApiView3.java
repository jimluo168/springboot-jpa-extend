package com.bms.industry.sync.busbasic.view;

import com.bms.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;

/**
 * 公共类.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@Data
public abstract class AbstractBusApiView3 implements Serializable {
    @JsonProperty(value = "id")
    private String code;
    /**
     * 创建用户(create_user).
     */
    @JsonProperty(value = "insert_uid")
    private Long createUser;
    /**
     * 创建时间.
     */
    @JsonProperty(value = "insert_date")
    @JsonFormat(pattern = Constant.DATE_FORMAT_YYYY_MM_DD_HH_mm_ss, timezone = Constant.TIME_ZONE_GMT8)
    private Date createDate;
}
