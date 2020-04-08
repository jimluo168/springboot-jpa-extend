package com.bms.monitor.service;

import com.bms.Constant;
import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.MoInfoRelease;
import com.bms.monitor.dao.InfoReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 应急信息发布
 *
 * @author zouyongcan
 * @date 2020/4/8
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class InfoReleaseService {
    private final InfoReleaseRepository infoReleaseRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public MoInfoRelease insert(MoInfoRelease moInfoRelease) {
        moInfoRelease.setId(flakeId.next());
        infoReleaseRepository.save(moInfoRelease);
        return moInfoRelease;
    }

    public MoInfoRelease updateById(Long id, MoInfoRelease moInfoRelease) {
        MoInfoRelease value = this.findById(id);
        JpaUtils.copyNotNullProperties(moInfoRelease, value);
        return value;
    }

    @Transactional(readOnly = true)
    public PageList<MoInfoRelease> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_MO_INFO_RELEASE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public MoInfoRelease findById(Long id) {
        Optional<MoInfoRelease> infoRelease = infoReleaseRepository.findById(id);
        if (infoRelease.isPresent()) {
            return infoRelease.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public MoInfoRelease deleteById(Long id) {
        MoInfoRelease moInfoRelease = this.findById(id);
        moInfoRelease.setDeleted(DELETE_TRUE);
        return moInfoRelease;
    }
}
