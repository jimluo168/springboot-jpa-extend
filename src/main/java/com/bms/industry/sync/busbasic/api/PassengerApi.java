package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.*;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.BusTeamService;
import com.bms.industry.service.PractitionerService;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusApiResult;
import com.bms.industry.sync.busbasic.view.BusApiView;
import com.bms.industry.sync.busbasic.view.PassengerApiView;
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
 * 司机 从业人员.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class PassengerApi extends AbstractApi {
    private static final Logger logger = LoggerFactory.getLogger(PassengerApi.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final LoginApi loginApi;
    private final PractitionerService practitionerService;
    private final OrganizationService organizationService;
    private final BusRouteService busRouteService;
    private final BusTeamService busTeamService;

    public void getAll() throws IOException {
        login();

        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/corePassenger/getAll";

        BusApiResult result = http.getObject(url, null);
        logger.debug("coreBus getAll:{}", result);
        if (result.getResult() == null) {
            logger.info("获取车辆接口结果为空");
            return;
        }
        List<PassengerApiView> list = JSON.parseObject(JSON.toJSONString(result.getResult()), new TypeReference<List<PassengerApiView>>() {
        });
        if (list == null || list.isEmpty()) {
            logger.info("获取车辆接口结果为空");
            return;
        }
        list.stream().forEach(o -> {
            Practitioner pract = practitionerService.findByOId(o.getOId());
            if (pract != null) {
                BeanUtils.copyProperties(o, pract);
                if (StringUtils.isNotBlank(o.getORouteId())) {
                    BusRoute route = busRouteService.findByOId(o.getORouteId());
                    if (route != null) {
                        pract.setBusRoute(route);
                        pract.setOrganization(route.getOrganization());
                        pract.setCarTeam(route.getCarTeam());
                    }
                }
                if (StringUtils.isNotBlank(o.getOOrgId())) {
                    Organization org = organizationService.findByOId(o.getOOrgId());
                    if (org != null) {
                        pract.setOrganization(org);
                    }
                }
                if (StringUtils.isNotBlank(o.getOTeamId())) {
                    BusTeam team = busTeamService.findByOId(o.getOTeamId());
                    if (team != null) {
                        pract.setCarTeam(team);
                    }
                }
                pract.setStatus(BusSite.STATUS_PASS_AUDIT);
                practitionerService.updateById(pract.getId(), pract);
            } else {
                pract = new Practitioner();
                BeanUtils.copyProperties(o, pract);
                if (StringUtils.isNotBlank(o.getORouteId())) {
                    BusRoute route = busRouteService.findByOId(o.getORouteId());
                    if (route != null) {
                        pract.setBusRoute(route);
                        pract.setOrganization(route.getOrganization());
                        pract.setCarTeam(route.getCarTeam());
                    }
                }
                if (StringUtils.isNotBlank(o.getOOrgId())) {
                    Organization org = organizationService.findByOId(o.getOOrgId());
                    if (org != null) {
                        pract.setOrganization(org);
                    }
                }
                if (StringUtils.isNotBlank(o.getOTeamId())) {
                    BusTeam team = busTeamService.findByOId(o.getOTeamId());
                    if (team != null) {
                        pract.setCarTeam(team);
                    }
                }
                pract.setStatus(BusSite.STATUS_PASS_AUDIT);
                practitionerService.insert(pract);
            }
        });
    }

    @Override
    public Http getHttp() {
        return http;
    }

    @Override
    public LoginApi getLoginApi() {
        return loginApi;
    }
}
