package com.bms.monitor.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.MoOffSiteData;
import com.bms.monitor.dao.MoOffSiteDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * C8 离开站点数据.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
@Service
//@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class MoOffSiteDataService {

    private final FlakeId flakeId;
    private final MoOffSiteDataRepository moOffSiteDataRepository;

    public MoOffSiteData insert(MoOffSiteData data) {
        data.setId(flakeId.next());
        return moOffSiteDataRepository.save(data);
    }


}
