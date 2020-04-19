package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.entity.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Menu DAO.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Repository
public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {

    List<Menu> findByDeletedAndParentIsNullOrderByIndexAsc(int deleted);

    List<Menu> findByDeletedAndParentIdOrderByIndexAsc(int deleted, Long parentId);
}
