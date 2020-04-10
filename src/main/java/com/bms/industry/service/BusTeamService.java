package com.bms.industry.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusRoute;
import com.bms.entity.BusTeam;
import com.bms.entity.Organization;
import com.bms.industry.dao.BusTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public List<BusTeam> findByName(String name) {
        return busTeamRepository.findByName(name);
    }

    public BusTeam insert(BusTeam team) {
        team.setId(flakeId.next());
        return busTeamRepository.save(team);
    }

    public BusTeam updateById(Long id, BusTeam team) {
        BusTeam value = this.findById(id);
        JpaUtils.copyNotNullProperties(team, value);
        return value;
    }

    @Transactional(readOnly = true)
    public BusTeam findById(Long id) {
        Optional<BusTeam> team = busTeamRepository.findById(id);
        if (team.isPresent()) {
            return team.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    @Transactional(readOnly = true)
    public BusTeam findByOId(String oId) {
        return busTeamRepository.findByoId(oId);
    }

    @Transactional(readOnly = true)
    public List<BusTeam> findByOOrgId(String oOrgId) {
        return busTeamRepository.findByoOrgId(oOrgId);
    }
}
