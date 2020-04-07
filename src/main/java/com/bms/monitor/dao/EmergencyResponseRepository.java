package com.bms.monitor.dao;

import com.bms.entity.MoEmergencyResponse;
import com.bms.entity.MoRescueRescuer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 应急响应处理.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Repository
public interface EmergencyResponseRepository extends PagingAndSortingRepository<MoEmergencyResponse, Long> {
    int countByNameAndDeleted(String name, int deleted);

    int countByNameAndIdNotAndDeleted(String name, Long id, int deleted);
}
