package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.ErrorCodes;
import com.bms.entity.Organization;
import com.bms.sys.service.OrganizationService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 从业人员导出excel的model.
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Data
public class PractitionerExcelModel {
    @ExcelIgnore
    private final OrganizationService organizationService;

    @ColumnWidth(50)
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelIgnore
    private String gender;

    @ColumnWidth(50)
    @ExcelProperty(value = "性别", index = 1)
    private String genderText;

    @ExcelIgnore
    private Organization organization;

    @ColumnWidth(50)
    @ExcelProperty(value = "所属企业", index = 2)
    private String organizationName;

    @ColumnWidth(20)
    @ExcelProperty(value = "年龄", index = 3)
    private Integer age;

    @ColumnWidth(20)
    @ExcelProperty(value = "架龄", index = 4)
    private Integer drivingAge;

    @ColumnWidth(50)
    @ExcelProperty(value = "资格证号", index = 5)
    private String certNo;

    @ColumnWidth(50)
    @ExcelProperty(value = "身份证号", index = 6)
    private String idNumber;

    @ColumnWidth(50)
    @ExcelProperty(value = "从业类型", index = 7)
    private String type;

    /**
     * 1
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    @ExcelIgnore
    private Integer status;
    @ColumnWidth(15)
    @ExcelProperty(value = "状态", index = 8)
    private String statusText;

    public String getOrganizationName() {
        return this.organization.getName();
    }

    public String getGenderText(){
        if (gender == null) {
            return "";
        }
        if (gender.equals("M")) {
            return "男";
        }
        if (gender.equals("F")) {
            return "女";
        }
        if (gender.equals('N')) {
            return "未知";
        }
        return gender;
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

    public Organization getOrganization() {
        if (StringUtils.isBlank(organizationName)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "公司名称不能为空");
        }
        Organization organization = organizationService.findByName(organizationName);
        if (organization == null) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "公司名称[" + organizationName + "]不存在");
        }
        return organization;
    }
}
