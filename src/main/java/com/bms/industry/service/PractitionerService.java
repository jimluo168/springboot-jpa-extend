package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Practitioner;
import com.bms.entity.PractitionerAudit;
import com.bms.industry.dao.PractitionerAuditRepository;
import com.bms.industry.dao.PractitionerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 从业人员 service
 *
 * @author zouyongcan
 * @date 2020/3/16
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class PractitionerService {

    private final PractitionerRepository practitionerRepository;
    private final PractitionerAuditRepository practitionerAuditRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public Practitioner insert(Practitioner practitioner) {
        practitioner.setId(flakeId.next());
        practitionerRepository.save(practitioner);
        return practitioner;
    }

    public Practitioner updateById(Long id, Practitioner practitioner) {
        Practitioner value = this.findById(id);
        JpaUtils.copyNotNullProperties(practitioner, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<Practitioner> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_PRACTITIONER_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Practitioner findById(Long id) {
        Optional<Practitioner> practitioner = practitionerRepository.findById(id);
        if (practitioner.isPresent()) {
            return practitioner.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Practitioner deleteById(Long id) {
        Practitioner practitioner = this.findById(id);
        practitioner.setDeleted(DELETE_TRUE);
        return practitioner;
    }

    public void audit(Long id, int status, String reason) {
        Practitioner practitioner = this.findById(id);

        PractitionerAudit audit = new PractitionerAudit();
        BeanUtils.copyProperties(practitioner, audit);
        audit.setId(flakeId.next());
        audit.setPractitioner(practitioner);
        practitionerAuditRepository.save(audit);

        practitioner.setStatus(status);
        practitioner.setReason(reason);
    }

    public void saveAll(List<Practitioner> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        practitionerRepository.saveAll(list);
    }

    @Transactional(readOnly = true)
    public Practitioner findByName(String name) {
        return practitionerRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean existsByStaffNumber(String staffNumber, Long id) {
        if (id == null) {
            return practitionerRepository.countByStaffNumber(staffNumber) > 0;
        }
        return practitionerRepository.countByStaffNumberAndIdNot(staffNumber, id) > 0;
    }

    @Transactional(readOnly = true)
    public boolean existsByIdNumber(String idNumber, Long id) {
        if (id == null) {
            return practitionerRepository.countByIdNumber(idNumber) > 0;
        }
        return practitionerRepository.countByIdNumberAndIdNot(idNumber, id) > 0;
    }
}
