package com.bms.sys.dao;

import com.bms.entity.OperationLog;
import com.bms.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpLogRepository extends CrudRepository<OperationLog, Long> {
}
