package com.bms.monitor.sync.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Redis缓存-车辆部分信息.
 *
 * @author luojimeng
 * @date 2020/4/15
 */
@Data
public class MoBusVehicleCache implements Serializable {

    /**
     * 缓存KEY cache:busvehicle:{veh_code}.
     */
    public static final String CACHE_BUSVEHICLE_KEYS = "cache:busvehicle:*";
    public static final String CACHE_BUSVEHICLE_KEY = "cache:busvehicle:%s";
    public static final int CACHE_BUSVEHICLE_KEY_EXP_SECONDS = 26 * 60 * 60;
    /**
     * 车辆ID.
     */
    private Long vehId;
    /**
     * 车辆编号.
     */
    @JsonProperty(access = WRITE_ONLY)
    private String vehCode;
    /**
     * 座位数.
     */
    private Integer seatNum;
    /**
     * 公司ID.
     */
    private Long orgId;
    /**
     * 车队ID.
     */
    private Long teamId;
    /**
     * 线路ID.
     */
    private Long routeId;
}
