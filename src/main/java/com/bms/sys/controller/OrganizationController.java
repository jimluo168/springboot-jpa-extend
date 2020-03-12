package com.bms.sys.controller;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.domain.PageList;
import com.bms.common.domain.Result;
import com.bms.entity.Organization;
import com.bms.entity.User;
import com.bms.sys.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping("")
    public Result<Organization> create(@RequestBody Organization organization) {
        organizationService.insert(organization);
        return Result.ok(organization);
    }

    @PutMapping("/{id}")
    public Result<Organization> edit(@PathVariable Long id, @RequestBody Organization organization) {
        organizationService.updateById(id, organization);
        return Result.ok(organization);
    }

    @GetMapping("/list")
    public Result<PageList<Organization>> list(Pageable pageable,
                                               String name,
                                               @RequestParam(defaultValue = "0") int level) {
        return ok(organizationService.page(pageable, name, level));
    }

    @GetMapping("/{id}")
    public Result<Organization> details(@PathVariable Long id) {
        return Result.ok(organizationService.findById(id));
    }

    @DeleteMapping("/{id}")
    public Result<Organization> deleteById(@PathVariable Long id) {
        return Result.ok(organizationService.deleteById(id));
    }

    @PostMapping("/{id}/status/{status}")
    public Result<Organization> audit(@PathVariable Long id, @PathVariable int status, @RequestBody Organization organization) {
        organizationService.audit(id, status, organization.getReason());
        return Result.ok();
    }


}
