package com.bms.industry.dao;

import com.bms.entity.BusRoute;
import com.bms.entity.BusRouteAudit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 公交路线管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface BusRouteAuditRepository extends PagingAndSortingRepository<BusRouteAudit, Long> {
}
