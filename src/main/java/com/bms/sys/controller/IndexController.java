package com.bms.sys.controller;

import com.bms.common.config.session.ISession;
import com.bms.common.config.session.ISessionManager;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.config.web.interceptor.AuthenticationInterceptor;
import com.bms.common.domain.Result;
import com.bms.entity.User;
import com.bms.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class IndexController {

    private final UserService userService;

    private final ISessionManager sessionManager;

    @PostMapping("/login")
    public Result<Void> login(@RequestBody User user, HttpServletResponse response) {
        SessionInfo info = userService.loginValidate(user.getAccount(), user.getPasswd());
        // 成功登录了 保存session会话到缓存
        ISession session = sessionManager.createSession();
        session.setAttribute(SessionInfo.CACHE_SESSION_KEY, info);
        response.addHeader(AuthenticationInterceptor.HTTP_HEAD_AUTH, session.getSessionId());
        return ok();
    }
}
