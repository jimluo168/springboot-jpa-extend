package com.bms.industry.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusTerminal;
import com.bms.entity.BusTerminalAudit;
import com.bms.industry.dao.BusTerminalAuditRepository;
import com.bms.industry.dao.BusTerminalRepository;
import com.bms.sys.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

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
}
