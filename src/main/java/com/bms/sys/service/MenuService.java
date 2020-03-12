package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.Menu;
import com.bms.sys.dao.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
