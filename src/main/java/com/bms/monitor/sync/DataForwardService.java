package com.bms.monitor.sync;

import com.bms.Constant;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.util.JSON;
import com.bms.monitor.view.BusRouteNameAndSiteNameView;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据转发业务类.
 *
 * @author luojimeng
 * @date 2020/4/13
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class DataForwardService {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardService.class);

    private final FlakeId flakeId;
    private final HibernateDao hibernateDao;

    @Transactional(readOnly = true)
    public BusRouteNameAndSiteNameView findBusRouteNameAndSiteNameByRouteOIdAndSiteIndex(String routeOId, Integer siteIndex) {
        Map<String, Object> params = new HashMap<>();
        params.put("routeOId", routeOId);
        params.put("siteIndex", siteIndex);
        return hibernateDao.getSingle(new DaoCmd(Constant.MAPPER_MO_DATA_FORWARD_FIND_BUS_ROUTE_SITE_BY_ROUTEID_AND_SITEINDEX, params, BusRouteNameAndSiteNameView.class));
    }

    /**
     * 根据车辆编号更新车辆运行状态 是否在线.
     *
     * @param code     车辆编号
     * @param isMove   是否运行
     * @param isOnline 是否在线
     */
    public int updateBusVehicleByCode(String code, Integer isMove, Integer isOnline,
                                      Integer currentSiteIndex, String currentSiteName, Float speed,
                                      Integer nextSiteIndex, String nextSiteName,
                                      String practOId) {
        Map<String, Object> params = new HashMap<>();
        params.put("move", isMove);
        params.put("online", isOnline);
        params.put("currentSiteIndex", currentSiteIndex);
        params.put("currentSiteName", currentSiteName);
        params.put("speed", speed);
        params.put("nextSiteIndex", nextSiteIndex);
        params.put("nextSiteName", nextSiteName);
        params.put("practOId", practOId);
        params.put("code", code);
        return hibernateDao.executeUpdate(new DaoCmd(Constant.MAPPER_MO_DATA_FORWARD_UPDATE_BUS_VEHICLE_BY_CODE, params));
    }
}
