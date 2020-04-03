package com.bms.industry.dao;

import com.bms.entity.BusTerminal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 场站 DAO
 *
 * @author zouyongcan
 * @date 2020/3/17
 */
@Repository
public interface BusTerminalRepository extends PagingAndSortingRepository<BusTerminal, Long>, JpaSpecificationExecutor<BusTerminal> {
    List<BusTerminal> findByName(String name);

    int countByName(String name);

    int countByNameAndIdNot(String name, Long id);
}
