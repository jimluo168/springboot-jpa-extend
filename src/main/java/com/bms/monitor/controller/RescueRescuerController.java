package com.bms.monitor.controller;

import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.MoRescueRescuer;
import com.bms.monitor.service.RescueRescuerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 救援资源管理-人员.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/rescuerescuers")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("救援资源管理-人员")
@Api(value = "救援资源管理-人员", tags = "救援资源管理-人员")
public class RescueRescuerController {
    private static final Logger logger = LoggerFactory.getLogger(RescueRescuerController.class);

    private final RescueRescuerService rescueRescuerService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("rescue_rescuer_create")
    @PostMapping("")
    public Result<MoRescueRescuer> create(@RequestBody MoRescueRescuer moRescueRescuer) {
        if (rescueRescuerService.existsByName(moRescueRescuer.getName(), null)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "姓名已存在", true);
        }
        return ok(rescueRescuerService.insert(moRescueRescuer));
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("rescue_rescuer_edit")
    @PutMapping("/{id}")
    public Result<MoRescueRescuer> edit(@PathVariable Long id, @RequestBody MoRescueRescuer moRescueRescuer) {
        if (rescueRescuerService.existsByName(moRescueRescuer.getName(), id)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "姓名已存在", true);
        }
        return ok(rescueRescuerService.updateById(id, moRescueRescuer));
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("rescue_rescuer_list")
    @GetMapping("/list")
    public Result<PageList<MoRescueRescuer>> list(PageRequest pageRequest, MoRescueRescuer moRescueRescuer) throws IllegalAccessException {
        return ok(rescueRescuerService.page(pageRequest, BeanMapper.toMap(moRescueRescuer)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("rescue_rescuer_details")
    @GetMapping("/{id}")
    public Result<MoRescueRescuer> details(@PathVariable Long id) {
        return Result.ok(rescueRescuerService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("rescue_rescuer_delete")
    @DeleteMapping("/{id}")
    public Result<MoRescueRescuer> delete(@PathVariable Long id) {
        return ok(rescueRescuerService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("rescue_rescuer_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<MoRescueRescuer> audit(@PathVariable Long id, @PathVariable int status, @RequestBody MoRescueRescuer moRescueRescuer) {
        rescueRescuerService.audit(id, status, moRescueRescuer.getReason());
        return ok();
    }

    @ApiOperation("公司")
    @RequiresPermissions("rescue_rescuer_list")
    @GetMapping("/company/list")
    public Result<PageList<MoRescueRescuer>> companyPage(PageRequest pageRequest, MoRescueRescuer moRescueRescuer) throws IllegalAccessException {
        return ok(rescueRescuerService.companyPage(pageRequest, BeanMapper.toMap(moRescueRescuer)));
    }

    @ApiOperation("职位")
    @RequiresPermissions("rescue_rescuer_list")
    @GetMapping("/position/list")
    public Result<PageList<MoRescueRescuer>> positionPage(PageRequest pageRequest, MoRescueRescuer moRescueRescuer) throws IllegalAccessException {
        return ok(rescueRescuerService.positionPage(pageRequest, BeanMapper.toMap(moRescueRescuer)));
    }

}
