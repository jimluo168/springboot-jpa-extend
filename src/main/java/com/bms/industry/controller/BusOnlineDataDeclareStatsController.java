package com.bms.industry.controller;

import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareItem;
import com.bms.industry.service.BusOnlineDataDeclareStatsService;
import com.bms.industry.view.DataDeclareTotalRetrieval;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bms.common.domain.Result.ok;

/**
 * 统计数据表.
 *
 * @author luojimeng
 * @date 2020/3/30
 */
@RestController
@RequestMapping("/industry/onlinedatadeclares/stats")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("统计数据表")
@Api("统计数据表")
public class BusOnlineDataDeclareStatsController {

    private final BusOnlineDataDeclareStatsService busOnlineDataDeclareStatsService;

    @ApiOperation("总排炭量统计")
    @RequiresPermissions("statis_data_list")
    @GetMapping("/carbons")
    public Result<BusOnlineDataDeclareItem> carbon() {
        return ok(busOnlineDataDeclareStatsService.carbon());
    }

    @ApiOperation("节能减排数据统计")
    @RequiresPermissions("statis_data_list")
    @GetMapping("/cutemissions")
    public Result<BusOnlineDataDeclareItem> cutEmissions(BusOnlineDataDeclareItem item) throws IllegalAccessException {
        return ok(busOnlineDataDeclareStatsService.cutEmissions(BeanMapper.toMap(item)));
    }

    @ApiOperation("能源趋势对比")
    @RequiresPermissions("statis_data_list")
    @GetMapping("/energycomparisons")
    public Result<BusOnlineDataDeclareItem> energycomparisonsYear(BusOnlineDataDeclareItem item) throws IllegalAccessException {
        return ok(busOnlineDataDeclareStatsService.cutEmissions(BeanMapper.toMap(item)));
    }

    @ApiOperation("统计查询")
    @RequiresPermissions("query_statis_list")
    @GetMapping("/querystatis")
    public Result<DataDeclareTotalRetrieval> queryStatis(DataDeclareTotalRetrieval data) throws IllegalAccessException {
        return ok(busOnlineDataDeclareStatsService.queryStatis(BeanMapper.toMap(data)));
    }

}
