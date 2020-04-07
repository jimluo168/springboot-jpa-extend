package com.bms.monitor.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Expert;
import com.bms.monitor.service.ExpertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.bms.common.domain.Result.ok;

/**
 * TODO(类的简要说明)
 *
 * @author zouyongcan
 * @date 2020/4/7
 */
@RestController
@RequestMapping("/monitor/experts")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("专家库管理")
@Api(value = "专家库管理", tags = "专家库管理")
public class ExpertController {
    private final ExpertService expertService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("kbes_expert_create")
    @PostMapping("")
    public Result<Expert> create(@RequestBody Expert expert) {
        expertService.insert(expert);
        return ok(expert);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("kbes_expert_edit")
    @PutMapping("/{id}")
    public Result<Expert> edit(@PathVariable Long id, @RequestBody Expert expert) {
        expertService.updateById(id, expert);
        return ok(expert);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("kbes_expert_list")
    @GetMapping("/list")
    public Result<PageList<Expert>> list(PageRequest pageRequest, Expert queryParams) throws IllegalAccessException {
        return ok(expertService.page(pageRequest, BeanMapper.toMap(queryParams)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("kbes_expert_details")
    @GetMapping("/{id}")
    public Result<Expert> details(@PathVariable Long id) {
        return Result.ok(expertService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("kbes_expert_delete")
    @DeleteMapping("/{id}")
    public Result<Expert> delete(@PathVariable Long id) {
        return ok(expertService.deleteById(id));
    }
}
