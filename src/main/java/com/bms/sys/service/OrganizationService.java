package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.exception.ExceptionFactory;
import com.bms.common.exception.ServiceException;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Organization;
import com.bms.sys.dao.OrganizationRepository;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
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
}
