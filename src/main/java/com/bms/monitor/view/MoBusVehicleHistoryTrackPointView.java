package com.bms.monitor.view;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 车辆历史轨迹.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusVehicleHistoryTrackPointView implements Serializable {
    /**
     * 当前纬度.
     */
    private BigDecimal latitude;
    /**
     * 当前经度.
     */
    private BigDecimal longitude;
}
