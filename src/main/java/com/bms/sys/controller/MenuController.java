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
public class MenuController {

    private final MenuService menuService;

    /**
     * 新增菜单
     *
     * @param body
     * @return
     */
    @PostMapping("")
    public Result<Long> create(@RequestBody Menu body) {
        Menu menu = menuService.insert(body);
        return ok(menu.getId());
    }

    /**
     * 根据id删除菜单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Long> deleteById(@PathVariable Long id) {
        Menu menu = menuService.deleteById(id);
        return ok(menu.getId());
    }

    /**
     * 根据id编辑菜单
     *
     * @param id
     * @param body
     * @return
     */
    @PutMapping("/{id}")
    public Result<Menu> edit(@PathVariable Long id, @RequestBody Menu body) {
        Menu menu = menuService.updateById(id, body);
        return Result.ok(menu);
    }

    @RequiresAuthentication
    @GetMapping("/my")
    public Result<List<Menu>> mymenus() {
        Long userId = SessionInfo.getCurrentUserId();
        List<Menu> list = menuService.mymenus(userId);
        return ok(list);
    }

    @RequiresAuthentication
    @GetMapping("/all")
    public Result<List<Menu>> all() {
        List<Menu> list = menuService.findAll();
        return ok(list);
    }

}
