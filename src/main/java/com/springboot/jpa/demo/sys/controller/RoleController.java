package com.springboot.jpa.demo.sys.controller;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.domain.Result;
import com.springboot.jpa.demo.core.web.annotation.OpLog;
import com.springboot.jpa.demo.core.web.annotation.OpLogModule;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import com.springboot.jpa.demo.core.web.annotation.RequiresPermissions;
import com.springboot.jpa.demo.entity.Role;
import com.springboot.jpa.demo.sys.service.RoleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.springboot.jpa.demo.core.domain.Result.ok;

/**
 * 角色管理.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@RestController
@RequestMapping("/sys/roles")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("角色管理")
@Api(value = "角色管理", tags = "角色管理")
public class RoleController {

    private final RoleService roleService;

    @OpLog("新增")
    @RequiresPermissions("role_create")
    @PostMapping("")
    public Result<Role> create(@RequestBody Role role) {
        if (roleService.existsByName(role.getName(), null)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "角色名称已存在", true);
        }
        return ok(roleService.insert(role));
    }

    @OpLog("编辑")
    @RequiresPermissions("role_edit")
    @PutMapping("/{id}")
    public Result<Role> edit(@PathVariable Long id, @RequestBody Role role) {
        if (roleService.existsByName(role.getName(), id)) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "角色名称已存在", true);
        }
        return ok(roleService.updateById(id, role));
    }

    @OpLog("删除")
    @RequiresPermissions("role_delete")
    @DeleteMapping("/{id}")
    public Result<Role> delete(@PathVariable Long id) {
        return ok(roleService.deleteById(id));
    }

    @OpLog("详情")
    @RequiresPermissions("role_details")
    @GetMapping("/{id}")
    public Result<Role> details(@PathVariable Long id) {
        return ok(roleService.findById(id));
    }

    @OpLog("查询")
    @RequiresPermissions("role_list")
    @GetMapping("/list")
    public Result<PageList<Role>> list(PageRequest pageRequest, Role role) throws IllegalAccessException {
        return ok(roleService.page(pageRequest, role));
    }

}
