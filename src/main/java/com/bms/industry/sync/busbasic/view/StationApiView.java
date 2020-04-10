package com.bms.industry.sync.busbasic.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 站点.
 *
 * @author luojimeng
 * @date 2020/4/10
 */
@Data
public class StationApiView extends AbstractBusApiView2 {
    @JsonProperty(value = "line_id")
    private String oRouteId;
    @JsonProperty(value = "type")
    private Integer type;
    @JsonProperty(value = "site_order")
    private Integer index;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "up_down_flag")
    private Integer upDown;
    /**
     * 经度.
     */
    @JsonProperty(value = "gps_x")
    private Float longitude;
    /**
     * 纬度.
     */
    @JsonProperty(value = "gps_y")
    private Float latitude;
    /**
     * GPS夹角.
     */
    @JsonProperty(value = "gps_angle")
    private Float gpsAngle;
    @JsonProperty(value = "report_voice_file")
    private String reportVoiceFile;

    @JsonProperty(value = "station_version")
    private String version;
    @JsonProperty(value = "next_station")
    private String nextStation;
    @JsonProperty(value = "up_station")
    private String upStation;
    @JsonProperty(value = "upStaDistance")
    private String upStaDistance;
    @JsonProperty(value = "yesOrnointerval")
    private String yesOrnointerval;
    @JsonProperty(value = "intervalValue")
    private String intervalValue;
    @JsonProperty(value = "yesOrnoZhistation")
    private String yesOrnoZhistation;
    @JsonProperty(value = "radius")
    private Float radius;
    @JsonProperty(value = "zhistationValue")
    private String zhistationValue;
}
