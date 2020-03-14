package com.bms.sys.controller;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Organization;
import com.bms.sys.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * 机构.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/sys/organizations")
@RequiredArgsConstructor
@RequiresAuthentication
public class OrganizationController {

    private final OrganizationService organizationService;

    @RequiresPermissions("organizations_create")
    @PostMapping("")
    public Result<Organization> create(@RequestBody Organization organization) {
        organizationService.insert(organization);
        return Result.ok(organization);
    }

    @RequiresPermissions("organizations_edit")
    @PutMapping("/{id}")
    public Result<Organization> edit(@PathVariable Long id, @RequestBody Organization organization) {
        organizationService.updateById(id, organization);
        return Result.ok(organization);
    }

    @RequiresPermissions("organizations_list")
    @GetMapping("/list")
    public Result<PageList<Organization>> list(PageRequest pageRequest,
                                               @RequestParam(defaultValue = "") String name,
                                               @RequestParam(defaultValue = "0") int level) {
        return ok(organizationService.page(pageRequest, name, level));
    }

    @RequiresPermissions("organizations_details")
    @GetMapping("/{id}")
    public Result<Organization> details(@PathVariable Long id) {
        return Result.ok(organizationService.findById(id));
    }

    @RequiresPermissions("organizations_delete")
    @DeleteMapping("/{id}")
    public Result<Organization> deleteById(@PathVariable Long id) {
        return Result.ok(organizationService.deleteById(id));
    }

    @RequiresPermissions("organizations_audit")
    @PostMapping("/{id}/status/{status}")
    public Result<Organization> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Organization organization) {
        organizationService.audit(id, status, organization.getReason());
        return Result.ok();
    }


}
