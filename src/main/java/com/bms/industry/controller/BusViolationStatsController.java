package com.bms.industry.controller;

import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.industry.service.BusViolationStatsService;
import com.bms.industry.view.BusViolationStatsType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 事件统计分析.
 *
 * @author luojimeng
 * @date 2020/3/22
 */
@RestController
@RequestMapping("/industry/busviolationstats")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("事件统计分析")
@Api("事件统计分析")
public class BusViolationStatsController {

    private final BusViolationStatsService busViolationStatsService;

    /**
     * 公司违规事件排行.
     */
    @ApiOperation("公司违规事件排行")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/companies")
    public Result<Void> company() {
        return ok();
    }

    /**
     * 违规类型.
     */
    @ApiOperation("违规类型")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/types")
    public Result<List<BusViolationStatsType>> type(BusViolationStatsType params) throws IllegalAccessException {
        return ok(busViolationStatsService.type(BeanMapper.toMap(params)));
    }

    /**
     * 司机违规排行.
     */
    @ApiOperation("司机违规排行")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/drivers")
    public Result<Void> driver() {
        return ok();
    }

    /**
     * 全部违规行为统计(周、月、年)-周.
     */
    @ApiOperation("全部违规行为统计(周、月、年)-周")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/weeks")
    public Result<Void> week() {
        return ok();
    }

    /**
     * 全部违规行为统计(周、月、年)-月.
     */
    @ApiOperation("全部违规行为统计(周、月、年)-月")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/months")
    public Result<Void> moth() {
        return ok();
    }

    /**
     * 全部违规行为统计(周、月、年)-年.
     */
    @ApiOperation("全部违规行为统计(周、月、年)-年")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/years")
    public Result<Void> year() {
        return ok();
    }
}
