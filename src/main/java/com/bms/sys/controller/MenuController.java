package com.bms.sys.controller;

import com.bms.common.config.session.SessionInfo;
import com.bms.common.domain.Result;
import com.bms.common.web.annotation.RequiresAuthentication;
import com.bms.entity.Menu;
import com.bms.sys.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/my")
    public Result<List<Menu>> my() {
        Long userId = SessionInfo.getCurrentUserId();
        List<Menu> list = menuService.mymenus(userId);
        return ok(list);
    }

    @GetMapping("/all")
    public Result<List<Menu>> all() {
        List<Menu> list = menuService.findAll();
        return ok(list);
    }
}
