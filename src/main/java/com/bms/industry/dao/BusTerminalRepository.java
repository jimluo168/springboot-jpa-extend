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

    int countByNameAndDeleted(String name, int deleted);

    int countByNameAndIdNotAndDeleted(String name, Long id, int deleted);

    int countByCodeAndDeleted(String code, int deleted);

    int countByCodeAndIdNotAndDeleted(String code, Long id, int deleted);
}
