package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JSON;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoEmergencyPreplan;
import com.bms.monitor.dao.EmergencyPreplanRepository;
import com.bms.monitor.view.MoRescueMaterialJson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 应急预案管理
 *
 * @author zouyongcan
 * @date 2020/4/8
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class EmergencyPreplanService {
    private final EmergencyPreplanRepository emergencyPreplanRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoEmergencyPreplan insert(MoEmergencyPreplan moEmergencyPreplan) {
        moEmergencyPreplan.setId(flakeId.next());
        if (moEmergencyPreplan.getRescueMaterialList() != null && !moEmergencyPreplan.getRescueMaterialList().isEmpty()) {
            List<MoRescueMaterialJson> jsonList = new ArrayList<>();
            moEmergencyPreplan.getRescueMaterialList().forEach(o -> {
                MoRescueMaterialJson json = new MoRescueMaterialJson();
                json.setId(o.getId());
                json.setUsageQuantity(o.getUsageQuantity());
                jsonList.add(json);
            });
            moEmergencyPreplan.setRescueMaterialJson(JSON.toJSONString(jsonList));
        }
        emergencyPreplanRepository.save(moEmergencyPreplan);
        return moEmergencyPreplan;
    }

    public MoEmergencyPreplan updateById(Long id, MoEmergencyPreplan moEmergencyPreplan) {
        MoEmergencyPreplan value = this.findById(id);
        JpaUtils.copyNotNullProperties(moEmergencyPreplan, value);
        if (moEmergencyPreplan.getRescueMaterialList() != null && !moEmergencyPreplan.getRescueMaterialList().isEmpty()) {
            List<MoRescueMaterialJson> jsonList = new ArrayList<>();
            moEmergencyPreplan.getRescueMaterialList().forEach(o -> {
                MoRescueMaterialJson json = new MoRescueMaterialJson();
                json.setId(o.getId());
                json.setUsageQuantity(o.getUsageQuantity());
                jsonList.add(json);
            });
            moEmergencyPreplan.setRescueMaterialJson(JSON.toJSONString(jsonList));
        }
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoEmergencyPreplan> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_EMERGENCY_PREPLAN_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoEmergencyPreplan findById(Long id) {
        Optional<MoEmergencyPreplan> emergencyPreplan = emergencyPreplanRepository.findById(id);
        if (emergencyPreplan.isPresent()) {
            return emergencyPreplan.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoEmergencyPreplan deleteById(Long id) {
        MoEmergencyPreplan moEmergencyPreplan = this.findById(id);
        moEmergencyPreplan.setDeleted(DELETE_TRUE);
        return moEmergencyPreplan;
    }

    @Transactional(readOnly = true)
    public PageList<MoEmergencyPreplan> namePage(PageRequest pageRequest, Map<String, Object> queryParams){
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_EMERGENCY_PREPLAN_NAME_PAGE, queryParams));
    }
}
