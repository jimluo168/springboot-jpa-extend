package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoRescueRescuer;
import com.bms.entity.OrganizationAudit;
import com.bms.monitor.dao.RescueRescuerRepository;
import com.bms.sys.dao.OrganizationAuditRepository;
import com.bms.sys.dao.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 救援资源管理-人员.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class RescueRescuerService {

    private final RescueRescuerRepository rescueRescuerRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoRescueRescuer insert(MoRescueRescuer organization) {
        organization.setId(flakeId.next());
        rescueRescuerRepository.save(organization);
        return organization;
    }

    public MoRescueRescuer updateById(Long id, MoRescueRescuer organization) {
        MoRescueRescuer value = this.findById(id);
        JpaUtils.copyNotNullProperties(organization, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueRescuer> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_RESCUER_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueRescuer> orgnamePage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_RESCUER_ORGNAME_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueRescuer> positionPage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_RESCUER_POSITION_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoRescueRescuer findById(Long id) {
        Optional<MoRescueRescuer> organization = rescueRescuerRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoRescueRescuer deleteById(Long id) {
        MoRescueRescuer organization = this.findById(id);
        organization.setDeleted(DELETE_TRUE);
        return organization;
    }

    public void audit(Long id, int status, String reason) {
        MoRescueRescuer organization = this.findById(id);
        organization.setStatus(status);
        organization.setReason(reason);
    }

    public void saveAll(List<MoRescueRescuer> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        rescueRescuerRepository.saveAll(list);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name, Long id) {
        if (id == null) {
            return rescueRescuerRepository.countByNameAndDeleted(name, DELETE_FALSE) > 0;
        }
        return rescueRescuerRepository.countByNameAndIdNotAndDeleted(name, id, DELETE_FALSE) > 0;
    }
}
