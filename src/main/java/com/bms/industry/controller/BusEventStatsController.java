package com.bms.industry.controller;

import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事件统计分析.
 *
 * @author luojimeng
 * @date 2020/3/22
 */
@RestController
@RequestMapping("/industry/buseventstats")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("事件统计分析")
@Api("事件统计分析")
public class BusEventStatsController {

}
