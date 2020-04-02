package com.bms.sys.controller;

import com.bms.common.config.session.SessionInfo;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.entity.Menu;
import com.bms.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bms.common.domain.Result.ok;

/**
 * 菜单管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@RestController
@RequestMapping("/sys/menus")
@RequiredArgsConstructor
@RequiresAuthentication
@Api(value = "菜单管理",tags = "菜单管理")
public class MenuController {

    private final MenuService menuService;

    @ApiOperation("获取我授权的菜单")
    @GetMapping("/my")
    public Result<List<Menu>> my() {
        Long userId = SessionInfo.getCurrentUserId();
        List<Menu> list = menuService.mymenus(userId);
        return ok(list);
    }

    @ApiOperation("获取全部菜单")
    @GetMapping("/all")
    public Result<List<Menu>> all() {
        List<Menu> list = menuService.findAll();
        return ok(list);
    }
}
