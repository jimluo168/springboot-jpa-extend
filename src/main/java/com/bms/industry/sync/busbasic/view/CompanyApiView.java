package com.bms.industry.sync.busbasic.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 公司.
 *
 * @author luojimeng
 * @date 2020/4/9
 */
@Data
public class CompanyApiView extends AbstractApiView {

    @JsonProperty(value = "c_name")
    private String name;
    @JsonProperty(value = "c_city")
    private String city;
    /**
     * 级别.
     */
    @JsonProperty(value = "c_level")
    private Integer level;

    @JsonProperty(value = "up_cid")
    private String oParentId;
    /**
     * 详细地址.
     */
    @JsonProperty(value = "c_addr")
    private String address;
    /**
     * 备注.
     */
    @JsonProperty(value = "comment")
    private String remark;
    /**
     * 排序顺序.
     */
    @JsonProperty(value = "order_num")
    private Integer index;
    /**
     * 公司编号.
     */
    @JsonProperty(value = "num")
    private String companyNo;

}
