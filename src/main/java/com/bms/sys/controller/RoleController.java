package com.bms.sys.controller;

import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
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

    @PostMapping("")
    public Result<Long> create(@RequestBody Role body) {
        Role role = roleService.insert(body);
        return ok(role.getId());
    }

    @DeleteMapping("/{id}")
    public Result<Long> deleteById(@PathVariable Long id) {
        Role role = roleService.deleteById(id);
        return ok(role.getId());
    }

    @PutMapping("/{id}")
    public Result<Role> updateById(@PathVariable Long id, @RequestBody Role updateBody) {
        Role role = roleService.updateById(id, updateBody);
        return ok(role);
    }

    @GetMapping("/{id}}")
    public Result<Role> list(@PathVariable Long id) {
        Role role = roleService.findById(id);
        return ok(role);
    }

    @GetMapping("/list")
    public Result<PageList<Role>> list(PageRequest pageRequest, @RequestParam(defaultValue = "") String name) {
        PageList<Role> list = roleService.page(pageRequest, name);
        return ok(list);
    }

}
