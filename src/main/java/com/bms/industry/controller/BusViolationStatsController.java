package com.bms.industry.controller;

import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.industry.service.BusViolationStatsService;
import com.bms.industry.view.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
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
    public Result<List<BusViolationStatsCompany>> company(BusViolationStatsCompany params) throws IllegalAccessException {
        return ok(busViolationStatsService.company(BeanMapper.toMap(params)));
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
    public Result<List<BusViolationStatsDriver>> driver(BusViolationStatsDriver params) throws IllegalAccessException {
        return ok(busViolationStatsService.driver(BeanMapper.toMap(params)));
    }

    /**
     * 全部违规行为统计(周、月、年)-周.
     */
    @ApiOperation("全部违规行为统计(周、月、年)-周")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/weeks")
    public Result<BusViolationStatsEchartView> week() throws IllegalAccessException {
        BusViolationStatsWeek params = new BusViolationStatsWeek();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        params.setBegin(calendar.getTime());
        params.setEnd(now);
        return ok(busViolationStatsService.week(BeanMapper.toMap(params)));
    }

    /**
     * 全部违规行为统计(周、月、年)-月.
     */
    @ApiOperation("全部违规行为统计(周、月、年)-月")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/months")
    public Result<BusViolationStatsEchartView> moth() throws IllegalAccessException {
        BusViolationStatsWeek params = new BusViolationStatsWeek();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -1);
        params.setBegin(calendar.getTime());
        params.setEnd(now);
        return ok(busViolationStatsService.month(BeanMapper.toMap(params)));
    }

    /**
     * 全部违规行为统计(周、月、年)-年.
     */
    @ApiOperation("全部违规行为统计(周、月、年)-年")
    @RequiresPermissions("bus_violation_stats_list")
    @GetMapping("/years")
    public Result<BusViolationStatsEchartView> year() throws IllegalAccessException {
        BusViolationStatsWeek params = new BusViolationStatsWeek();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -11);
        calendar.setTime(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        params.setBegin(calendar.getTime());
        params.setEnd(now);
        return ok(busViolationStatsService.year(BeanMapper.toMap(params)));
    }
}
