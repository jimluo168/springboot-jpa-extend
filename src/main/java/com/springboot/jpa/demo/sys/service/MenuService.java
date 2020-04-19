package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.config.flake.FlakeId;
import com.springboot.jpa.demo.core.util.JpaUtils;
import com.springboot.jpa.demo.entity.Menu;
import com.springboot.jpa.demo.entity.Role;
import com.springboot.jpa.demo.entity.User;
import com.springboot.jpa.demo.sys.dao.MenuMapper;
import com.springboot.jpa.demo.sys.dao.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.springboot.jpa.demo.core.domain.BaseEntity.DELETE_FALSE;

/**
 * 菜单管理.
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
    private final MenuMapper menuMapper;

    public Menu insert(Menu menu) {
        menu.setId(flakeId.next());
        return menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    public Menu findById(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            return menu.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Menu deleteById(Long id) {
        Menu menu = this.findById(id);
        menuRepository.deleteById(id);
        return menu;
    }

    public Menu updateById(Long id, Menu updateBody) {
        Menu value = this.findById(id);
        JpaUtils.copyNotNullProperties(updateBody, value);
        return value;
    }

    @Transactional(readOnly = true)
    public List<Menu> mymenus(Long userId) {
        User user = userService.findById(userId);
        Role role = user.getRole();
        if (role == null) {
            return Collections.emptyList();
        }
        return buildMyMenu(role.getMenuList());
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        List<Menu> list = menuRepository.findByDeletedAndParentIsNullOrderByIndexAsc(DELETE_FALSE);
        return buildMenu(list);
    }

    @Transactional(readOnly = true)
    public Set<String> findPermissionCodeByUserId(Long userId) {
        List<String> list = menuMapper.findPermissionCodeByUserId(userId);
        return new HashSet<>(list);
    }

    private List<Menu> buildMyMenu(List<Menu> list) {
        Map<Long, Menu> parentMap = new LinkedHashMap<>();

        Map<Long, List<Menu>> leafMap = new LinkedHashMap<>();

        List<Menu> rootList = new ArrayList<>();

        for (Menu m : list) {
            // 忽略按钮 和 tab页
            if (m.getType() == Menu.TYPE_BTN || m.getType() == Menu.TYPE_TAB) {
                continue;
            }
            if (m.getParent() == null) {
                parentMap.put(m.getId(), copyMenu(m));
            } else {
                Menu parent = parentMap.get(m.getParent().getId());
                if (parent != null) {
                    parent.getChildren().add(copyMenu(m));
                } else {
                    List<Menu> leaf = leafMap.get(m.getParent().getId());
                    if (leaf == null) {
                        leaf = new ArrayList<>();
                        leafMap.put(m.getParent().getId(), leaf);
                    }
                    leaf.add(copyMenu(m));
                }
            }
        }

        // 叶子节点
        for (Map.Entry<Long, List<Menu>> entry : leafMap.entrySet()) {
            Long parentId = entry.getKey();
            Menu parent = parentMap.get(parentId);
            if (parent != null) {
                parent.getChildren().addAll(entry.getValue());
                Collections.sort(parent.getChildren(), (m1, m2) -> {
                    return m1.getIndex() - m2.getIndex();
                });
            }
        }
        // 父节点
        for (Map.Entry<Long, Menu> entry : parentMap.entrySet()) {
            Menu menu = entry.getValue();
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                Collections.sort(menu.getChildren(), (m1, m2) -> {
                    return m1.getIndex() - m2.getIndex();
                });
            }
            rootList.add(menu);
        }

        Collections.sort(rootList, (m1, m2) -> {
            return m1.getIndex() - m2.getIndex();
        });

        return rootList;
    }

    private Menu copyMenu(Menu source) {
        Menu target = new Menu();
        BeanUtils.copyProperties(source, target, "parent", "children", "roleList");
        target.setChildren(new ArrayList<>());
        return target;
    }

    private List<Menu> buildMenu(List<Menu> menus) {
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        List<Menu> root = new ArrayList<>();
        menus.forEach(o -> {
            Menu m = copyMenu(o);
            root.add(m);
            List<Menu> children = o.getChildren();
            if (children != null && !children.isEmpty()) {
                List<Menu> leaf = this.buildMenu(children);
                m.setChildren(leaf);
            }
        });
        return root;
    }

    @Transactional(readOnly = true)
    public List<Menu> alltabs(Long parentId) {
//        Menu parent = this.findById(parentId);
        List<Menu> tabs = menuRepository.findByDeletedAndParentIdOrderByIndexAsc(DELETE_FALSE, parentId);
        if (tabs == null || tabs.isEmpty()) {
            return Collections.emptyList();
        }
        List<Menu> root = new ArrayList<>();
        tabs.forEach(o -> {
            Menu m = copyMenu(o);
            root.add(m);
        });
        return root;
    }

    @Transactional(readOnly = true)
    public List<Menu> mytabs(Long userId, Long parentId) {
        List<Menu> tabs = menuMapper.findByUserIdAndParentIdAndType(userId, parentId, Menu.TYPE_TAB);
        if (tabs == null || tabs.isEmpty()) {
            return Collections.emptyList();
        }
        List<Menu> root = new ArrayList<>();
        tabs.forEach(o -> {
            Menu m = copyMenu(o);
            root.add(m);
        });
        return root;
    }
}

