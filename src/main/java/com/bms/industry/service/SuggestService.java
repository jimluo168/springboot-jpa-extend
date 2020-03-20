package com.bms.industry.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.*;
import com.bms.entity.Suggest;
import com.bms.entity.SuggestAudit;
import com.bms.industry.dao.SuggestAuditRepository;
import com.bms.industry.dao.SuggestRepository;
import com.bms.Constant;
import com.bms.sys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    private final UserService userService;
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

    public void audit(Long id, int status, String reason, Long userId) {
        Suggest suggest = this.findById(id);
        User auditor = userService.findById(userId);
        Date auditTime = new Date();

        SuggestAudit audit = new SuggestAudit();
        BeanUtils.copyProperties(suggest, audit);
        audit.setId(flakeId.next());
        audit.setSuggest(suggest);
        suggestAuditRepository.save(audit);

        suggest.setStatus(status);
        suggest.setReason(reason);
        suggest.setAuditor(auditor);
        suggest.setAuditTime(auditTime);
    }
}
