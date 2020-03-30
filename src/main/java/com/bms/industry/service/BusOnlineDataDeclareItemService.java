package com.bms.industry.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.BusOnlineDataDeclareItem;
import com.bms.industry.dao.BusOnlineDataDeclareItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申报明细
 *
 * @author zouyongcan
 * @date 2020/3/30
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class BusOnlineDataDeclareItemService {

    private final BusOnlineDataDeclareItemRepository declareItemRepository;

    private final FlakeId flakeId;

    public void saveAll(List<BusOnlineDataDeclareItem> list) {
        list.stream().forEach(o -> {
            o.setId(flakeId.next());
        });
        declareItemRepository.saveAll(list);
    }
}
