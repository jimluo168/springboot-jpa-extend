package com.bms.industry.view;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.bms.ErrorCodes;
import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusRoute;
import com.bms.entity.BusTeam;
import com.bms.entity.Organization;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.BusTeamService;
import com.bms.sys.service.OrganizationService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 网上申报明细
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Data
public class DeclareItemExcelModel {
    @ExcelIgnore
    private BusTeamService busTeamService;
    @ExcelIgnore
    private BusRouteService busRouteService;
    @ExcelIgnore
    private OrganizationService organizationService;

    public DeclareItemExcelModel() {
        this(null, null, null);
    }

    public DeclareItemExcelModel(BusTeamService busTeamService, BusRouteService busRouteService, OrganizationService organizationService) {
        this.busTeamService = busTeamService;
        this.busRouteService = busRouteService;
        this.organizationService = organizationService;
    }

    /**
     * 所属申报
     */
    @ExcelIgnore
    private BusOnlineDataDeclare declare;


    @ExcelProperty(value = "序号", index = 0)
    private String num;

    /**
     * 所属企业.
     */
    @ExcelIgnore
    private Organization organization;
    @ColumnWidth(50)
    @ExcelProperty(value = "单位名称", index = 1)
    private String orgName;

    /**
     * 所属车队.
     */
    @ExcelIgnore
    private BusTeam carTeam;
    @ExcelProperty(value = "车队名称", index = 2)
    private String teamName;

    /**
     * 所属路线.
     */
    @ExcelIgnore
    private BusRoute busRoute;
    @ExcelProperty(value = "线路", index = 3)
    private String routeName;

    /**
     * 车辆编号.
     */
    @ExcelProperty(value = "车辆编号", index = 4)
    private String vehCode;

    /**
     * 汽油数量.
     */
    @ExcelProperty(value = "汽油数量", index = 5)
    private BigDecimal gasQuantity;

    /**
     * 汽油 单价.
     */
    @ExcelProperty(value = "汽油单价", index = 6)
    private BigDecimal gasPrice;

    /**
     * 汽油 金额.
     */
    @ExcelProperty(value = "汽油金额", index = 7)
    private BigDecimal gasBalance;

    /**
     * 柴油 数量.
     */
    @ExcelProperty(value = "柴油数量", index = 8)
    private BigDecimal dieselOilQuantity;

    /**
     * 柴油 单价.
     */
    @ExcelProperty(value = "柴油单价", index = 9)
    private BigDecimal dieselOilPrice;

    /**
     * 柴油 金额.
     */
    @ExcelProperty(value = "柴油金额", index = 10)
    private BigDecimal dieselOilBalance;

    /**
     * 天然气 数量.
     */
    @ExcelProperty(value = "天然气数量", index = 11)
    private BigDecimal naturalGasQuantity;

    /**
     * 天然气 单价.
     */
    @ExcelProperty(value = "天然气单价", index = 12)
    private BigDecimal naturalGasPrice;

    /**
     * 天然气 金额.
     */
    @ExcelProperty(value = "天然气金额", index = 13)
    private BigDecimal naturalGasBalance;

    /**
     * 电能 数量.
     */
    @ExcelProperty(value = "电能数量", index = 14)
    private BigDecimal electricQuantity;

    /**
     * 电能 单价.
     */
    @ExcelProperty(value = "电能单价", index = 15)
    private BigDecimal electricPrice;

    /**
     * 电能 金额.
     */
    @ExcelProperty(value = "电能金额", index = 16)
    private BigDecimal electricBalance;

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
            return this.orgName;
        }
        return organization.getName();
    }

    public BusRoute getBusRoute() {
        if (StringUtils.isBlank(routeName)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "线路名称不能为空");
        }
        List<BusRoute> routes = busRouteService.findByName(routeName);
        if (routes.size() < 1) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "线路名称不存在");
        }
        return routes.get(0);
    }

    public String getRouteName() {
        if (busRoute == null) {
            return this.routeName;
        }
        return busRoute.getName();
    }

    public BusTeam getCarTeam() {
        if (StringUtils.isBlank(teamName)) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "车队名称不能为空");
        }
        List<BusTeam> teams = busTeamService.findByName(teamName);
        if (teams.size() < 1) {
            throw ErrorCodes.build(ErrorCodes.IMPORT_DATA_FORMAT_ERR, "车队名称不存在");
        }
        return teams.get(0);
    }

    public String getTeamName() {
        if (carTeam == null) {
            return this.teamName;
        }
        return carTeam.getName();
    }

}
