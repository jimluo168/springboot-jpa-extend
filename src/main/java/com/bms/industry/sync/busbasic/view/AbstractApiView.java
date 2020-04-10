package com.bms.industry.sync.busbasic.view;

import com.bms.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
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
public abstract class AbstractApiView implements Serializable {
    @JsonProperty(value = "id")
    private String oId;
    /**
     * 删除状态（0=正常，1=删除）(is_delete).
     */
    private Integer deleted = DELETE_FALSE;
    /**
     * 创建用户(create_user).
     */
    @JsonProperty(value = "insert_uid")
    private Long createUser;
    /**
     * 创建时间
     */
    @JsonProperty(value = "insert_date")
    @JsonFormat(pattern = Constant.DATE_FORMAT_TIME_ZONE, timezone = Constant.TIME_ZONE_GMT8)
    private Date createDate;
}
