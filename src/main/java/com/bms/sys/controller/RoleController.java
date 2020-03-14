package com.bms.sys.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Role;
import com.bms.sys.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bms.common.domain.Result.ok;

/**
 * Created by zouyongcan on 2020/3/13
 */
@RestController
@RequestMapping("/sys/roles")
@RequiredArgsConstructor
@RequiresAuthentication
public class RoleController {

    private final RoleService roleService;

    @RequiresPermissions("role_create")
    @PostMapping("")
    public Result<Long> create(@RequestBody Role body) {
        Role role = roleService.insert(body);
        return ok(role.getId());
    }

    @RequiresPermissions("role_edit")
    @PutMapping("/{id}")
    public Result<Role> edit(@PathVariable Long id, @RequestBody Role updateBody) {
        Role role = roleService.updateById(id, updateBody);
        return ok(role);
    }

    @RequiresPermissions("role_delete")
    @DeleteMapping("/{id}")
    public Result<Long> delete(@PathVariable Long id) {
        Role role = roleService.deleteById(id);
        return ok(role.getId());
    }

    @RequiresPermissions("role_details")
    @GetMapping("/{id}}")
    public Result<Role> details(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ok(role);
    }

    @RequiresPermissions("role_list")
    @GetMapping("/list")
    public Result<PageList<Role>> list(PageRequest pageRequest, @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String remark) {
        PageList<Role> list = roleService.page(pageRequest, name, remark);
        return ok(list);
    }

}
