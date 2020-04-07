package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoRescueVehicle;
import com.bms.monitor.dao.RescueVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 救援资源管理-车辆.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class RescueVehicleService {

    private final RescueVehicleRepository rescueVehicleRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoRescueVehicle insert(MoRescueVehicle moRescueVehicle) {
        moRescueVehicle.setId(flakeId.next());
        rescueVehicleRepository.save(moRescueVehicle);
        return moRescueVehicle;
    }

    public MoRescueVehicle updateById(Long id, MoRescueVehicle moRescueVehicle) {
        MoRescueVehicle value = this.findById(id);
        JpaUtils.copyNotNullProperties(moRescueVehicle, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueVehicle> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_VEHICLE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueVehicle> orgnamePage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_VEHICLE_ORGNAME_PAGEE, queryParams));
    }

    @Transactional(readOnly = true)
    public PageList<MoRescueVehicle> routeNamePage(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_RESCUE_VEHICLE_ROUTENAME_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoRescueVehicle findById(Long id) {
        Optional<MoRescueVehicle> moRescueVehicle = rescueVehicleRepository.findById(id);
        if (moRescueVehicle.isPresent()) {
            return moRescueVehicle.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoRescueVehicle deleteById(Long id) {
        MoRescueVehicle moRescueVehicle = this.findById(id);
        moRescueVehicle.setDeleted(DELETE_TRUE);
        return moRescueVehicle;
    }

    public void audit(Long id, int status, String reason) {
        MoRescueVehicle moRescueVehicle = this.findById(id);
        moRescueVehicle.setStatus(status);
        moRescueVehicle.setReason(reason);
    }

    public void saveAll(List<MoRescueVehicle> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        rescueVehicleRepository.saveAll(list);
    }

    @Transactional(readOnly = true)
    public boolean existsByCode(String name, Long id) {
        if (id == null) {
            return rescueVehicleRepository.countByCodeAndDeleted(name, DELETE_FALSE) > 0;
        }
        return rescueVehicleRepository.countByCodeAndIdNotAndDeleted(name, id, DELETE_FALSE) > 0;
    }
}
