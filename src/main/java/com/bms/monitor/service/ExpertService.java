package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Expert;
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

    public Expert insert(Expert expert) {
        expert.setId(flakeId.next());
        expertRepository.save(expert);
        return expert;
    }

    public Expert updateById(Long id, Expert expert) {
        Expert value = this.findById(id);
        JpaUtils.copyNotNullProperties(expert, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<Expert> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_EXPERT_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Expert findById(Long id) {
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isPresent()) {
            return expert.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Expert deleteById(Long id) {
        Expert expert = this.findById(id);
        expert.setDeleted(DELETE_TRUE);
        return expert;
    }
}
