package com.bms.monitor.controller;

import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应急响应处理.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/emergencyresponses")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("应急响应处理")
@Api(value = "应急响应处理", tags = "应急响应处理")
public class EmergencyResponseController {

}
