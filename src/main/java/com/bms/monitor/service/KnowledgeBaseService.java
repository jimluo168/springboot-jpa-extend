package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoKnowledgeBase;
import com.bms.monitor.dao.KnowledgeBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 专家知识库管理
 *
 * @author zouyongcan
 * @date 2020/4/3
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class KnowledgeBaseService {
    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoKnowledgeBase insert(MoKnowledgeBase moKnowledgeBase) {
        moKnowledgeBase.setId(flakeId.next());
        knowledgeBaseRepository.save(moKnowledgeBase);
        return moKnowledgeBase;
    }

    public MoKnowledgeBase updateById(Long id, MoKnowledgeBase moKnowledgeBase) {
        MoKnowledgeBase value = this.findById(id);
        JpaUtils.copyNotNullProperties(moKnowledgeBase, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoKnowledgeBase> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_KNOWLEDGE_BASE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoKnowledgeBase findById(Long id) {
        Optional<MoKnowledgeBase> knowledgeBase = knowledgeBaseRepository.findById(id);
        if (knowledgeBase.isPresent()) {
            return knowledgeBase.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoKnowledgeBase deleteById(Long id) {
        MoKnowledgeBase moKnowledgeBase = this.findById(id);
        moKnowledgeBase.setDeleted(DELETE_TRUE);
        return moKnowledgeBase;
    }
}
