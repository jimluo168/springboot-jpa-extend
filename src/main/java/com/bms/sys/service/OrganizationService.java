package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.domain.PageList;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Organization;
import com.bms.entity.OrganizationAudit;
import com.bms.sys.dao.OrganizationAuditRepository;
import com.bms.sys.dao.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 机构组织Service.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final FlakeId flakeId;
    private final OrganizationAuditRepository organizationAuditRepository;

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
            throw ExceptionFactory.dataNotExist();
        }
    }

    public PageList<Organization> page(Pageable pageable, String name, int level) {
        Page<Organization> page = organizationRepository.findByNameLikeOrLevelOrderByCreateDateDesc(pageable, name, level);
        return new PageList<>(page.getTotalElements(), page.getContent());
    }

    public Organization findById(Long id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get();
        }
        throw ExceptionFactory.dataNotExist();
    }

    public Organization deleteById(Long id) {
        Organization organization = this.findById(id);
        organizationRepository.delete(organization);
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
