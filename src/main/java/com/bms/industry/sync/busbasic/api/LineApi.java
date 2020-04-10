package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.BusRoute;
import com.bms.entity.BusTeam;
import com.bms.entity.Organization;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.BusTeamService;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusBasicResult;
import com.bms.industry.sync.busbasic.view.CompanyApiView;
import com.bms.industry.sync.busbasic.view.LineApiView;
import com.bms.sys.service.OrganizationService;
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
 * 线路.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class LineApi {
    private static final Logger logger = LoggerFactory.getLogger(LineApi.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final BusRouteService busRouteService;
    private final OrganizationService organizationService;
    private final BusTeamService busTeamService;

    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/coreLine/getAll";

        BusBasicResult result = http.getObject(url, null);
        logger.debug("line getAll:{}", result);
        if (result.getResult() == null) {
            logger.info("获取线路接口结果为空");
            return;
        }
        List<LineApiView> list = JSON.parseObject(JSON.toJSONString(result.getResult()), new TypeReference<List<LineApiView>>() {
        });
        if (list == null || list.isEmpty()) {
            logger.info("获取线路接口结果为空");
            return;
        }
        list.stream().forEach(o -> {
            BusRoute route = busRouteService.findByOId(o.getOId());
            if (route != null) {
                BeanUtils.copyProperties(o, route);
                if (StringUtils.isNotBlank(o.getOTeamId())) {
                    BusTeam team = busTeamService.findByOId(o.getOTeamId());
                    if (team != null) {
                        route.setCarTeam(team);
                        route.setOOrgId(team.getOOrgId());
                        if (team.getOrgId() != null) {
                            Organization org = new Organization();
                            org.setId(team.getOrgId());
                            route.setOrganization(org);
                        }
                    }
                }
                route.setStatus(o.getStatus() == 0 ? BusRoute.STATUS_PASS_AUDIT : BusRoute.STATUS_UN_AUDIT);
                busRouteService.updateById(route.getId(), route);
            } else {
                route = new BusRoute();
                BeanUtils.copyProperties(o, route);
                if (StringUtils.isNotBlank(o.getOTeamId())) {
                    BusTeam team = busTeamService.findByOId(o.getOTeamId());
                    if (team != null) {
                        route.setCarTeam(team);
                        route.setOOrgId(team.getOOrgId());
                        if (team.getOrgId() != null) {
                            Organization org = new Organization();
                            org.setId(team.getOrgId());
                            route.setOrganization(org);
                        }
                    }
                }
                route.setStatus(o.getStatus() == 0 ? BusRoute.STATUS_PASS_AUDIT : BusRoute.STATUS_UN_AUDIT);
                busRouteService.insert(route);
            }
        });
    }

}
