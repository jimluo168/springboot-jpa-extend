package com.bms.monitor.service;

import com.bms.common.config.flake.FlakeId;
import com.bms.entity.MoBusVehicleGpsData;
import com.bms.entity.MoOffSiteData;
import com.bms.monitor.dao.MoBusVehicleGpsDataRepository;
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
public class MoBusVehicleGpsDataService {

    private final FlakeId flakeId;
    private final MoBusVehicleGpsDataRepository moBusVehicleGpsDataRepository;

    public MoBusVehicleGpsData insert(MoBusVehicleGpsData data) {
        data.setId(flakeId.next());
        return moBusVehicleGpsDataRepository.save(data);
    }


}
