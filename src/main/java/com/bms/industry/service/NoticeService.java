package com.bms.industry.service;

import com.bms.ErrorCodes;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.domain.PageList;
import com.bms.common.domain.PageRequest;
import com.bms.common.util.JpaUtils;
import com.bms.entity.Notice;
import com.bms.industry.dao.NoticeRepository;
import com.bms.sys.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.bms.common.domain.BaseEntity.DELETE_TRUE;

/**
 * 行政管理service
 *
 * @author zouyongcan
 * @date 2020/3/19
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    public Notice insert(Notice notice) {
        notice.setId(flakeId.next());
        noticeRepository.save(notice);
        return notice;
    }

    public Notice updateById(Long id, Notice notice) {
        Notice value = this.findById(id);
        JpaUtils.copyNotNullProperties(notice, value);
        return value;
    }

    public PageList<Notice> page(PageRequest pageRequest, Map<String, Object> queryParams) {
        return hibernateDao.findAll(pageRequest, new DaoCmd(Constant.MAPPER_NOTICE_PAGE, queryParams));
    }

    @Transactional(readOnly = true)
    public Notice findById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        }
        throw ErrorCodes.build(ErrorCodes.DATA_NOT_EXIST);
    }

    public Notice deleteById(Long id) {
        Notice notice = this.findById(id);
        notice.setDeleted(DELETE_TRUE);
        return notice;
    }
}
