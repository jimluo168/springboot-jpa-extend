package com.bms.sys.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Menu;
import com.bms.entity.Role;
import com.bms.entity.User;
import com.bms.Constant;
import com.bms.sys.dao.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final HibernateDao hibernateDao;

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
        DaoCmd cmd = new DaoCmd(Constant.MAPPER_MENU_FIND_PERMISSION_CODE_BY_USERID);
        cmd.getParams().put("userId", userId);
        List<String> list = hibernateDao.getList(cmd);
        return new HashSet<>(list);
    }

    private List<Menu> buildMyMenu(List<Menu> list) {
        Map<Long, Menu> parentMap = new LinkedHashMap<>();

        Map<Long, List<Menu>> leafMap = new LinkedHashMap<>();

        List<Menu> rootList = new ArrayList<>();

        for (Menu m : list) {
            // 忽略按钮
            if (m.getType() == Menu.TYPE_BTN) {
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
            rootList.add(entry.getValue());
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
}

