package com.springboot.jpa.demo.sys.controller;

import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.domain.Result;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import com.springboot.jpa.demo.entity.Menu;
import com.springboot.jpa.demo.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springboot.jpa.demo.core.domain.Result.ok;

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
@Api(value = "菜单管理", tags = "菜单管理")
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

    @ApiOperation("菜单下所有的tabs页签")
    @GetMapping("/{id}/alltabs")
    public Result<List<Menu>> alltabs(@PathVariable Long id) {
        List<Menu> list = menuService.alltabs(id);
        return ok(list);
    }

    @ApiOperation("菜单下我拥有的tabs页签")
    @GetMapping("/{id}/mytabs")
    public Result<List<Menu>> mytabs(@PathVariable Long id) {
        Long userId = SessionInfo.getCurrentUserId();
        List<Menu> list = menuService.mytabs(userId, id);
        return ok(list);
    }

}
