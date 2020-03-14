package com.bms.sys.controller;

import com.bms.common.config.session.ISession;
import com.bms.common.config.session.ISessionManager;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.config.web.interceptor.AuthenticationInterceptor;
import com.bms.common.domain.Result;
import com.bms.common.util.JSON;
import com.bms.common.web.annotation.OpLog;
import com.bms.common.web.annotation.OpLogModule;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.entity.OperationLog;
import com.bms.entity.Organization;
import com.bms.entity.User;
import com.bms.sys.service.MenuService;
import com.bms.sys.service.OpLogService;
import com.bms.sys.service.OrganizationService;
import com.bms.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bms.common.domain.Result.ok;

/**
 * Index.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
@OpLogModule("用户管理")
public class IndexController {

    private final UserService userService;
    private final ISessionManager sessionManager;
    private final MenuService menuService;
    private final OpLogService opLogService;

    @PostMapping("/login")
    public Result<Void> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        SessionInfo info = userService.loginValidate(user.getAccount(), user.getPasswd());
        // 成功登录了 保存session会话到缓存
        ISession session = sessionManager.createSession();
        session.setAttribute(SessionInfo.CACHE_SESSION_KEY, info);
        // 保存权限到缓存
        session.setAttribute(SessionInfo.CACHE_PERMISSION_KEY, JSON.toJSONString(menuService.findPermissionCodeByUserId(info.getId())));

        // 设置头信息
        response.addHeader(AuthenticationInterceptor.HTTP_HEAD_AUTH, session.getSessionId());

        // 记录操作日志
        OperationLog log = new OperationLog();
        BeanUtils.copyProperties(info, log);
        log.setRealName(info.getName());
        log.setModule("用户管理");
        log.setFuncName("登录");
        log.setIp(request.getRemoteAddr());
        log.setUrl(request.getRequestURI());
        log.setCreateUser(info.getId());
        log.setLastUpdUser(info.getId());
        opLogService.insert(log);
        return ok();
    }

    @OpLog("退出")
    @RequiresAuthentication
    @PostMapping("/logout")
    public Result<Void> logout() {
        sessionManager.removeSession(SessionInfo.getCurrentSession().getSessionId());
        return ok();
    }

}
