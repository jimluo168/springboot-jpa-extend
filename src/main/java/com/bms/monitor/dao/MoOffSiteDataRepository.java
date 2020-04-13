package com.bms.monitor.dao;

import com.bms.entity.MoBusVehicleGpsData;
import com.bms.entity.MoOffSiteData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * C8 离开站点数据.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
@Repository
public interface MoOffSiteDataRepository extends CrudRepository<MoOffSiteData, Long> {
}
