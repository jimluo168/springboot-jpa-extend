package com.bms.sys.controller;

import com.bms.common.config.session.ISession;
import com.bms.common.config.session.ISessionManager;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.config.web.interceptor.AuthenticationInterceptor;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.domain.Result;
import com.bms.common.exception.ServiceException;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.common.web.annotation.RequiresPermissions;
import com.bms.entity.User;
import com.bms.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.bms.common.domain.Result.ok;

/**
 * 用户controller.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestController
@RequestMapping("/sys/users")
@RequiredArgsConstructor
@RequiresAuthentication
@OpLogModule("用户管理")
public class UserController {

    private final UserService userService;

    @OpLog("新增")
    @RequiresPermissions("user_create")
    @PostMapping("")
    public Result<User> create(@RequestBody User user) {
        return ok(userService.insert(user));
    }

    @OpLog("查询")
    @RequiresPermissions("user_list")
    @GetMapping("/list")
    public Result<PageList<User>> list(PageRequest pageRequest,
                                       @RequestParam(defaultValue = "") String account,
                                       @RequestParam(defaultValue = "") String real_name,
                                       @RequestParam(defaultValue = "") String organization,
                                       @RequestParam(defaultValue = "-1" ) int status) {
        return ok(userService.page(pageRequest, account, real_name, organization, status));
    }

    @OpLog("详情")
    @RequiresPermissions("user_details")
    @GetMapping("/{id}")
    public Result<User> details(@PathVariable Long id) {
        User user = userService.findById(id);
        return ok(user);
    }

    @OpLog("编辑")
    @RequiresPermissions("user_edit")
    @PutMapping("/{id}")
    public Result<User> edit(@PathVariable Long id, @RequestBody User updateBody) {
        User user = userService.updateById(id, updateBody);
        return ok(user);
    }

    @OpLog("删除")
    @RequiresPermissions("user_delete")
    @DeleteMapping("/{id}")
    public Result<Long> delete(@PathVariable Long id) {
        User user = userService.deleteById(id);
        return ok(user.getId());
    }

    @OpLog("禁用/启用")
    @RequiresPermissions("user_status")
    @PutMapping("/{id}/status/{status}")
    public Result<User> status(@PathVariable Long id, @PathVariable int status) {
        User updateBody = new User();
        updateBody.setStatus(status);
        User user = userService.updateById(id, updateBody);
        return ok(user);
    }

    @OpLog("重置密码")
    @RequiresPermissions("user_resetpasswd")
    @PostMapping("/{id}/resetpasswd")
    public Result<User> resetPasswd(@PathVariable Long id) {
        User user = userService.resetPasswd(id);
        return ok(user);
    }
}
