package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.BusRoute;
import com.bms.entity.BusSite;
import com.bms.entity.BusVehicle;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.VehicleService;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusApiResult;
import com.bms.industry.sync.busbasic.view.BusApiView;
import com.bms.industry.sync.busbasic.view.StationApiView;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点信息.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class BusApi {
    private static final Logger logger = LoggerFactory.getLogger(BusApi.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final VehicleService vehicleService;
    private final BusRouteService busRouteService;


    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/coreBus/getAll";

        BusApiResult result = http.getObject(url, null);
        logger.debug("coreBus getAll:{}", result);
        if (result.getResult() == null) {
            logger.info("获取车辆接口结果为空");
            return;
        }
        List<BusApiView> list = JSON.parseObject(JSON.toJSONString(result.getResult()), new TypeReference<List<BusApiView>>() {
        });
        if (list == null || list.isEmpty()) {
            logger.info("获取车辆接口结果为空");
            return;
        }
        list.stream().forEach(o -> {
            BusVehicle vehicle = vehicleService.findByCode(o.getCode());
            if (vehicle != null) {
                BeanUtils.copyProperties(o, vehicle);
                if (StringUtils.isNotBlank(o.getORouteId())) {
                    BusRoute route = busRouteService.findByOId(o.getORouteId());
                    if (route != null) {
                        vehicle.setBusRoute(route);
                        vehicle.setOrganization(route.getOrganization());
                        vehicle.setCarTeam(route.getCarTeam());
                    }
                }
                vehicle.setStatus(BusSite.STATUS_PASS_AUDIT);
                vehicle.setDeleted(StringUtils.equals(o.getDeleteSatus(), "1") ? BusVehicle.DELETE_FALSE : BusVehicle.DELETE_TRUE);
                vehicleService.updateById(vehicle.getId(), vehicle);
            } else {
                vehicle = new BusVehicle();
                BeanUtils.copyProperties(o, vehicle);
                if (StringUtils.isNotBlank(o.getORouteId())) {
                    BusRoute route = busRouteService.findByOId(o.getORouteId());
                    if (route != null) {
                        vehicle.setBusRoute(route);
                        vehicle.setOrganization(route.getOrganization());
                        vehicle.setCarTeam(route.getCarTeam());
                    }
                }
                vehicle.setStatus(BusSite.STATUS_PASS_AUDIT);
                vehicle.setDeleted(StringUtils.equals(o.getDeleteSatus(), "1") ? BusVehicle.DELETE_FALSE : BusVehicle.DELETE_TRUE);
                vehicleService.insert(vehicle);
            }
        });
    }

}
