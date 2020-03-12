package com.bms.sys.controller;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.domain.Result;
import com.bms.entity.Organization;
import com.bms.sys.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        organizationService.insert(organization);
        return Result.ok(organization);
    }


}
