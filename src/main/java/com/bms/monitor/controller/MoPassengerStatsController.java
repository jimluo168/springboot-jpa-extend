package com.bms.monitor.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.monitor.service.MoPassengerStatsService;
import com.bms.monitor.view.MoBusVehicleView;
import com.bms.monitor.view.MoPassengerListView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 客流量动态.
 *
 * @author luojimeng
 * @date 2020/4/16
 */
@RestController
@RequestMapping("/monitor/passengers")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("客流量动态")
@Api(value = "客流量动态", tags = "客流量动态")
@RequiresPermissions("monitor_passenger_list")
public class MoPassengerStatsController {

    private final MoPassengerStatsService moPassengerStatsService;

    @ApiOperation("列表")
    @GetMapping("/list")
    public Result<PageList<MoPassengerListView>> list(PageRequest pageRequest, MoPassengerListView view) {
        return ok(moPassengerStatsService.pageList(pageRequest, view));
    }

    @ApiOperation("站点客流量排名")
    @GetMapping("/tops")
    public Result<PageList<MoPassengerListView>> top(PageRequest pageRequest, MoPassengerListView view) {
        return ok(moPassengerStatsService.pageTop(pageRequest, view));
    }

    @ApiOperation("线路总客流量")
    @GetMapping("/all")
    public Result<List<MoPassengerListView>> all(MoPassengerListView view) {
        return ok(moPassengerStatsService.all(view));
    }

}
