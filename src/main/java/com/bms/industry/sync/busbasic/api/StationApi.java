package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.BusRoute;
import com.bms.entity.BusSite;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.BusSiteService;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusApiResult;
import com.bms.industry.sync.busbasic.view.StationApiView;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 站点信息.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class StationApi {
    private static final Logger logger = LoggerFactory.getLogger(StationApi.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final BusSiteService busSiteService;
    private final BusRouteService busRouteService;


    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/machineStation/getAll";

        BusApiResult result = http.getObject(url, null);
        logger.debug("machineStation getAll:{}", result);
        if (result.getResult() == null) {
            logger.info("获取站点接口结果为空");
            return;
        }
        List<StationApiView> list = JSON.parseObject(JSON.toJSONString(result.getResult()), new TypeReference<List<StationApiView>>() {
        });
        if (list == null || list.isEmpty()) {
            logger.info("获取站点接口结果为空");
            return;
        }
        list.stream().forEach(o -> {
            BusSite site = busSiteService.findByOId(o.getOId());
            if (site != null) {
                BeanUtils.copyProperties(o, site);
                if (StringUtils.isNotBlank(o.getORouteId())) {
                    BusRoute route = busRouteService.findByOId(o.getORouteId());
                    if (route != null) {
                        site.setRoute(route);
                    }
                }
                site.setStatus(BusSite.STATUS_PASS_AUDIT);
                busSiteService.updateById(site.getId(), site);
            } else {
                site = new BusSite();
                BeanUtils.copyProperties(o, site);
                if (StringUtils.isNotBlank(o.getORouteId())) {
                    BusRoute route = busRouteService.findByOId(o.getORouteId());
                    if (route != null) {
                        site.setRoute(route);
                    }
                }
                site.setStatus(BusSite.STATUS_PASS_AUDIT);
                busSiteService.insert(site);
            }
        });
    }

}
