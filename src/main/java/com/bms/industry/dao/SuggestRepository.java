package com.bms.industry.dao;

import com.bms.entity.Suggest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 投诉建议
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@Repository
public interface SuggestRepository extends PagingAndSortingRepository<Suggest, Long>, JpaSpecificationExecutor<Suggest> {
}
