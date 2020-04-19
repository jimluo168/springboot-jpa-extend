package com.springboot.jpa.demo.sys.controller;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.config.session.ISession;
import com.springboot.jpa.demo.core.config.session.ISessionManager;
import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.core.domain.Result;
import com.springboot.jpa.demo.core.web.annotation.OpLog;
import com.springboot.jpa.demo.core.web.annotation.OpLogModule;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import com.springboot.jpa.demo.core.web.annotation.RequiresPermissions;
import com.springboot.jpa.demo.entity.User;
import com.springboot.jpa.demo.sys.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "用户管理", tags = "用户管理")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ISessionManager sessionManager;

    @OpLog("新增")
    @RequiresPermissions("user_create")
    @PostMapping("")
    public Result<User> create(@RequestBody User user) {
        if (userService.existsByAccount(user.getAccount())) {
            throw ErrorCodes.build(ErrorCodes.RECORD_EXISTS, "用户名已存在", true);
        }
        return Result.ok(userService.insert(user));
    }

    @OpLog("查询")
    @RequiresPermissions("user_list")
    @GetMapping("/list")
    public Result<PageList<User>> list(PageRequest pageRequest, User user) throws IllegalAccessException {
        return Result.ok(userService.page(pageRequest, user));
    }

    @OpLog("详情")
    @RequiresPermissions("user_details")
    @GetMapping("/{id}")
    public Result<User> details(@PathVariable Long id) {
        User user = userService.findById(id);
        return Result.ok(user);
    }

    @OpLog("编辑")
    @RequiresPermissions("user_edit")
    @PutMapping("/{id}")
    public Result<User> edit(@PathVariable Long id, @RequestBody User updateBody) {
        User user = userService.updateById(id, updateBody);
        return Result.ok(user);
    }

    @OpLog("删除")
    @RequiresPermissions("user_delete")
    @DeleteMapping("/{id}")
    public Result<Long> delete(@PathVariable Long id) {
        User user = userService.deleteById(id);
        return Result.ok(user.getId());
    }

    @OpLog("禁用/启用")
    @RequiresPermissions("user_status")
    @PutMapping("/{id}/status/{status}")
    public Result<User> status(@PathVariable Long id, @PathVariable int status) {
        User user = userService.status(id, status);
        // 更新Session的缓存
        String sessionId = sessionManager.getSessionId(user.getId());
        if (StringUtils.isNotBlank(sessionId)) {
            ISession session = sessionManager.getSession(sessionId);
            SessionInfo info = session.getAttribute(SessionInfo.CACHE_SESSION_KEY, SessionInfo.class);
            info.setStatus(user.getStatus());
            session.setAttribute(SessionInfo.CACHE_SESSION_KEY, info);
        }
        return Result.ok(user);
    }

    @OpLog("重置密码")
    @RequiresPermissions("user_resetpasswd")
    @PostMapping("/{id}/resetpasswd")
    public Result<User> resetPasswd(@PathVariable Long id) {
        User user = userService.resetPasswd(id);
        return Result.ok(user);
    }

}
