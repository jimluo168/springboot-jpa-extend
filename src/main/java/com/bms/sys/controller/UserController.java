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
