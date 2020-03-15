package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Organization;
import com.bms.entity.OrganizationAudit;
import com.bms.sys.Constant;
import com.bms.sys.dao.OrganizationAuditRepository;
import com.bms.sys.dao.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 机构组织Service.
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
        Optional<Organization> originalOrgan = organizationRepository.findById(id);
        if (originalOrgan.isPresent()) {
            Organization value = originalOrgan.get();
            JpaUtils.copyNotNullProperties(organization, value);
            return value;
        } else {
            throw ExceptionFactory.dataNotExistException();
        }
    }

    public PageList<Organization> page(PageRequest pageRequest, Map<String,Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_ORGANIZATION_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Organization findById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get();
        }
        throw ExceptionFactory.dataNotExistException();
    }

    public Organization deleteById(Long id) {
        Organization organization = this.findById(id);
        organization.setDeleted(DELETE_TRUE);
        return organization;
    }

    public void audit(Long id, int status, String reason) {
        Organization organization = this.findById(id);
        organization.setStatus(status);
        organization.setReason(reason);

        OrganizationAudit audit = new OrganizationAudit();
        audit.setId(flakeId.next());
        audit.setOrganization(organization);
        audit.setReason(reason);
        organizationAuditRepository.save(audit);
    }
}
