package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoExpert;
import com.bms.monitor.dao.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 专家库管理
 *
 * @author zouyongcan
 * @date 2020/4/7
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoExpert insert(MoExpert moExpert) {
        moExpert.setId(flakeId.next());
        expertRepository.save(moExpert);
        return moExpert;
    }

    public MoExpert updateById(Long id, MoExpert moExpert) {
        MoExpert value = this.findById(id);
        JpaUtils.copyNotNullProperties(moExpert, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoExpert> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_EXPERT_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoExpert findById(Long id) {
        Optional<MoExpert> expert = expertRepository.findById(id);
        if (expert.isPresent()) {
            return expert.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoExpert deleteById(Long id) {
        MoExpert moExpert = this.findById(id);
        moExpert.setDeleted(DELETE_TRUE);
        return moExpert;
    }
}
