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
import com.bms.industry.controller.BusSiteController;
import com.bms.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ISessionManager sessionManager;

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
                                       @RequestParam(defaultValue = "") String realName,
                                       @RequestParam(defaultValue = "") String organization,
                                       @RequestParam(defaultValue = "-1") int status) {
        logger.debug("!!!!!!!!!"+realName);
        return ok(userService.page(pageRequest, account, realName, organization, status));
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
        User user = userService.status(id, status);
        // 更新Session的缓存
        String sessionId = sessionManager.getSessionId(user.getId());
        if (StringUtils.isNotBlank(sessionId)) {
            ISession session = sessionManager.getSession(sessionId);
            SessionInfo info = session.getAttribute(SessionInfo.CACHE_SESSION_KEY, SessionInfo.class);
            info.setStatus(user.getStatus());
            session.setAttribute(SessionInfo.CACHE_SESSION_KEY, info);
        }
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
