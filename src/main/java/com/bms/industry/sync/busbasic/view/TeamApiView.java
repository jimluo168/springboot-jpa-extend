package com.bms.industry.sync.busbasic.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * 公司.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@Data
public class TeamApiView extends AbstractBusApiView {
    @JsonProperty(value = "t_name")
    private String name;
    /**
     * 旧系统公司ID.
     */
    @JsonProperty(value = "cid")
    private String oOrgId;
    /**
     * 详细地址.
     */
    @JsonProperty(value = "t_addr")
    private String address;
    /**
     * 电话.
     */
    @JsonProperty(value = "t_tel")
    private String telephone;
    /**
     * 负责人.
     */
    @Column(name = "t_per")
    private String principal;
    /**
     * 车队编号.
     */
    @Column(name = "num")
    private String num;

    @JsonProperty(value = "status")
    private Integer status;

}
