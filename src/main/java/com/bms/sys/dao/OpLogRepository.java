package com.bms.sys.dao;

import com.bms.entity.OperationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpLogRepository extends CrudRepository<OperationLog, Long> {
}
