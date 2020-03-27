package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.bms.Constant;
import com.bms.DictConstant;
import com.bms.ErrorCodes;
import com.bms.entity.*;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.PractitionerService;
import com.bms.industry.service.VehicleService;
import com.bms.sys.service.DictService;
import com.bms.sys.service.OrganizationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 违规信息管理-导入/导出 Excel模型.
 *
 * @author luojimeng
 * @date 2020/3/19
 */
@Data
@RequiredArgsConstructor
public class BusViolationExcelModel {
    @ExcelIgnore
    private final DictService dictService;
    @ExcelIgnore
    private final PractitionerService practitionerService;
    @ExcelIgnore
    private final VehicleService vehicleService;
    @ExcelIgnore
    private final BusRouteService busRouteService;
    @ExcelIgnore
    private final OrganizationService organizationService;


    /**
     * 司机姓名.
     */
    @ExcelIgnore
    private Practitioner practitioner;
    @ExcelProperty(value = "司机", index = 1)
    private String practitionerNameText;
    /**
     * 违规车辆信息.
     */
    @ExcelIgnore
    private Vehicle vehicle;
    @ExcelProperty(value = "车辆号牌", index = 0)
    private String vehicleLicNoText;
    /**
     * 车型(必填) 改为文本 20200320.
     * “车辆型号”改为“车型”，文本类型.
     */
    @ExcelProperty(value = "车辆型号", index = 3)
    private String vehType;
    /**
     * 线路.
     */
    @ExcelIgnore
    private BusRoute busRoute;
    @ExcelProperty(value = "线路", index = 2)
    private String busRouteNameText;
    /**
     * 所属企业.
     */
    @ExcelIgnore
    private Organization organization;
    @ExcelProperty(value = "公司名称", index = 4)
    private String orgNameText;

    /**
     * 违规类型-行为(字典表 VIOLATION_TYPE).
     */
    @ExcelIgnore
    private Integer type;
    @ExcelProperty(value = "违规行为", index = 6)
    private String typeText;
    /**
     * 发生时间.
     */
    @DateTimeFormat(value = Constant.DATE_FORMAT_YYYY_MM_DD)
    @ExcelProperty(value = "时间", index = 5)
    private Date time;
    /**
     * 状态(1:处理中 2:已处理).
     */
    @ExcelIgnore
    private Integer status;
    @ExcelProperty(value = "状态", index = 7)
    private String statusText;

    public Practitioner getPractitioner() {
        if (StringUtils.isBlank(practitionerNameText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "司机姓名不能为空");
        }
        Practitioner practitioner = practitionerService.findByName(practitionerNameText);
        if (practitioner == null) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR, "司机姓名[" + practitionerNameText + "]不存在");
        }
        return practitioner;
    }

    public String getPractitionerNameText() {
        if (practitioner == null) {
            return "";
        }
        return practitioner.getName();
    }

    public Vehicle getVehicle() {
        if (StringUtils.isBlank(vehicleLicNoText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "车牌号不能为空");
        }
        Vehicle vehicle = vehicleService.findByLicNo(vehicleLicNoText);
        if (vehicle == null) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_ERR, "车牌号[" + vehicleLicNoText + "]不存在");
        }
        return vehicle;
    }

    public String getVehicleLicNoText() {
        if (vehicle == null) {
            return "";
        }
        return vehicle.getLicNo();
    }

    public BusRoute getBusRoute() {
        if (StringUtils.isBlank(busRouteNameText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "线路不能为空");
        }
        List<BusRoute> list = busRouteService.findByName(busRouteNameText);
        if (list == null || list.isEmpty()) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "线路[" + busRouteNameText + "]不存在");
        }
        return list.get(0);
    }

    public String getBusRouteNameText() {
        if (busRoute == null) {
            return "";
        }
        return busRoute.getName();
    }

    public Organization getOrganization() {
        if (StringUtils.isBlank(orgNameText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "公司名称不能为空");
        }
        Organization organization = organizationService.findByName(orgNameText);
        if (organization == null) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "公司名称[" + orgNameText + "]不存在");
        }
        return organization;
    }

    public String getOrgNameText() {
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    public Integer getType() {
        if (StringUtils.isBlank(typeText)) {
            return null;
        }
        List<Dictionary> list = dictService.findByCode(DictConstant.VIOLATION_TYPE);
        AtomicReference<Integer> type = new AtomicReference<>();
        list.forEach(o -> {
            if (typeText.equals(o.getText())) {
                type.set(Integer.parseInt(o.getValue()));
            }
        });
        return type.get();
    }

    public String getTypeText() {
        if (type == null) {
            return "";
        }
        List<Dictionary> list = dictService.findByCode(DictConstant.VIOLATION_TYPE);
        AtomicReference<String> text = new AtomicReference<>("");
        list.forEach(o -> {
            if (type.intValue() == Integer.parseInt(o.getValue())) {
                text.set(o.getText());
            }
        });
        return text.get();
    }


    public Integer getStatus() {
        if (StringUtils.isBlank(statusText)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "状态不能为空");
        }
        if (StringUtils.equals(statusText, "待处理")) {
            return BusViolation.STATUS_PENDING;
        }
        if (StringUtils.equals(statusText, "已处理")) {
            return BusViolation.STATUS_PROCESSED;
        }
        throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "不存在的状态");
    }

    public String getStatusText() {
        if (status == null) {
            return "";
        }
        if (status == BusViolation.STATUS_PENDING) {
            return "待处理";
        }
        if (status == BusViolation.STATUS_PROCESSED) {
            return "已处理";
        }
        return status.toString();
    }
}
