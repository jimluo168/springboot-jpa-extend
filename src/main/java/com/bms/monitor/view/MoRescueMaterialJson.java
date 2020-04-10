package com.bms.monitor.view;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 存储应急资源的使用数量.
 *
 * @author luojimeng
 * @date 2020/4/10
 */
@Data
public class MoRescueMaterialJson implements Serializable {
    private Long id;
    /**
     * 使用数量.
     */
    private BigDecimal usageQuantity;
}
