package com.bms.sys.service;

import com.bms.Constant;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.entity.OperationLog;
import com.bms.sys.dao.OpLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


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
    private final HibernateDao hibernateDao;
    private final FlakeId flakeId;

    @Transactional(readOnly = true)
    public PageList<OperationLog> page(PageRequest pageRequest, Map<String, Object> params) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_OPLOG_PAGE, params));
    }

    public void insert(OperationLog log) {
        log.setId(flakeId.next());
        opLogRepository.save(log);
    }
}
