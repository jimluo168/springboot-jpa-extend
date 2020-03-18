package com.bms.industry.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusRoute;
import com.bms.industry.service.BusRouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 公交车队管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/industry/busteams")
@RequiredArgsConstructor
@RequiresAuthentication
//@OpLogModule("公交车队管理")
@Api("公交车队管理")
public class BusTeamController {
    private static final Logger logger = LoggerFactory.getLogger(BusTeamController.class);

    private final BusRouteService busRouteService;
    private final ObjectMapper objectMapper;

    @ApiOperation("查询")
//    @OpLog("查询")
//    @RequiresPermissions("bus_route_list")
    @GetMapping("/list")
    public Result<PageList<BusRoute>> list(PageRequest pageRequest, BusRoute busRoute) throws IllegalAccessException {
        return ok(busRouteService.page(pageRequest, BeanMapper.toMap(busRoute)));
    }

}