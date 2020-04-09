package com.bms.industry.dao;

import com.bms.entity.BusVehicle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 公交车辆管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface VehicleRepository extends PagingAndSortingRepository<BusVehicle, Long> {
    BusVehicle findByLicNo(String licNo);

    int countByLicNoAndDeleted(String licNo, int deleted);

    int countByLicNoAndIdNotAndDeleted(String licNo, Long id, int deleted);

    int countByCodeAndDeleted(String code, int deleted);

    int countByCodeAndIdNotAndDeleted(String code, Long id, int deleted);
}
