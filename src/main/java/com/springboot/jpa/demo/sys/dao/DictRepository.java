package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.entity.Dictionary;
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
    /**
     * 根据字典编码获取该条数据记录.
     *
     * @param code 字典编码
     * @return Dictionary
     */
    Optional<Dictionary> findByCodeOrderByIndexAsc(String code);

    List<Dictionary> findByParentIsNullOrderByIndexAsc();
}
