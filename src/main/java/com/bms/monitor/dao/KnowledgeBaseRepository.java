package com.bms.monitor.dao;

import com.bms.entity.MoKnowledgeBase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 专家知识库管理
 *
 * @author zouyongcan
 * @date 2020/4/3
 */
@Repository
public interface KnowledgeBaseRepository extends PagingAndSortingRepository<MoKnowledgeBase, Long> {
}
