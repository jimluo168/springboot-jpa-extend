package com.bms.monitor.controller;

import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应急预案管理.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/emergencypreplans")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("应急预案管理")
@Api(value = "应急预案管理", tags = "应急预案管理")
public class EmergencyPreplanController {

}
