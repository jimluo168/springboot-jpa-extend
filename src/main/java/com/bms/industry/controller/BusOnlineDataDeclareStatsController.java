package com.bms.industry.controller;

import com.bms.ErrorCodes;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusOnlineDataDeclareItem;
import com.bms.industry.service.BusOnlineDataDeclareStatsService;
import com.bms.industry.view.BusOnlineDataDeclareStatsEnergyComparisonEchartView;
import com.bms.industry.view.DataDeclareTotalRetrieval;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

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
    public Result<BusOnlineDataDeclareStatsEnergyComparisonEchartView> energycomparison(BusOnlineDataDeclareItem params) throws IllegalAccessException {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        switch (params.getCategory()) {
            case 1:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.MONTH, -11);
                calendar.setTime(calendar.getTime());
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                params.setBegin(calendar.getTime());
                params.setEnd(now);
                return ok(busOnlineDataDeclareStatsService.year(params));
            case 2:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.DATE, -90);
                calendar.setTime(calendar.getTime());
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                params.setBegin(calendar.getTime());
                params.setEnd(now);
                return ok(busOnlineDataDeclareStatsService.quarter(params));
            case 3:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.MONTH, -1);
                calendar.setTime(calendar.getTime());
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                params.setBegin(calendar.getTime());
                params.setEnd(now);
                return ok(busOnlineDataDeclareStatsService.month(params));
            case 4:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.DATE, -7);
                calendar.setTime(calendar.getTime());
                params.setEnd(now);
                return ok(busOnlineDataDeclareStatsService.week(params));
        }

        throw ErrorCodes.build(ErrorCodes.INVALID_PARAMETER, " category=" + params.getCategory() + " 不支持的类别");
    }

    @ApiOperation("统计查询")
    @RequiresPermissions("query_statis_list")
    @GetMapping("/querystatis")
    public Result<DataDeclareTotalRetrieval> queryStatis(DataDeclareTotalRetrieval data) throws IllegalAccessException {
        return ok(busOnlineDataDeclareStatsService.queryStatis(BeanMapper.toMap(data)));
    }

}
