package com.bms.monitor.dao;

import com.bms.entity.MoRescueMaterial;
import com.bms.entity.MoRescueVehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 救援资源管理-人员.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Repository
public interface RescueMaterialRepository extends PagingAndSortingRepository<MoRescueMaterial, Long> {
    int countByCodeAndDeleted(String name, int deleted);

    int countByCodeAndIdNotAndDeleted(String name, Long id, int deleted);
}
