package com.bms.sys.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.entity.OperationLog;
import com.bms.entity.User;
import com.bms.sys.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 日志管理-service.
 * @author luojimeng
 * @date 2020/3/9
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class OpLogService {

    private final HibernateDao hibernateDao;

    public PageList<User> page(PageRequest pageRequest, OperationLog log) {
        Map<String, Object> params = new HashMap<>();
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_OPLOG_PAGE, params));
    }
}
