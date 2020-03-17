package com.bms.sys.dao;

import com.bms.entity.Dictionary;
import com.bms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 字典管理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Repository
public interface DictRepository extends PagingAndSortingRepository<Dictionary, Long> {

    Optional<Dictionary> findByCodeOrderByIndexAsc(String code);

    List<Dictionary> findByParentIsNullOrderByIndexAsc();
}
