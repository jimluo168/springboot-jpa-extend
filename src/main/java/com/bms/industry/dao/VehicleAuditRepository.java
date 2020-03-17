package com.bms.industry.dao;

import com.bms.entity.Vehicle;
import com.bms.entity.VehicleAudit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 公交车辆管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface VehicleAuditRepository extends PagingAndSortingRepository<VehicleAudit, Long> {
}
