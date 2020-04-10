package com.bms.industry.dao;

import com.bms.entity.BusViolation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 违规信息管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface BusViolationRepository extends PagingAndSortingRepository<BusViolation, Long> {
}
