package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.Constant;
import com.bms.DictConstant;
import com.bms.ErrorCodes;
import com.bms.common.util.DateUtil;
import com.bms.entity.BusRoute;
import com.bms.entity.Dictionary;
import com.bms.entity.Organization;
import com.bms.industry.service.BusRouteService;
import com.bms.sys.service.DictService;
import com.bms.sys.service.OrganizationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 基础业务管理-公交车辆管理-Excel模型.
 *
 * @author luojimeng
 * @date 2020/3/19
 */
@Data
public class VehicleExcelModel {

    @ExcelIgnore
    private BusRouteService busRouteService;
    @ExcelIgnore
    private OrganizationService organizationService;
    @ExcelIgnore
    private DictService dictService;

    public VehicleExcelModel() {
        this(null, null, null);
    }

    public VehicleExcelModel(BusRouteService busRouteService, OrganizationService organizationService, DictService dictService) {
        this.busRouteService = busRouteService;
        this.organizationService = organizationService;
        this.dictService = dictService;
    }

    /**
     * 车牌号.
     */
    @ColumnWidth(30)
    @ExcelProperty(value = "车牌号", index = 0)
    private String licNo;
    /**
     * 所属企业.
     */
    @ExcelIgnore
    private Organization organization;
    @ColumnWidth(50)
    @ExcelProperty(value = "所属企业", index = 1)
    private String orgName;
    /**
     * VIN码.
     */
    @ExcelProperty(value = "VIN码", index = 2)
    private String vin;
    /**
     * 车型(必填) 改为文本 20200320.
     * “车辆型号”改为“车型”，文本类型.
     */
    @ColumnWidth(50)
    @ExcelProperty(value = "车型", index = 3)
    private String vehType;
    /**
     * 燃料类型(字典表).
     */
    @ExcelIgnore
    private Integer fuelType;
    @ColumnWidth(30)
    @ExcelProperty(value = "燃料类型", index = 4)
    private String fuelTypeText;
    /**
     * 上牌时间.
     */
    @ExcelIgnore
    private Date cardTime;
    @ColumnWidth(40)
    @DateTimeFormat(Constant.DATE_FORMAT_YYYY_MM_DD)
    @ExcelProperty(value = "上牌时间", index = 5)
    private Date cardTimeGMT8;
    /**
     * 座位数量.
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "座位数量", index = 6)
    private Integer seatNum;
    @ExcelIgnore
    private BusRoute busRoute;
    /**
     * 线路.
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "线路", index = 7)
    private String busRouteText;
    /**
     * 状态(1:待审核 2:通过审核 3:未通过审核).
     */
    @ExcelIgnore
    private Integer status;
    @ColumnWidth(20)
    @ExcelProperty(value = "状态", index = 8)
    private String statusText;

    public Organization getOrganization() {
        if (StringUtils.isBlank(orgName)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "企业名称不能为空");
        }
        Organization org = organizationService.findByName(orgName);
        if (org == null) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "企业名称不存在");
        }
        return org;
    }

    public String getOrgName() {
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    public String getFuelTypeText() {
        if (fuelType == null) {
            return "";
        }
        List<Dictionary> list = dictService.findByCode(DictConstant.FUEL_TYPE);
        if (list == null || list.isEmpty()) {
            return "";
        }
        String text = "";
        for (Dictionary dict : list) {
            if (StringUtils.equals(dict.getValue(), fuelType.toString())) {
                text = dict.getText();
                break;
            }
        }
        return text;
    }

    public Integer getFuelType() {
        if (StringUtils.isBlank(fuelTypeText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR);
        }
        List<Dictionary> list = dictService.findByCode(DictConstant.FUEL_TYPE);
        if (list == null || list.isEmpty()) {
            return null;
        }
        Integer type = null;
        for (Dictionary dict : list) {
            if (StringUtils.equals(dict.getText(), fuelTypeText)) {
                type = Integer.parseInt(dict.getValue());
            }
        }
        return type;
    }

    public String getBusRouteText() {
        if (busRoute == null) {
            return "";
        }
        return busRoute.getName();
    }

    public Date getCardTime() {
        if (cardTimeGMT8 == null) {
            return null;
        }
        return DateUtil.gmt82utc(cardTimeGMT8);
    }

    public Date getCardTimeGMT8() {
        if (cardTime == null) {
            return null;
        }
        return DateUtil.utc2gmt8(cardTime);
    }

    public BusRoute getBusRoute() {
        if (StringUtils.isBlank(busRouteText)) {
            return null;
        }
        List<BusRoute> list = busRouteService.findByName(busRouteText);
        if (list == null || list.isEmpty()) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "线路名称不存在");
        }
        return list.get(0);
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
