package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.common.domain.BaseEntity;
import com.bms.entity.BusSite;
import com.bms.entity.Organization;
import com.bms.industry.controller.BusSiteController;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 公交站点导出excel的model
 *
 * @author zouyongcan
 * @date 2020/3/18
 */
@Data
public class BusSiteExcelModel extends BaseEntity {
    private static final Logger logger = LoggerFactory.getLogger(BusSiteExcelModel.class);

    @ColumnWidth(50)
    @ExcelProperty(value = "站点名称", index = 0)
    private String name;

    @ColumnWidth(50)
    @ExcelProperty(value = "站点编码", index = 1)
    private String code;

    /**
     * 省.
     */
    @ExcelIgnore
    private String province;
    /**
     * 市.
     */
    @ExcelIgnore
    private String city;
    /**
     * 区/县.
     */
    @ExcelIgnore
    private String county;

    @ColumnWidth(50)
    @ExcelProperty(value = "所在区域", index = 2)
    private String region;

    /**
     * 经度.
     */
    @ExcelIgnore
    private Float longitude;
    /**
     * 纬度.
     */
    @ExcelIgnore
    private Float latitude;

    @ColumnWidth(50)
    @ExcelProperty(value = "经纬度", index = 3)
    private String longitudeAndLatitude;

    @ColumnWidth(50)
    @ExcelProperty(value = "站点位置", index = 4)
    private String address;

    /**
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    @ExcelIgnore
    private Integer status;

    @ColumnWidth(15)
    @ExcelProperty(value = "状态", index = 5)
    private String statusText;

    /**
     * 解析所属区域
     *
     * @return
     */
    public String getRegion() {
        return StringUtils.replace(
                StringUtils.join(new String[]{province, city, county, address}, ""),
                "null", "");
    }

    public String getProvince() {
        if (StringUtils.isBlank(region)) {
            return "";
        }
        if (region.contains("省")) {
            int begin = region.indexOf("省");
            return region.substring(0, begin + 1);
        }
        return "";
    }

    public String getCity() {
        if (StringUtils.isBlank(region)) {
            return "";
        }
        if (region.contains("市")) {
            int begin = region.indexOf("省");
            int end = region.indexOf("市");
            return region.substring(begin + 1, end + 1);
        }
        return "";
    }

    public String getLongitudeAndLatitude() {
        return longitude.toString()+ "," + latitude.toString();
    }

    public String getStatusText() {
        if (status == null) {
            return "";
        }
        if (status == BusSite.STATUS_TO_BE_AUDIT) {
            return "待审核";
        }
        if (status == BusSite.STATUS_PASS_AUDIT) {
            return "已通过";
        }
        if (status == BusSite.STATUS_UN_AUDIT) {
            return "未通过";
        }
        return status.toString();
    }
}
