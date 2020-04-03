package com.bms.industry.dao;

import com.bms.entity.Notice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 行政管理
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@Repository
public interface NoticeRepository extends PagingAndSortingRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {
    int countByTitleAndDeleted(String title, int deleted);

    int countByTitleAndIdNotAndDeleted(String title, Long id, int deleted);
}
