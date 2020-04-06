package com.bms.monitor.controller;

import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
