package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.ErrorCodes;
import com.bms.entity.Organization;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 公交场站导出excel的model。
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Data
public class BusTerminalExcelModel {

    @ColumnWidth(50)
    @ExcelProperty(value = "场站名称", index = 0)
    private String name;

    @ColumnWidth(50)
    @ExcelProperty(value = "场站编码", index = 1)
    private String code;

    @ExcelIgnore
    private Integer type;

    @ColumnWidth(50)
    @ExcelProperty(value = "场站类型", index = 2)
    private String typeValue;

    @ColumnWidth(50)
    @ExcelProperty(value = "场站面积", index = 3)
    private Float area;

    @ColumnWidth(50)
    @ExcelProperty(value = "场站地址", index = 4)
    private String address;

    /**
     * 1
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    @ExcelIgnore
    private Integer status;
    @ColumnWidth(15)
    @ExcelProperty(value = "状态", index = 5)
    private String statusText;

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
        throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR);
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

    public String getTypeValue() {
        if (type==null){
            return "";
        }
        if (type == 1) {
            return "综合类型";
        }
        if (type == 2) {
            return "类型二";
        }
        if (type == 1) {
            return "类型三";
        }
        return String.valueOf(type);
    }

    public Integer getType() {
        if (StringUtils.isBlank(typeValue)) {
            return -1;
        }
        if (typeValue.equals("类型二")) {
            return 2;
        }
        if (typeValue.equals("类型三")) {
            return 3;
        }
        return 1;
    }
}

