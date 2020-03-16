package com.bms.sys.controller;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Organization;
import com.bms.sys.service.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 公交企业管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/sys/organizations")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("公交企业管理")
@Api("公交企业管理")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final ObjectMapper objectMapper;

    @ApiOperation("新增")
    @OpLog("新增")
    @RequiresPermissions("organization_create")
    @PostMapping("")
    public Result<Organization> create(@RequestBody Organization organization) {
        organizationService.insert(organization);
        return ok(organization);
    }

    @ApiOperation("编辑")
    @OpLog("编辑")
    @RequiresPermissions("organization_edit")
    @PutMapping("/{id}")
    public Result<Organization> edit(@PathVariable Long id, @RequestBody Organization organization) {
        organizationService.updateById(id, organization);
        return ok(organization);
    }

    @ApiOperation("查询")
    @OpLog("查询")
    @RequiresPermissions("organization_list")
    @GetMapping("/list")
    public Result<PageList<Organization>> list(PageRequest pageRequest, Organization organization) throws IllegalAccessException {
        return ok(organizationService.page(pageRequest, BeanMapper.toMap(organization)));
    }

    @ApiOperation("详情")
    @OpLog("详情")
    @RequiresPermissions("organization_details")
    @GetMapping("/{id}")
    public Result<Organization> details(@PathVariable Long id) {
        return Result.ok(organizationService.findById(id));
    }

    @ApiOperation("删除")
    @OpLog("删除")
    @RequiresPermissions("organization_delete")
    @DeleteMapping("/{id}")
    public Result<Organization> delete(@PathVariable Long id) {
        return ok(organizationService.deleteById(id));
    }

    @ApiOperation("审核")
    @OpLog("审核")
    @RequiresPermissions("organization_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Organization> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Organization organization) {
        organizationService.audit(id, status, organization.getReason());
        return ok();
    }
}
