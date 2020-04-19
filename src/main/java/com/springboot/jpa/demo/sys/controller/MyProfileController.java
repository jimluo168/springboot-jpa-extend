package com.springboot.jpa.demo.sys.controller;

import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.domain.Result;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import com.springboot.jpa.demo.entity.User;
import com.springboot.jpa.demo.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springboot.jpa.demo.core.domain.Result.ok;

/**
 * 用户controller.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestController
@RequestMapping("/sys/my")
@RequiredArgsConstructor
@RequiresAuthentication
@Api(value = "我的个人信息", tags = "我的个人信息")
public class MyProfileController {

    private final UserService userService;

    @ApiOperation("获取我的个人信息")
    @GetMapping("/profiles")
    public Result<User> profiles() {
        return ok(userService.findById(SessionInfo.getCurrentUserId()));
    }

}
