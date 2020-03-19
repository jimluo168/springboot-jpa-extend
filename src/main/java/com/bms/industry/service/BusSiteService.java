package com.bms.industry.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.BusSite;
import com.bms.entity.BusSiteAudit;
import com.bms.industry.dao.BusSiteAuditRepository;
import com.bms.industry.dao.BusSiteRepository;
import com.bms.sys.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 公交站点管理
 *
 * @author zouyongcan
 * @date 2020/3/18
 */

@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusSiteService {

    private final BusSiteRepository busSiteRepository;
    private final BusSiteAuditRepository busSiteAuditRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public BusSite insert(BusSite busSite) {
        busSite.setId(flakeId.next());
        busSiteRepository.save(busSite);
        return busSite;
    }

    public BusSite updateById(Long id, BusSite busSite) {
        BusSite value = this.findById(id);
        JpaUtils.copyNotNullProperties(busSite, value);
        return value;
    }

    public PageList<BusSite> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_BUS_SITE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public BusSite findById(Long id) {
        Optional<BusSite> busSite = busSiteRepository.findById(id);
        if (busSite.isPresent()) {
            return busSite.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public BusSite deleteById(Long id) {
        BusSite busSite = this.findById(id);
        busSite.setDeleted(DELETE_TRUE);
        return busSite;
    }

    public void audit(Long id, int status, String reason) {
        BusSite busSite = this.findById(id);
        busSite.setStatus(status);
        busSite.setReason(reason);

        BusSiteAudit audit = new BusSiteAudit();
        audit.setId(flakeId.next());
        audit.setBusSite(busSite);
        audit.setReason(reason);
        busSiteAuditRepository.save(audit);
    }

    public void saveAll(List<BusSite> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        busSiteRepository.saveAll(list);
    }
}
