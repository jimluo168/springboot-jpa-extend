package com.bms.monitor.controller;

import com.bms.common.domain.Result;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class MoVehicleController {

    @ApiOperation("车辆-左侧菜单")
    @GetMapping("/menus/all")
    public Result<Void> menuAll() {

    }
}
