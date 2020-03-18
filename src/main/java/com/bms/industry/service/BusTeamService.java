package com.bms.industry.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.entity.BusRoute;
import com.bms.entity.BusTeam;
import com.bms.industry.dao.BusTeamRepository;
import com.bms.sys.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 公交车队管理.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusTeamService {
    private final BusTeamRepository busTeamRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    @Transactional(readOnly = true)
    public PageList<BusTeam> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_BUS_CAR_TEAM_PAGE, queryParams));
    }
}
