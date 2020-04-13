package com.bms.monitor.dao;

import com.bms.entity.MoBusVehicleGpsData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * C2 定位数据.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
@Repository
public interface MoBusVehicleGpsDataRepository extends CrudRepository<MoBusVehicleGpsData, Long> {
}
