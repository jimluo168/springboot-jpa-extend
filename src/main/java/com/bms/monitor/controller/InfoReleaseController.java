package com.bms.monitor.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.MoInfoRelease;
import com.bms.monitor.service.InfoReleaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.bms.common.domain.Result.ok;

/**
 * 应急信息发布
 *
 * @author zouyongcan
 * @date 2020/4/8
 */
@RestController
@RequestMapping("/monitor/inforeleases")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("应急信息发布")
@Api(value = "应急信息发布", tags = "应急信息发布")
public class InfoReleaseController {
    private final InfoReleaseService infoReleaseService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("info_release_create")
    @PostMapping("")
    public Result<MoInfoRelease> create(@RequestBody MoInfoRelease moInfoRelease) {
        infoReleaseService.insert(moInfoRelease);
        return ok(moInfoRelease);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("info_release_edit")
    @PutMapping("/{id}")
    public Result<MoInfoRelease> edit(@PathVariable Long id, @RequestBody MoInfoRelease moInfoRelease) {
        infoReleaseService.updateById(id, moInfoRelease);
        return ok(moInfoRelease);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("info_release_list")
    @GetMapping("/list")
    public Result<PageList<MoInfoRelease>> list(PageRequest pageRequest, MoInfoRelease queryParams) throws IllegalAccessException {
        return ok(infoReleaseService.page(pageRequest, BeanMapper.toMap(queryParams)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("info_release_details")
    @GetMapping("/{id}")
    public Result<MoInfoRelease> details(@PathVariable Long id) {
        return Result.ok(infoReleaseService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("info_release_delete")
    @DeleteMapping("/{id}")
    public Result<MoInfoRelease> delete(@PathVariable Long id) {
        return ok(infoReleaseService.deleteById(id));
    }
}
