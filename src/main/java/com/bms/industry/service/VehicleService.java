package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Vehicle;
import com.bms.entity.VehicleAudit;
import com.bms.industry.dao.VehicleAuditRepository;
import com.bms.industry.dao.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Vehicle insert(Vehicle vehicle) {
        vehicle.setId(flakeId.next());
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle updateById(Long id, Vehicle vehicle) {
        Vehicle value = this.findById(id);
        JpaUtils.copyNotNullProperties(vehicle, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<Vehicle> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_VEHICLE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Vehicle findById(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isPresent()) {
            return vehicle.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Vehicle deleteById(Long id) {
        Vehicle vehicle = this.findById(id);
        vehicle.setDeleted(DELETE_TRUE);
        return vehicle;
    }

    public void audit(Long id, int status, String reason) {
        Vehicle vehicle = this.findById(id);
        vehicle.setStatus(status);
        vehicle.setReason(reason);

        VehicleAudit audit = new VehicleAudit();
        audit.setId(flakeId.next());
        audit.setVehicle(vehicle);
        audit.setReason(reason);
        vehicleAuditRepository.save(audit);
    }

    public void saveAll(List<Vehicle> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        vehicleRepository.saveAll(list);
    }
}
