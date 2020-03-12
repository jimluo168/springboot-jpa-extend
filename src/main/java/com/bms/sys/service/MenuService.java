package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Menu;
import com.bms.entity.Organization;
import com.bms.sys.dao.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Menu insert(Menu menu) {
        menu.setId(flakeId.next());
        return menuRepository.save(menu);
    }

    public Menu findById(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            return menu.get();
        }
        throw ExceptionFactory.dataNotExist();
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
            throw ExceptionFactory.dataNotExist();
        }
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}

