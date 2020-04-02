package com.bms.sys.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.OperationLog;
import com.bms.sys.service.OpLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.bms.common.domain.Result.ok;

/**
 * 操作日志管理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestController
@RequestMapping("/sys/oplogs")
@RequiredArgsConstructor
@RequiresAuthentication
@Api(value = "日志管理",tags = "日志管理")
public class OpLogController {

    private final OpLogService opLogService;

    @RequiresPermissions("oplog_list")
    @GetMapping("/list")
    @ApiOperation("查询")
    public Result<PageList<OperationLog>> list(PageRequest pageRequest, QueryParams params) throws IllegalAccessException {
        return ok(opLogService.page(pageRequest, BeanMapper.toMap(params)));
    }

    @Data
    private static class QueryParams extends OperationLog {
        private Date begin;
        private Date end;
    }
}
