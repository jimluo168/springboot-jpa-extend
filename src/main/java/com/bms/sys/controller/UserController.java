package com.bms.sys.controller;

import com.bms.common.config.session.ISession;
import com.bms.common.config.session.ISessionManager;
import com.bms.common.config.session.SessionInfo;
import com.bms.common.config.web.interceptor.AuthenticationInterceptor;
import com.bms.common.domain.PageList;
import com.bms.common.domain.Result;
import com.bms.common.exception.ServiceException;
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
public class UserController {

    private final UserService userService;

    private final ISessionManager sessionManager;

    @PostMapping("/login")
    public Result<Void> login(@RequestBody User user, HttpServletResponse response) {
        SessionInfo info = userService.loginValidate(user.getAccount(), user.getPasswd());
        // 成功登录了 保存session会话到缓存
        ISession session = sessionManager.createSession();
        session.setAttribute(SessionInfo.CACHE_SESSION_KEY, info);
        response.addHeader(AuthenticationInterceptor.HTTP_HEAD_AUTH, session.getSessionId());
        return ok(null);
    }

    @PostMapping("")
    public Result<Long> create(@RequestBody User body) {
        Long id = userService.insert(body);
        return ok(id);
    }

    @GetMapping("/list")
    public Result<PageList<User>> list(Pageable pageable, String keyword) {
        return ok(userService.page(pageable, keyword));
    }
}
