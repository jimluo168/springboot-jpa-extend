package com.bms.sys.controller;

import com.bms.common.config.session.SessionInfo;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.entity.User;
import com.bms.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bms.common.domain.Result.ok;

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
@Api(value = "我的个人信息",tags = "我的个人信息")
public class MyProfileController {

    private final UserService userService;

    @ApiOperation("获取我的个人信息")
    @GetMapping("/profiles")
    public Result<User> profiles() {
        return ok(userService.findById(SessionInfo.getCurrentUserId()));
    }

}
