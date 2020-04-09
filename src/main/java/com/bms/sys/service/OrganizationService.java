package com.bms.sys.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Organization;
import com.bms.entity.OrganizationAudit;
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
 * 公交企业管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final FlakeId flakeId;
    private final OrganizationAuditRepository organizationAuditRepository;
    private final HibernateDao hibernateDao;

    public Organization insert(Organization organization) {
        organization.setId(flakeId.next());
        organizationRepository.save(organization);
        return organization;
    }

    public Organization updateById(Long id, Organization organization) {
        Organization value = this.findById(id);
        JpaUtils.copyNotNullProperties(organization, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<Organization> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_ORGANIZATION_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Organization findById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Organization deleteById(Long id) {
        Organization organization = this.findById(id);
        organization.setDeleted(DELETE_TRUE);
        return organization;
    }

    public void audit(Long id, int status, String reason) {
        Organization organization = this.findById(id);

        OrganizationAudit audit = new OrganizationAudit();
        BeanUtils.copyProperties(organization, audit);
        audit.setId(flakeId.next());
        audit.setOrganization(organization);
        organizationAuditRepository.save(audit);

        organization.setStatus(status);
        organization.setReason(reason);
    }

    public void saveAll(List<Organization> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        organizationRepository.saveAll(list);
    }

    @Transactional(readOnly = true)
    public Organization findByName(String name) {
        return organizationRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name, Long id) {
        if (id == null) {
            return organizationRepository.countByNameAndDeleted(name, DELETE_FALSE) > 0;
        }
        return organizationRepository.countByNameAndIdNotAndDeleted(name, id, DELETE_FALSE) > 0;
    }

    @Transactional(readOnly = true)
    public Organization findByOId(String oId) {
        return organizationRepository.findByoId(oId);
    }

}
