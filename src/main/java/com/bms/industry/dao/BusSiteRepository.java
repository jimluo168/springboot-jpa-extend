package com.bms.industry.dao;

import com.bms.entity.BusRoute;
import com.bms.entity.BusSite;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 站点 repository.
 *
 * @author zouyongcan
 * @date 2020/3/18
 */
@Repository
public interface BusSiteRepository extends PagingAndSortingRepository<BusSite, Long>, JpaSpecificationExecutor<BusSite> {

    int countByRouteAndNameAndUpDownAndDeleted(BusRoute route, String name, Integer upDown, int deleted);

    int countByRouteAndNameAndUpDownAndIdNotAndDeleted(BusRoute route, String name, Integer upDown, Long id, int deleted);

    BusSite findByoId(Integer oId);
}
