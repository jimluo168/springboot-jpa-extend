package com.bms.industry.sync.busbasic.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 司机-从业人员.
 *
 * @author luojimeng
 * @date 2020/4/10
 */
@Data
public class PassengerApiView extends AbstractBusApiView {
    /**
     * 姓名.
     */
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "ic_card")
    private String cardNumber;
    @JsonProperty(value = "id_card")
    private String idNumber;
    @JsonProperty(value = "c_id")
    private String oOrgId;
    @JsonProperty(value = "t_id")
    private String oTeamId;
    @JsonProperty(value = "l_id")
    private String oRouteId;
    @JsonProperty(value = "phone")
    private String phone;
    @JsonProperty(value = "jobNum")
    private String staffNumber;
    @JsonProperty(value = "driverYears")
    private Integer drivingAge;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "sex")
    private String gender;
//    @JsonProperty(value = "status")
//    private Integer status;
}
