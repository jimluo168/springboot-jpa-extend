package com.bms.industry.dao;

import com.bms.entity.BusVehicleAudit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 公交车辆管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface VehicleAuditRepository extends PagingAndSortingRepository<BusVehicleAudit, Long> {
}
