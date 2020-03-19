package com.bms.industry.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Suggest;
import com.bms.entity.SuggestAudit;
import com.bms.industry.dao.SuggestAuditRepository;
import com.bms.industry.dao.SuggestRepository;
import com.bms.sys.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 投诉建议管理service
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class SuggestService {
    private final SuggestRepository suggestRepository;
    private final SuggestAuditRepository suggestAuditRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public Suggest insert(Suggest suggest) {
        suggest.setId(flakeId.next());
        suggestRepository.save(suggest);
        return suggest;
    }

    public Suggest updateById(Long id, Suggest suggest) {
        Suggest value = this.findById(id);
        JpaUtils.copyNotNullProperties(suggest, value);
        return value;
    }

    public PageList<Suggest> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_SUGGEST_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Suggest findById(Long id) {
        Optional<Suggest> suggest = suggestRepository.findById(id);
        if (suggest.isPresent()) {
            return suggest.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Suggest deleteById(Long id) {
        Suggest suggest = this.findById(id);
        suggest.setDeleted(DELETE_TRUE);
        return suggest;
    }

    public void audit(Long id, int status, String reason) {
        Suggest suggest = this.findById(id);
        suggest.setStatus(status);
        suggest.setReason(reason);

        SuggestAudit audit = new SuggestAudit();
        audit.setId(flakeId.next());
        audit.setSuggest(suggest);
        audit.setReason(reason);
        suggestAuditRepository.save(audit);
    }
}
