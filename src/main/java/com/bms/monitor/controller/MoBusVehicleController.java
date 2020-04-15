package com.bms.monitor.controller;

import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.monitor.service.MoBusVehicleService;
import com.bms.monitor.sync.DataForwardService;
import com.bms.monitor.sync.MoDataForwardCache;
import com.bms.monitor.view.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 车辆运行监控.
 *
 * @author luojimeng
 * @date 2020/4/14
 */
@RestController
@RequestMapping("/monitor/vehilces")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("车辆运行监控")
@Api(value = "车辆运行监控", tags = "车辆运行监控")
@RequiresPermissions("monitor_bus_vehilce_list")
public class MoBusVehicleController {

    private final MoBusVehicleService moBusVehicleService;
    private final DataForwardService dataForwardService;

    @ApiOperation("车辆-左侧菜单")
    @GetMapping("/menus/all")
    public Result<List<MoBusVehicleTreeView>> menuAll() {
        return ok(moBusVehicleService.findByVehicleMenuAll());
    }

    @ApiOperation("车辆-列表")
    @GetMapping("/list")
    public Result<PageList<MoBusVehicleView>> vehicleList(PageRequest pageRequest, MoBusVehicleView view) {
        if (view.getRouteIdList() == null || view.getRouteIdList().isEmpty()) {
            throw ErrorCodes.build(ErrorCodes.ILLEGAL_ARGUMENT, "route_id_list不能为空", true);
        }
        return ok(moBusVehicleService.pageVehicleListByRouteIdList(pageRequest, view));
    }

    @ApiOperation("车辆-定位")
    @GetMapping("/{vehCode}/gps")
    public Result<MoDataForwardCache> gps(@PathVariable String vehCode) {
        MoDataForwardCache cache = dataForwardService.getMoDataForwardCacheByVehCode(vehCode);
        if (cache == null) {
            throw ErrorCodes.build(ErrorCodes.GPS_NO_LOCATION_DATA_ERR);
        }
        return ok(cache);
    }


    @ApiOperation("车辆-历史轨迹")
    @GetMapping("/{vehCode}/tracks/list")
    public Result<PageList<MoBusVehicleHistoryTrackView>> trackList(@PathVariable String vehCode, PageRequest pageRequest, MoBusVehicleHistoryTrackView view) {
        if (view.getBegin() == null || view.getEnd() == null) {
            throw ErrorCodes.build(ErrorCodes.ILLEGAL_ARGUMENT, "begin end参数不能缺少");
        }
        return ok(moBusVehicleService.pageVehicleTrackListByVehCode(vehCode, pageRequest, view));
    }

    @ApiOperation("车辆-线路-列表")
    @GetMapping("/routes/list")
    public Result<PageList<MoBusRouteView>> routeList(PageRequest pageRequest, MoBusRouteView view) {
        if (view.getRouteIdList() == null || view.getRouteIdList().isEmpty()) {
            throw ErrorCodes.build(ErrorCodes.ILLEGAL_ARGUMENT, "route_id_list不能为空", true);
        }
        return ok(moBusVehicleService.pageRouteListByRouteIdList(pageRequest, view));
    }

    @ApiOperation("车辆-线路-站点列表")
    @GetMapping("/sites/list")
    public Result<PageList<MoBusSiteView>> siteList(PageRequest pageRequest, MoBusSiteView view) {
        if (view.getRouteIdList() == null || view.getRouteIdList().isEmpty()) {
            throw ErrorCodes.build(ErrorCodes.ILLEGAL_ARGUMENT, "route_id_list不能为空", true);
        }
        return ok(moBusVehicleService.pageSiteListByRouteIdList(pageRequest, view));
    }
}
