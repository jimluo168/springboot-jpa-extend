package com.bms.industry.view;

import com.bms.entity.BusRoute;
import com.bms.entity.BusTeam;
import com.bms.entity.Organization;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/3/31
 */
@Data
public class DataDeclareTotalRetrieval {
    private List<DataDeclareRetrieval> list;

    private DataDeclareTotal total;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Organization organization;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BusRoute busRoute;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BusTeam carTeam;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String orgName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String vehCode;
    /**
     * 统计的开始时间.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date begin;
    /**
     * 统计的结束时间.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date end;
}
