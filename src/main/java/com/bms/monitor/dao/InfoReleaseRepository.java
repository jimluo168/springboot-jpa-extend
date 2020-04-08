package com.bms.monitor.dao;

import com.bms.entity.MoInfoRelease;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 应急信息发布
 *
 * @author zouyongcan
 * @date 2020/4/8
 */
@Repository
public interface InfoReleaseRepository extends PagingAndSortingRepository<MoInfoRelease, Long> {
}
