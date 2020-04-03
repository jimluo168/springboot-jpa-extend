package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.DictConstant;
import com.bms.ErrorCodes;
import com.bms.entity.Dictionary;
import com.bms.entity.Organization;
import com.bms.entity.Practitioner;
import com.bms.sys.service.DictService;
import com.bms.sys.service.OrganizationService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 从业人员导出excel的model.
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Data
public class PractitionerExcelModel {
    @ExcelIgnore
    private OrganizationService organizationService;

    @ExcelIgnore
    private DictService dictService;

    public PractitionerExcelModel(){
        this(null,null);
    }

    public PractitionerExcelModel(OrganizationService organizationService, DictService dictService){
        this.organizationService = organizationService;
        this.dictService = dictService;
    }

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

    @ExcelIgnore
    private Integer type;

    @ColumnWidth(50)
    @ExcelProperty(value = "从业类型", index = 7)
    private String typeText;

    /**
     * 1
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    @ExcelIgnore
    private Integer status;
    @ColumnWidth(15)
    @ExcelProperty(value = "状态", index = 8)
    private String statusText;

    @ExcelIgnore
    @ExcelProperty(value = "手机号码", index = 9)
    private String phone;

    public String getOrganizationName() {
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    public String getGenderText(){
        if (gender == null) {
            return "未知";
        }
        if (gender.equals("M")) {
            return "男";
        }
        if (gender.equals("F")) {
            return "女";
        }
        return "未知";
    }

    public String getGender(){
        if(genderText == null){
            return "N";
        }
        if (genderText.equals("男")) {
            return "M";
        }
        if (genderText.equals("女")) {
            return "F";
        }
        return "N";
    }

    public String getStatusText() {
        if (status == null) {
            return "";
        }
        if (status == Practitioner.STATUS_TO_BE_AUDIT) {
            return "待审核";
        }
        if (status == Practitioner.STATUS_PASS_AUDIT) {
            return "已通过";
        }
        if (status == Practitioner.STATUS_UN_AUDIT) {
            return "未通过";
        }
        return status.toString();
    }

    public Integer getStatus() {
        if (StringUtils.isBlank(statusText)) {
            return Practitioner.STATUS_TO_BE_AUDIT;
        }
        if (StringUtils.equals(statusText, "待审核")) {
            return Practitioner.STATUS_TO_BE_AUDIT;
        }
        if (StringUtils.equals(statusText, "已通过")) {
            return Practitioner.STATUS_PASS_AUDIT;
        }
        if (StringUtils.equals(statusText, "未通过")) {
            return Practitioner.STATUS_UN_AUDIT;
        }
        return Practitioner.STATUS_TO_BE_AUDIT;
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

    public String getTypeText() {
        if (type == null) {
            return "";
        }
        List<Dictionary> list = dictService.findByCode(DictConstant.EMPLOYMENT_TYPE);
        if (list == null || list.isEmpty()) {
            return "";
        }
        String text = "";
        for (Dictionary dict : list) {
            if (StringUtils.equals(dict.getValue(), type.toString())) {
                text = dict.getText();
                break;
            }
        }
        return text;
    }

    public Integer getType() {
        if (StringUtils.isBlank(typeText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR);
        }
        List<Dictionary> list = dictService.findByCode(DictConstant.EMPLOYMENT_TYPE);
        if (list == null || list.isEmpty()) {
            return null;
        }
        Integer type = null;
        for (Dictionary dict : list) {
            if (StringUtils.equals(dict.getText(), typeText)) {
                type = Integer.parseInt(dict.getValue());
            }
        }
        return type;
    }

    // excel表格没有手机时默认设置为0
    public String getPhone(){
        if(phone == null){
            return "0";
        }
        return this.phone;
    }
}
