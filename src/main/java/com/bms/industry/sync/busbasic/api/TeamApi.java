package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.BusTeam;
import com.bms.entity.Organization;
import com.bms.industry.service.BusTeamService;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusBasicResult;
import com.bms.industry.sync.busbasic.view.CompanyApiView;
import com.bms.industry.sync.busbasic.view.TeamApiView;
import com.bms.sys.service.OrganizationService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 公司.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class TeamApi {
    private static final Logger logger = LoggerFactory.getLogger(TeamApi.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final BusTeamService busTeamService;
    private final OrganizationService organizationService;

    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/carTeam/getAll";


        BusBasicResult result = http.getObject(url, null);
        logger.debug("carTeam getAll:{}", result);
        if (result.getResult() == null) {
            logger.info("获取车队接口结果为空");
            return;
        }
        List<TeamApiView> list = JSON.parseObject(JSON.toJSONString(result.getResult()), new TypeReference<List<TeamApiView>>() {
        });
        if (list == null || list.isEmpty()) {
            logger.info("获取车队接口结果为空");
            return;
        }
        list.stream().forEach(o -> {
            BusTeam team = busTeamService.findByOId(o.getOId());
            if (team != null) {
                BeanUtils.copyProperties(o, team);
                if (StringUtils.isNotBlank(o.getOOrgId())) {
                    Organization parent = organizationService.findByOId(o.getOOrgId());
                    team.setOrgId(parent != null ? parent.getId() : null);
                }
                busTeamService.updateById(team.getId(), team);
            } else {
                team = new BusTeam();
                BeanUtils.copyProperties(o, team);
                if (StringUtils.isNotBlank(o.getOOrgId())) {
                    Organization parent = organizationService.findByOId(o.getOOrgId());
                    team.setOrgId(parent != null ? parent.getId() : null);
                }
                team.setStatus(Organization.STATUS_PASS_AUDIT);
                busTeamService.insert(team);
            }
        });
    }

}
