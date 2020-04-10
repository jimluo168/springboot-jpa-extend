package com.bms.monitor.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.MoEmergencyPreplan;
import com.bms.monitor.service.EmergencyPreplanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 应急预案管理.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/emergencypreplans")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("应急预案管理")
@Api(value = "应急预案管理", tags = "应急预案管理")
public class EmergencyPreplanController {
    private final EmergencyPreplanService emergencyPreplanService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("info_release_create")
    @PostMapping("")
    public Result<MoEmergencyPreplan> create(@RequestBody MoEmergencyPreplan moEmergencyPreplan) {
        emergencyPreplanService.insert(moEmergencyPreplan);
        return ok(moEmergencyPreplan);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("info_release_edit")
    @PutMapping("/{id}")
    public Result<MoEmergencyPreplan> edit(@PathVariable Long id, @RequestBody MoEmergencyPreplan moEmergencyPreplan) {
        emergencyPreplanService.updateById(id, moEmergencyPreplan);
        return ok(moEmergencyPreplan);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("info_release_list")
    @GetMapping("/list")
    public Result<PageList<MoEmergencyPreplan>> list(PageRequest pageRequest, MoEmergencyPreplan queryParams) throws IllegalAccessException {
        return ok(emergencyPreplanService.page(pageRequest, BeanMapper.toMap(queryParams)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("info_release_details")
    @GetMapping("/{id}")
    public Result<MoEmergencyPreplan> details(@PathVariable Long id) {
        return Result.ok(emergencyPreplanService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("info_release_delete")
    @DeleteMapping("/{id}")
    public Result<MoEmergencyPreplan> delete(@PathVariable Long id) {
        return ok(emergencyPreplanService.deleteById(id));
    }

    @ApiOperation("预案名称")
    @GetMapping("/name/list")
    public Result<PageList<MoEmergencyPreplan>> namePage(PageRequest pageRequest, MoEmergencyPreplan queryParams) throws IllegalAccessException {
        return ok(emergencyPreplanService.namePage(pageRequest, BeanMapper.toMap(queryParams)));
    }

}
