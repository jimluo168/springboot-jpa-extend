package com.bms.industry.dao;

import com.bms.entity.BusTeam;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 公交车队管理.
 *
 * @author luojimeng
 * @date 2020/3/17
 */
@Repository
public interface BusTeamRepository extends PagingAndSortingRepository<BusTeam, Long> {
}
