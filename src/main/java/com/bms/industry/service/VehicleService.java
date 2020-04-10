package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusVehicle;
import com.bms.entity.BusVehicleAudit;
import com.bms.industry.dao.VehicleAuditRepository;
import com.bms.industry.dao.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_FALSE;
import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 公交车辆管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final FlakeId flakeId;
    private final VehicleAuditRepository vehicleAuditRepository;
    private final HibernateDao hibernateDao;

    public BusVehicle insert(BusVehicle vehicle) {
        vehicle.setId(flakeId.next());
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public BusVehicle updateById(Long id, BusVehicle vehicle) {
        BusVehicle value = this.findById(id);
        JpaUtils.copyNotNullProperties(vehicle, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<BusVehicle> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_VEHICLE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusVehicle findById(Long id) {
        Optional<BusVehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            return vehicle.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusVehicle deleteById(Long id) {
        BusVehicle vehicle = this.findById(id);
        vehicle.setDeleted(DELETE_TRUE);
        return vehicle;
    }

    public void audit(Long id, int status, String reason) {
        BusVehicle vehicle = this.findById(id);
        vehicle.setStatus(status);
        vehicle.setReason(reason);

        BusVehicleAudit audit = new BusVehicleAudit();
        audit.setId(flakeId.next());
        audit.setVehicle(vehicle);
        audit.setReason(reason);
        vehicleAuditRepository.save(audit);
    }

    public void saveAll(List<BusVehicle> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        vehicleRepository.saveAll(list);
    }

    @Transactional(readOnly = true)
    public BusVehicle findByLicNo(String licNo) {
        return vehicleRepository.findByLicNo(licNo);
    }

    @Transactional(readOnly = true)
    public boolean existsByLicNo(String licNo, Long id) {
        if (id == null) {
            return vehicleRepository.countByLicNoAndDeleted(licNo, DELETE_FALSE) > 0;
        }
        return vehicleRepository.countByLicNoAndIdNotAndDeleted(licNo, id, DELETE_FALSE) > 0;
    }

    @Transactional(readOnly = true)
    public boolean existsByCode(String code, Long id) {
        if (id == null) {
            return vehicleRepository.countByCodeAndDeleted(code, DELETE_FALSE) > 0;
        }
        return vehicleRepository.countByCodeAndIdNotAndDeleted(code, id, DELETE_FALSE) > 0;
    }

    @Transactional(readOnly = true)
    public BusVehicle findByCode(String code) {
        return vehicleRepository.findByCode(code);
    }
}
