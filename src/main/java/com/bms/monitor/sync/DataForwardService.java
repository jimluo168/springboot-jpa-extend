package com.bms.monitor.sync;

import com.bms.Constant;
import com.bms.common.config.flake.FlakeId;
import com.bms.common.config.redis.RedisClient;
import com.bms.common.dao.DaoCmd;
import com.bms.common.dao.HibernateDao;
import com.bms.common.util.BeanMapper;
import com.bms.common.util.JSON;
import com.bms.monitor.sync.view.MoBusSiteCache;
import com.bms.monitor.sync.view.MoBusVehicleCache;
import com.bms.monitor.sync.view.MoDataForwardCache;
import com.bms.monitor.view.BusRouteNameAndSiteNameView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据转发业务类.
 *
 * @author luojimeng
 * @date 2020/4/13
 */
@Service
@RequiredArgsConstructor
public class DataForwardService {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardService.class);

    private final RedisClient redisClient;
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
    @Transactional(rollbackFor = RuntimeException.class)
    public int updateBusVehicleByCode(MoDataForwardCache cache) {
        return hibernateDao.executeUpdate(new DaoCmd(Constant.MAPPER_MO_DATA_FORWARD_UPDATE_BUS_VEHICLE_BY_CODE, BeanMapper.toMap(cache)));
    }

    public MoDataForwardCache getMoDataForwardCacheByVehCode(String vehCode) {
        String key = String.format(MoDataForwardCache.CACHE_VEHICLE_KEY, vehCode);
        String json = redisClient.get(key);
        if (StringUtils.isNotBlank(json)) {
            return JSON.parseObject(json, MoDataForwardCache.class);
        }
        return null;
    }

    public void setMoDataForwardCacheByVehCode(String vehCode, MoDataForwardCache cache) {
        String key = String.format(MoDataForwardCache.CACHE_VEHICLE_KEY, vehCode);
        redisClient.setex(key, MoDataForwardCache.CACHE_VEHICLE_KEY_EXP_SECONDS, JSON.toJSONString(cache));
    }

    public Set<String> findCacheKeys() {
        return redisClient.keys(MoDataForwardCache.CACHE_VEHICLE_KEYS);
    }

    @Transactional(readOnly = true)
    public List<MoBusSiteCache> findAllBusSiteCache() {
        return hibernateDao.getList(new DaoCmd("mo_bus_site_and_bus_route_cache_info", null, MoBusSiteCache.class));
    }


    public MoBusSiteCache getMoBusSiteCacheByRouteOIdAndSiteIndex(String routeOId, Integer siteIndex) {
        String key = String.format(MoBusSiteCache.CACHE_BUSSITE_KEY, routeOId, siteIndex.toString());
        String json = redisClient.get(key);
        if (StringUtils.isNotBlank(json)) {
            return JSON.parseObject(json, MoBusSiteCache.class);
        }
        return null;
    }

    public void setMoBusSiteCacheByRouteOIdAndSiteIndex(String routeOId, Integer siteIndex, MoBusSiteCache cache) {
        String key = String.format(MoBusSiteCache.CACHE_BUSSITE_KEY, routeOId, siteIndex.toString());
        redisClient.setex(key, MoBusSiteCache.CACHE_BUSSITE_KEY_EXP_SECONDS, JSON.toJSONString(cache));
    }


    @Transactional(readOnly = true)
    public List<MoBusVehicleCache> findAllBusVehicleCache() {
        return hibernateDao.getList(new DaoCmd("mo_bus_vehicle_cache_info", null, MoBusVehicleCache.class));
    }

    public MoBusVehicleCache getMoBusVehicleCacheByVehCode(String vehCode) {
        String key = String.format(MoBusVehicleCache.CACHE_BUSVEHICLE_KEY, vehCode);
        String json = redisClient.get(key);
        if (StringUtils.isNotBlank(json)) {
            return JSON.parseObject(json, MoBusVehicleCache.class);
        }
        return null;
    }

    public void setMoBusVehicleCacheByVehCode(String vehCode, MoBusVehicleCache cache) {
        String key = String.format(MoBusVehicleCache.CACHE_BUSVEHICLE_KEY, vehCode);
        redisClient.setex(key, MoBusVehicleCache.CACHE_BUSVEHICLE_KEY_EXP_SECONDS, JSON.toJSONString(cache));
    }
}
