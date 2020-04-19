package com.springboot.jpa.demo.sys.service;

import com.springboot.jpa.demo.core.config.flake.FlakeId;
import com.springboot.jpa.demo.core.domain.PageList;
import com.springboot.jpa.demo.core.domain.PageRequest;
import com.springboot.jpa.demo.entity.OperationLog;
import com.springboot.jpa.demo.sys.dao.OpLogMapper;
import com.springboot.jpa.demo.sys.dao.OpLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 日志管理-service.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class OpLogService {

    private final OpLogRepository opLogRepository;
    private final FlakeId flakeId;
    private final OpLogMapper opLogMapper;

    @Transactional(readOnly = true)
    public PageList<OperationLog> page(PageRequest pageRequest, OperationLog operationLog) {
//        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_OPLOG_PAGE, params));
        return opLogMapper.findAll(pageRequest, operationLog);
    }

    public void insert(OperationLog log) {
        log.setId(flakeId.next());
        opLogRepository.save(log);
    }
}
