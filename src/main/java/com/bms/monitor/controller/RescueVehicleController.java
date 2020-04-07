package com.bms.monitor.controller;

import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.MoRescueVehicle;
import com.bms.monitor.service.RescueVehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 救援资源管理-车辆.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/rescuevehicles")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("救援资源管理-车辆")
@Api(value = "救援资源管理-车辆", tags = "救援资源管理-车辆")
public class RescueVehicleController {
    private static final Logger logger = LoggerFactory.getLogger(RescueVehicleController.class);

    private final RescueVehicleService rescueVehicleService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("rescue_vehicle_create")
    @PostMapping("")
    public Result<MoRescueVehicle> create(@RequestBody MoRescueVehicle moRescueVehicle) {
        if (rescueVehicleService.existsByCode(moRescueVehicle.getCode(), null)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "车辆编号已存在", true);
        }
        return ok(rescueVehicleService.insert(moRescueVehicle));
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("rescue_vehicle_edit")
    @PutMapping("/{id}")
    public Result<MoRescueVehicle> edit(@PathVariable Long id, @RequestBody MoRescueVehicle moRescueVehicle) {
        if (rescueVehicleService.existsByCode(moRescueVehicle.getCode(), id)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "车辆编号已存在", true);
        }
        return ok(rescueVehicleService.updateById(id, moRescueVehicle));
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("rescue_vehicle_list")
    @GetMapping("/list")
    public Result<PageList<MoRescueVehicle>> list(PageRequest pageRequest, MoRescueVehicle moRescueVehicle) throws IllegalAccessException {
        return ok(rescueVehicleService.page(pageRequest, BeanMapper.toMap(moRescueVehicle)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("rescue_vehicle_details")
    @GetMapping("/{id}")
    public Result<MoRescueVehicle> details(@PathVariable Long id) {
        return Result.ok(rescueVehicleService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("rescue_vehicle_delete")
    @DeleteMapping("/{id}")
    public Result<MoRescueVehicle> delete(@PathVariable Long id) {
        return ok(rescueVehicleService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("rescue_vehicle_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<MoRescueVehicle> audit(@PathVariable Long id, @PathVariable int status, @RequestBody MoRescueVehicle moRescueVehicle) {
        rescueVehicleService.audit(id, status, moRescueVehicle.getReason());
        return ok();
    }

    @ApiOperation("公司")
    @RequiresPermissions("rescue_vehicle_list")
    @GetMapping("/company/list")
    public Result<PageList<MoRescueVehicle>> companyPage(PageRequest pageRequest, MoRescueVehicle moRescueVehicle) throws IllegalAccessException {
        return ok(rescueVehicleService.companyPage(pageRequest, BeanMapper.toMap(moRescueVehicle)));
    }

    @ApiOperation("线路")
    @RequiresPermissions("rescue_vehicle_list")
    @GetMapping("/routename/list")
    public Result<PageList<MoRescueVehicle>> routeNamePage(PageRequest pageRequest, MoRescueVehicle moRescueVehicle) throws IllegalAccessException {
        return ok(rescueVehicleService.routeNamePage(pageRequest, BeanMapper.toMap(moRescueVehicle)));
    }
    
}
