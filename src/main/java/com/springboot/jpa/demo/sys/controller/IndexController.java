package com.springboot.jpa.demo.sys.controller;

import com.springboot.jpa.demo.core.config.session.ISession;
import com.springboot.jpa.demo.core.config.session.ISessionManager;
import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.config.web.interceptor.AuthenticationInterceptor;
import com.springboot.jpa.demo.core.domain.Result;
import com.springboot.jpa.demo.core.util.JSON;
import com.springboot.jpa.demo.core.web.annotation.OpLog;
import com.springboot.jpa.demo.core.web.annotation.OpLogModule;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import com.springboot.jpa.demo.entity.OperationLog;
import com.springboot.jpa.demo.entity.User;
import com.springboot.jpa.demo.sys.service.MenuService;
import com.springboot.jpa.demo.sys.service.OpLogService;
import com.springboot.jpa.demo.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

import static com.springboot.jpa.demo.core.domain.Result.ok;

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
@Api(value = "用户登录/注销", tags = "用户登录/注销")
public class IndexController {

    private final UserService userService;
    private final ISessionManager sessionManager;
    private final MenuService menuService;
    private final OpLogService opLogService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<Void> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        SessionInfo info = userService.loginValidate(user.getAccount(), user.getPasswd());
        // 将之前session信息从缓存中删除
        String sessionId = sessionManager.getSessionId(info.getId());
        if (StringUtils.isNotBlank(sessionId)) {
            sessionManager.removeSession(sessionId);
        }
        // 成功登录了 保存session会话到缓存
        ISession session = sessionManager.createSession(info.getId());
        session.setAttribute(SessionInfo.CACHE_SESSION_KEY, info);
        // 保存权限到缓存
        Set<String> set = menuService.findPermissionCodeByUserId(info.getId());
        Set<String> singleSet = new HashSet<>();
        for (String s : set) {
            if (!s.contains(",")) {
                singleSet.add(s);
                continue;
            }
            for (String ss : s.split(",")) {
                singleSet.add(ss);
            }
        }
        session.setAttribute(SessionInfo.CACHE_PERMISSION_KEY, JSON.toJSONString(singleSet));

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

    @ApiOperation("注销")
    @OpLog("注销")
    @RequiresAuthentication
    @PostMapping("/logout")
    public Result<Void> logout() {
        sessionManager.removeSession(SessionInfo.getCurrentSession().getSessionId());
        return ok();
    }

}
