package com.bms.monitor.dao;

import com.bms.entity.Expert;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 专家库管理
 *
 * @author zouyongcan
 * @date 2020/4/7
 */
@Repository
public interface ExpertRepository extends PagingAndSortingRepository<Expert, Long> {
}
