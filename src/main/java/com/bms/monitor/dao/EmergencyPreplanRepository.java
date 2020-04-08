package com.bms.monitor.dao;

import com.bms.entity.MoEmergencyPreplan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 应急预案管理
 *
 * @author zouyongcan
 * @date 2020/4/8
 */
@Repository
public interface EmergencyPreplanRepository extends PagingAndSortingRepository<MoEmergencyPreplan, Long> {
}
