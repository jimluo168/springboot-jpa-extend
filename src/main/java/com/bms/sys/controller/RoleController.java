package com.bms.sys.controller;

import com.bms.ErrorCodes;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.util.BeanMapper;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.Role;
import com.bms.sys.service.RoleService;
import io.swagger.annotations.Api;
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
@OpLogModule("角色管理")
@Api(value = "角色管理", tags = "角色管理")
public class RoleController {

    private final RoleService roleService;

    @OpLog("新增")
    @RequiresPermissions("role_create")
    @PostMapping("")
    public Result<Role> create(@RequestBody Role role) {
        boolean exists = roleService.existsName(role.getName(), null);
        if (exists) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "角色名称已存在", true);
        }
        return ok(roleService.insert(role));
    }

    @OpLog("编辑")
    @RequiresPermissions("role_edit")
    @PutMapping("/{id}")
    public Result<Role> edit(@PathVariable Long id, @RequestBody Role role) {
        boolean exists = roleService.existsName(role.getName(), id);
        if (exists) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "角色名称已存在",true);
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
        return ok(roleService.page(pageRequest, BeanMapper.toMap(role)));
    }

}
