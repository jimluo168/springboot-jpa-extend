package com.bms.monitor.sync.view;

import lombok.Data;

import java.io.Serializable;

/**
 * Redis缓存-车辆部分信息.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusVehicleCache implements Serializable {
    /**
     * 车辆ID.
     */
    private Long vehId;
    /**
     * 座位数.
     */
    private Integer seatNum;
}
