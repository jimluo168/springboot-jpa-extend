package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoEmergencyResponse;
import com.bms.monitor.dao.EmergencyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 应急响应处理.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class EmergencyResponseService {

    private final EmergencyResponseRepository emergencyResponseRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoEmergencyResponse insert(MoEmergencyResponse emergencyResponse) {
        emergencyResponse.setId(flakeId.next());
        emergencyResponseRepository.save(emergencyResponse);
        return emergencyResponse;
    }

    public MoEmergencyResponse updateById(Long id, MoEmergencyResponse emergencyResponse) {
        MoEmergencyResponse value = this.findById(id);
        JpaUtils.copyNotNullProperties(emergencyResponse, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoEmergencyResponse> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_EMERGENCY_RESPONSE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoEmergencyResponse findById(Long id) {
        Optional<MoEmergencyResponse> emergencyResponse = emergencyResponseRepository.findById(id);
        if (emergencyResponse.isPresent()) {
            return emergencyResponse.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoEmergencyResponse deleteById(Long id) {
        MoEmergencyResponse emergencyResponse = this.findById(id);
        emergencyResponse.setDeleted(DELETE_TRUE);
        return emergencyResponse;
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name, Long id) {
        if (id == null) {
            return emergencyResponseRepository.countByNameAndDeleted(name, DELETE_FALSE) > 0;
        }
        return emergencyResponseRepository.countByNameAndIdNotAndDeleted(name, id, DELETE_FALSE) > 0;
    }

    public MoEmergencyResponse follow(Long id, MoEmergencyResponse emergencyResponse) {
        MoEmergencyResponse value = this.findById(id);
        JpaUtils.copyNotNullProperties(emergencyResponse, value);
        value.setStatus(MoEmergencyResponse.STATUS_TO_BE_ASSESSED);
        return value;
    }

    public MoEmergencyResponse evaluate(Long id, MoEmergencyResponse emergencyResponse) {
        MoEmergencyResponse value = this.findById(id);
        JpaUtils.copyNotNullProperties(emergencyResponse, value);
        value.setStatus(MoEmergencyResponse.STATUS_HAS_BEEN_ASSESSED);
        return value;
    }

    public MoEmergencyResponse generateCase(Long id) {
        MoEmergencyResponse value = this.findById(id);
        value.setGenerateCase(MoEmergencyResponse.GENERATE_CASE_YES);
        return value;
    }

}