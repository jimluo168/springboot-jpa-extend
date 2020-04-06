package com.bms.monitor.controller;

import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 救援资源管理-物资.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/rescuematerials")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("救援资源管理-物资")
@Api(value = "救援资源管理-物资", tags = "救援资源管理-物资")
public class RescueMaterialController {

}
