package com.springboot.jpa.demo.sys.dao;

import com.springboot.jpa.demo.entity.OperationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpLogRepository extends CrudRepository<OperationLog, Long> {
}
