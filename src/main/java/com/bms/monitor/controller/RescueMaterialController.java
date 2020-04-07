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
import com.bms.entity.MoRescueMaterial;
import com.bms.monitor.service.RescueMaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 救援资源管理-物资.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@RestController
@RequestMapping("/monitor/rescuematerials")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("救援资源管理-物资")
@Api(value = "救援资源管理-物资", tags = "救援资源管理-物资")
public class RescueMaterialController {
    private static final Logger logger = LoggerFactory.getLogger(RescueVehicleController.class);

    private final RescueMaterialService rescueMaterialService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("rescue_material_create")
    @PostMapping("")
    public Result<MoRescueMaterial> create(@RequestBody MoRescueMaterial moRescueMaterial) {
        if (rescueMaterialService.existsByCode(moRescueMaterial.getCode(), null)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "车辆编号已存在", true);
        }
        return ok(rescueMaterialService.insert(moRescueMaterial));
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("rescue_material_edit")
    @PutMapping("/{id}")
    public Result<MoRescueMaterial> edit(@PathVariable Long id, @RequestBody MoRescueMaterial moRescueMaterial) {
        if (rescueMaterialService.existsByCode(moRescueMaterial.getCode(), id)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "车辆编号已存在", true);
        }
        return ok(rescueMaterialService.updateById(id, moRescueMaterial));
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("rescue_material_list")
    @GetMapping("/list")
    public Result<PageList<MoRescueMaterial>> list(PageRequest pageRequest, MoRescueMaterial moRescueMaterial) throws IllegalAccessException {
        return ok(rescueMaterialService.page(pageRequest, BeanMapper.toMap(moRescueMaterial)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("rescue_material_details")
    @GetMapping("/{id}")
    public Result<MoRescueMaterial> details(@PathVariable Long id) {
        return Result.ok(rescueMaterialService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("rescue_material_delete")
    @DeleteMapping("/{id}")
    public Result<MoRescueMaterial> delete(@PathVariable Long id) {
        return ok(rescueMaterialService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("rescue_material_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<MoRescueMaterial> audit(@PathVariable Long id, @PathVariable int status, @RequestBody MoRescueMaterial moRescueMaterial) {
        rescueMaterialService.audit(id, status, moRescueMaterial.getReason());
        return ok();
    }

    @ApiOperation("报废")
    @OpLog("报废")
    @RequiresPermissions("rescue_material_scrap")
    @PostMapping("/{id}/scraps")
    public Result<MoRescueMaterial> scrap(@PathVariable Long id) {
        rescueMaterialService.scrap(id);
        return ok();
    }

    @ApiOperation("公司")
    @RequiresPermissions("rescue_material_list")
    @GetMapping("/orgname/list")
    public Result<PageList<MoRescueMaterial>> orgnamePage(PageRequest pageRequest, MoRescueMaterial moRescueMaterial) throws IllegalAccessException {
        return ok(rescueMaterialService.orgnamePage(pageRequest, BeanMapper.toMap(moRescueMaterial)));
    }

    @ApiOperation("物资编号")
    @RequiresPermissions("rescue_material_list")
    @GetMapping("/code/list")
    public Result<PageList<MoRescueMaterial>> codePage(PageRequest pageRequest, MoRescueMaterial moRescueMaterial) throws IllegalAccessException {
        return ok(rescueMaterialService.codePage(pageRequest, BeanMapper.toMap(moRescueMaterial)));
    }

    @ApiOperation("物资类型")
    @RequiresPermissions("rescue_material_list")
    @GetMapping("/type/list")
    public Result<PageList<MoRescueMaterial>> typePage(PageRequest pageRequest, MoRescueMaterial moRescueMaterial) throws IllegalAccessException {
        return ok(rescueMaterialService.typePage(pageRequest, BeanMapper.toMap(moRescueMaterial)));
    }

    @ApiOperation("物资来源")
    @RequiresPermissions("rescue_material_list")
    @GetMapping("/origin/list")
    public Result<PageList<MoRescueMaterial>> originPage(PageRequest pageRequest, MoRescueMaterial moRescueMaterial) throws IllegalAccessException {
        return ok(rescueMaterialService.originPage(pageRequest, BeanMapper.toMap(moRescueMaterial)));
    }


}
