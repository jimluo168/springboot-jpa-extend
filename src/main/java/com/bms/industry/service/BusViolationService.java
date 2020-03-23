package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusViolation;
import com.bms.entity.VehicleAudit;
import com.bms.industry.dao.BusViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 违规信息管理.
 *
 * @author luojimeng
 * @date 2020/3/20
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusViolationService {

    private final BusViolationRepository busViolationRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public BusViolation insert(BusViolation busViolation) {
        busViolation.setId(flakeId.next());
        busViolationRepository.save(busViolation);
        return busViolation;
    }

    public BusViolation updateById(Long id, BusViolation busViolation) {
        BusViolation value = this.findById(id);
        JpaUtils.copyNotNullProperties(busViolation, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<BusViolation> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_BUS_VIOLATION_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusViolation findById(Long id) {
        Optional<BusViolation> busViolation = busViolationRepository.findById(id);
        if (busViolation.isPresent()) {
            return busViolation.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusViolation deleteById(Long id) {
        BusViolation busViolation = this.findById(id);
        busViolation.setDeleted(DELETE_TRUE);
        return busViolation;
    }

    public void saveAll(List<BusViolation> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        busViolationRepository.saveAll(list);
    }

    /**
     * 处理.
     */
    public BusViolation deal(Long id, BusViolation busViolation) {
        BusViolation violation = this.findById(id);
        violation.setStatus(BusViolation.STATUS_PROCESSED);
        violation.setDealOpinion(busViolation.getDealOpinion());
        violation.setDealPhotos(busViolation.getDealPhotos());
        violation.setAttachs(busViolation.getAttachs());
        violation.setTransactor(busViolation.getTransactor());
        return violation;
    }
}
