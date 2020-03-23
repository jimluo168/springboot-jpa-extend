package com.bms.industry.controller;

import com.bms.common.domain.Result;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.industry.service.BusViolationStatsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequiresPermissions("bus_violation_stats")
    @GetMapping("/companies")
    public Result<Void> company() {
        return ok();
    }

    /**
     * 违规类型.
     */
    @RequiresPermissions("bus_violation_stats")
    @GetMapping("/types")
    public Result<Void> type() {
        return ok();
    }

    /**
     * 司机违规排行.
     */
    @RequiresPermissions("bus_violation_stats")
    @GetMapping("/drivers")
    public Result<Void> driver() {
        return ok();
    }

    /**
     * 全部违规行为统计(周、月、年)-周.
     */
    @RequiresPermissions("bus_violation_stats")
    @GetMapping("/weeks")
    public Result<Void> week() {
        return ok();
    }

    /**
     * 全部违规行为统计(周、月、年)-月.
     */
    @RequiresPermissions("bus_violation_stats")
    @GetMapping("/months")
    public Result<Void> moth() {
        return ok();
    }

    /**
     * 全部违规行为统计(周、月、年)-年.
     */
    @RequiresPermissions("bus_violation_stats")
    @GetMapping("/years")
    public Result<Void> year() {
        return ok();
    }
}
