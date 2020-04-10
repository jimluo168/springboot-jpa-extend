package com.bms.industry.sync.busbasic.view;

import com.bms.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * 车辆.
 *
 * @author luojimeng
 * @date 2020/4/10
 */
@Data
public class BusApiView extends AbstractBusApiView3 {
    /**
     * 车牌号.
     */
    @JsonProperty(value = "bus_no")
    private String licNo;
    @JsonProperty(value = "line_id")
    private String oRouteId;
    @JsonProperty(value = "m_id")
    private String terminalNo;
    /**
     * 车型(必填) 改为文本 20200320.
     * “车辆型号”改为“车型”，文本类型.
     */
    @JsonProperty(value = "cheXing")
    private String vehType;
    @JsonProperty(value = "bus_type")
    private  Integer fuelType;
    @JsonProperty(value = "bus_kind")
    private Integer busKind;
    @JsonProperty(value = "seat_num")
    private Integer seatNum;
    /**
     * 载客数量.
     */
    @JsonProperty(value = "load_num")
    private Integer loadNum;

    @JsonProperty(value = "factory_name")
    private String factoryName;
    /**
     * 生产日期.
     */
    @JsonProperty(value = "producte_date")
    @JsonFormat(pattern = Constant.DATE_FORMAT_YYYY_MM_DD, timezone = Constant.TIME_ZONE_GMT8)
    private Date productDate;
    /**
     * 投产日期.
     */
    @JsonProperty(value = "register_date")
    @JsonFormat(pattern = Constant.DATE_FORMAT_YYYY_MM_DD, timezone = Constant.TIME_ZONE_GMT8)
    private Date registerDate;
    /**
     * SIM卡号.
     */
    @JsonProperty(value = "sim_id")
    private String sim;
    @JsonProperty(value = "voideNum")
    private Integer voideNum;
    @JsonProperty(value = "current_lineid")
    private String currentLineid;
    @JsonProperty(value = "frame_number")
    private String frameNumber;
    @JsonProperty(value = "carCus")
    private String purpose;
    /**
     * 车辆尺寸(长).
     */
    @JsonProperty(value = "carLength")
    private Float length;
    /**
     * 是否助力(1=是  0=否).
     */
    @JsonProperty(value = "carHelp")
    private Integer carHelp;
    /**
     * 前后置发动机(1=前  2=后).
     */
    @JsonProperty(value = "engine")
    private Integer engine;
    /**
     * 是否空调(1=是  0=否).
     */
    @JsonProperty(value = "kongtiao")
    private Integer airCondition;
    /**
     * 1：正常 2：删除
     */
    @JsonProperty(value = "deleteSatus")
    private String deleteSatus;

}
