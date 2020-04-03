package com.bms.industry.dao;

import com.bms.entity.BusRoute;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 公交路线管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface BusRouteRepository extends PagingAndSortingRepository<BusRoute, Long> {
    List<BusRoute> findByName(String name);

    int countByNameAndTypeAndDeleted(String name, Integer type, int deleted);

    int countByNameAndTypeAndIdNotAndDeleted(String name, Integer type, Long id, int deleted);
}
