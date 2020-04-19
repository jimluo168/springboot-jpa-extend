package com.springboot.jpa.demo.sys.controller;

import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.domain.Result;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import com.springboot.jpa.demo.core.web.annotation.RequiresPermissions;
import com.springboot.jpa.demo.entity.OperationLog;
import com.springboot.jpa.demo.sys.service.OpLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springboot.jpa.demo.core.domain.Result.ok;

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
@Api(value = "日志管理", tags = "日志管理")
public class OpLogController {

    private final OpLogService opLogService;

    @RequiresPermissions("oplog_list")
    @GetMapping("/list")
    @ApiOperation("查询")
    public Result<PageList<OperationLog>> list(PageRequest pageRequest, OperationLog operationLog) throws IllegalAccessException {
        return ok(opLogService.page(pageRequest, operationLog));
    }
}
