package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Menu;
import com.bms.entity.Role;
import com.bms.entity.User;
import com.bms.sys.dao.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final FlakeId flakeId;
    private final UserService userService;

    public Menu insert(Menu menu) {
        menu.setId(flakeId.next());
        return menuRepository.save(menu);
    }

    public Menu findById(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            return menu.get();
        }
        throw ExceptionFactory.dataNotExistException();
    }

    public Menu deleteById(Long id) {
        Menu menu = this.findById(id);
        menuRepository.deleteById(id);
        return menu;
    }

    public Menu updateById(Long id, Menu updateBody) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            Menu value = menu.get();
            JpaUtils.copyNotNullProperties(updateBody, value);
            return value;
        } else {
            throw ExceptionFactory.dataNotExistException();
        }
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    public List<Menu> mymenus(Long userId) {
        User user = userService.findById(userId);
        Role role = user.getRole();
        if (role == null) {
            return Collections.emptyList();
        }
        return buildMenu(role.getMenuList());
    }

    public List<Menu> findAll() {
        List<Menu> list = menuRepository.findByDeletedAndParentIsNullOrderByIndexAsc(DELETE_FALSE);
        return buildMenu(list);
    }

    private List<Menu> buildMenu(List<Menu> menus) {
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        List<Menu> root = new ArrayList<>();
        menus.forEach(o -> {
            Menu m = new Menu();
            BeanUtils.copyProperties(o, m, "parent", "children");
            root.add(m);
            List<Menu> children = o.getChildren();
            if (children != null && !children.isEmpty()) {
                List<Menu> leaf = this.buildMenu(children);
                m.setChildren(leaf);
            }
        });
        return root;
    }
}

