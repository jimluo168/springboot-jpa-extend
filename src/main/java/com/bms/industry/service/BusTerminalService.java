package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusRoute;
import com.bms.entity.BusTerminal;
import com.bms.entity.BusTerminalAudit;
import com.bms.entity.Organization;
import com.bms.industry.dao.BusTerminalAuditRepository;
import com.bms.industry.dao.BusTerminalRepository;
import com.bms.industry.view.BusTerminalMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;
import static com.bms.industry.view.BusTerminalMenu.MENU_TYPE_ORG;

/**
 * 场站 service
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusTerminalService {

    private final BusTerminalRepository busTerminalRepository;
    private final BusTerminalAuditRepository busTerminalAuditRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public BusTerminal insert(BusTerminal busTerminal) {
        busTerminal.setId(flakeId.next());
        busTerminalRepository.save(busTerminal);
        return busTerminal;
    }

    public BusTerminal updateById(Long id, BusTerminal busTerminal) {
        BusTerminal value = this.findById(id);
        JpaUtils.copyNotNullProperties(busTerminal, value);
        return value;
    }

    public PageList<BusTerminal> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_BUS_TERMINAL_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusTerminal findById(Long id) {
        Optional<BusTerminal> busTerminal = busTerminalRepository.findById(id);
        if (busTerminal.isPresent()) {
            return busTerminal.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusTerminal deleteById(Long id) {
        BusTerminal busTerminal = this.findById(id);
        busTerminal.setDeleted(DELETE_TRUE);
        return busTerminal;
    }

    public void audit(Long id, int status, String reason) {
        BusTerminal busTerminal = this.findById(id);
        busTerminal.setStatus(status);
        busTerminal.setReason(reason);

        BusTerminalAudit audit = new BusTerminalAudit();
        audit.setId(flakeId.next());
        audit.setBusTerminal(busTerminal);
        audit.setReason(reason);
        busTerminalAuditRepository.save(audit);
    }

    public void saveAll(List<BusTerminal> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        busTerminalRepository.saveAll(list);
    }

    @Transactional(readOnly = true)
    public List<BusTerminal> findByName(String name) {
        return busTerminalRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name, Long id) {
        if (id == null) {
            return busTerminalRepository.countByNameAndDeleted(name, DELETE_FALSE) > 0;
        }
        return busTerminalRepository.countByNameAndIdNotAndDeleted(name, id, DELETE_FALSE) > 0;
    }

    @Transactional(readOnly = true)
    public boolean existsByCode(String code, Long id) {
        if (id == null) {
            return busTerminalRepository.countByCodeAndDeleted(code, DELETE_FALSE) > 0;
        }
        return busTerminalRepository.countByCodeAndIdNotAndDeleted(code, id, DELETE_FALSE) > 0;
    }

    @Transactional(readOnly = true)
    public List<BusTerminalMenu> busTerminalMenu() {
        List<BusTerminalMenu> busTerminalMenus = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_TERMINAL_MENU_ORG, null, BusTerminalMenu.class));
        busTerminalMenus.forEach(b -> b.setType(MENU_TYPE_ORG));
        List<BusTerminalMenu> terminalMenus = hibernateDao.getList(new DaoCmd(Constant.MAPPER_BUS_TERMINAL_MENU_TERMINAL, null, BusTerminalMenu.class));
        terminalMenus.forEach(t -> t.setType(BusTerminalMenu.MENU_TYPE_TERMINAL));

        // 第一级
        List<BusTerminalMenu> topMenus = busTerminalMenus.stream().filter(b -> b.getParentId() == null).collect(Collectors.toList());

        // 第二级
        for (BusTerminalMenu top : topMenus) {
            List<BusTerminalMenu> menus2nd = busTerminalMenus.stream().filter(b -> b.getParentId() != null && b.getParentId().longValue() == top.getId().longValue()).collect(Collectors.toList());
            // 第三级
            for (BusTerminalMenu m2 : menus2nd) {
                List<BusTerminalMenu> menus3rd = busTerminalMenus.stream().filter(b -> b.getParentId() != null && b.getParentId().longValue() == m2.getId().longValue()).collect(Collectors.toList());
                if (menus3rd.size() < 1) {
                    menus3rd = terminalMenus.stream().filter(t -> t.getOrgId() != null && t.getOrgId().longValue() == m2.getId().longValue()).collect(Collectors.toList());
                } else {
                    // 第四级
                    for (BusTerminalMenu m3 : menus3rd) {
                        List<BusTerminalMenu> menus4th = terminalMenus.stream().filter(t -> t.getOrgId() != null && t.getOrgId().longValue() == m3.getId().longValue()).collect(Collectors.toList());
                        m3.setChildren(menus4th);
                    }
                }
                m2.setChildren(menus3rd);
            }
            top.setChildren(menus2nd);
        }
        return topMenus;
    }
}
