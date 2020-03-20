package com.bms.sys.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.entity.Organization;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 企业管理-Excel模型.
 *
 * @author luojimeng
 * @date 2020/3/16
 */
@Data
public class OrganizationExcelModel {
    /**
     * 组织名称.
     */
    @ColumnWidth(50)
    @ExcelProperty(value = "企业名称", index = 0)
    private String name;
    /**
     * 级别.
     */
    @ExcelIgnore
    private Integer level;

    @ColumnWidth(20)
    @ExcelProperty(value = "组织级别", index = 1)
    private String levelText;

    @ColumnWidth(50)
    @ExcelProperty(value = "所属区域", index = 2)
    private String region;

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
    /**
     * 详细地址.
     */
    @ExcelIgnore
    private String address;
    /**
     * 企业规模.
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "企业规模", index = 3)
    private String scale;
    /**
     * 负责人.
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "负责人", index = 4)
    private String principal;
    /**
     * 联系方式.
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "联系方式", index = 5)
    private String contact;
    /**
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    @ExcelIgnore
    private Integer status;
    @ColumnWidth(15)
    @ExcelProperty(value = "状态", index = 6)
    private String statusText;

    public Integer getLevel() {
        if (StringUtils.isBlank(levelText)) {
            return -1;
        }
        if (StringUtils.equals(levelText, "级别一")) {
            return 1;
        }
        if (StringUtils.equals(levelText, "级别二")) {
            return 2;
        }
        if (StringUtils.equals(levelText, "级别三")) {
            return 3;
        }
        return -1;
    }

    public String getLevelText() {
        if (level == null) {
            return "";
        }
        if (level == 1) {
            return "级别一";
        }

        if (level == 2) {
            return "级别二";
        }

        if (level == 3) {
            return "级别三";
        }
        return level.toString();
    }

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

    public String getCounty() {
        if (StringUtils.isBlank(region)) {
            return "";
        }
        if (region.contains("区") || region.contains("县")) {
            int begin = region.indexOf("市");
            int end1 = region.indexOf("区");
            int end2 = region.indexOf("县");
            return region.substring(begin + 1, (end1 > -1 ? end1 : end2) + 1);
        }
        return "";
    }

    public String getAddress() {
        if (StringUtils.isBlank(region)) {
            return "";
        }
        int begin1 = region.indexOf("区");
        int begin2 = region.indexOf("县");
        int begin = begin1 > -1 ? begin1 : begin2;
        if (begin == -1) {
            return "";
        }
        return region.substring(begin, region.length());
    }


    public Integer getStatus() {
        if (StringUtils.isBlank(statusText)) {
            return -1;
        }
        if (StringUtils.equals(statusText, "待审核")) {
            return Organization.STATUS_TO_BE_AUDIT;
        }
        if (StringUtils.equals(statusText, "已通过")) {
            return Organization.STATUS_PASS_AUDIT;
        }
        if (StringUtils.equals(statusText, "未通过")) {
            return Organization.STATUS_UN_AUDIT;
        }
        return -1;
    }

    public String getStatusText() {
        if (status == null) {
            return "";
        }
        if (status == Organization.STATUS_TO_BE_AUDIT) {
            return "待审核";
        }
        if (status == Organization.STATUS_PASS_AUDIT) {
            return "已通过";
        }
        if (status == Organization.STATUS_UN_AUDIT) {
            return "未通过";
        }
        return status.toString();
    }
}
