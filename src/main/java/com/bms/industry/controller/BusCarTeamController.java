package com.bms.industry.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.util.ResponseUtils;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.BusRoute;
import com.bms.industry.service.BusRouteService;
import com.bms.sys.view.OrganizationExcelModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 公交车队管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/industry/buscarteams")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交车队管理")
@Api("公交车队管理")
public class BusCarTeamController {
    private static final Logger logger = LoggerFactory.getLogger(BusCarTeamController.class);

    private final BusRouteService busRouteService;
    private final ObjectMapper objectMapper;

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("bus_route_list")
    @GetMapping("/list")
    public Result<PageList<BusRoute>> list(PageRequest pageRequest, BusRoute busRoute) throws IllegalAccessException {
        return ok(busRouteService.page(pageRequest, BeanMapper.toMap(busRoute)));
    }

}
