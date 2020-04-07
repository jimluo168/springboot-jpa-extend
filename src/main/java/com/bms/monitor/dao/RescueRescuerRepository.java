package com.bms.monitor.dao;

import com.bms.entity.KnowledgeBase;
import com.bms.entity.MoRescueRescuer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 救援资源管理-人员.
 *
 * @author luojimeng
 * @date 2020/4/7
 */
@Repository
public interface RescueRescuerRepository extends PagingAndSortingRepository<MoRescueRescuer, Long> {
    int countByNameAndDeleted(String name, int deleted);

    int countByNameAndIdNotAndDeleted(String name, Long id, int deleted);
}
