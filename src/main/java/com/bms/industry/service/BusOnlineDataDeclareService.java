package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusOnlineDataDeclare;
import com.bms.entity.BusOnlineDataDeclareAudit;
import com.bms.industry.dao.BusOnlineDataDeclareAuditRepository;
import com.bms.industry.dao.BusOnlineDataDeclareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 申报管理.
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareService {
    private final BusOnlineDataDeclareRepository busOnlineDataDeclareRepository;
    private final FlakeId flakeId;
    private final BusOnlineDataDeclareAuditRepository busOnlineDataDeclareAuditRepository;
    private final HibernateDao hibernateDao;

    public BusOnlineDataDeclare insert(BusOnlineDataDeclare busOnlineDataDeclare) {
        busOnlineDataDeclare.setId(flakeId.next());
        busOnlineDataDeclareRepository.save(busOnlineDataDeclare);
        return busOnlineDataDeclare;
    }

    public BusOnlineDataDeclare updateById(Long id, BusOnlineDataDeclare busOnlineDataDeclare) {
        BusOnlineDataDeclare value = this.findById(id);
        JpaUtils.copyNotNullProperties(busOnlineDataDeclare, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<BusOnlineDataDeclare> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_ONLINE_DATA_DECLARE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusOnlineDataDeclare findById(Long id) {
        Optional<BusOnlineDataDeclare> busOnlineDataDeclare = busOnlineDataDeclareRepository.findById(id);
        if (busOnlineDataDeclare.isPresent()) {
            return busOnlineDataDeclare.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusOnlineDataDeclare deleteById(Long id) {
        BusOnlineDataDeclare busOnlineDataDeclare = this.findById(id);
        busOnlineDataDeclare.setDeleted(DELETE_TRUE);
        return busOnlineDataDeclare;
    }

    public void audit(Long id, int status, String reason) {
        BusOnlineDataDeclare busOnlineDataDeclare = this.findById(id);
        busOnlineDataDeclare.setStatus(status);
        busOnlineDataDeclare.setReason(reason);

        BusOnlineDataDeclareAudit audit = new BusOnlineDataDeclareAudit();
        audit.setId(flakeId.next());
        audit.setDeclare(busOnlineDataDeclare);
        audit.setReason(reason);
        busOnlineDataDeclareAuditRepository.save(audit);
    }

    public void saveAll(List<BusOnlineDataDeclare> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        busOnlineDataDeclareRepository.saveAll(list);
    }

}
