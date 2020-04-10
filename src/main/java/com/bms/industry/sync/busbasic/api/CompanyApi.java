package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.BusTeam;
import com.bms.entity.Organization;
import com.bms.industry.service.BusTeamService;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusApiResult;
import com.bms.industry.sync.busbasic.view.CompanyApiView;
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

/**
 * 公司.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class CompanyApi {
    private static final Logger logger = LoggerFactory.getLogger(CompanyApi.class);

    private final SyncProperties syncProperties;
    private final Http http;
    private final OrganizationService organizationService;
    private final BusTeamService busTeamService;

    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/coreCompany/getAll";

        BusApiResult result = http.getObject(url, null);
        logger.debug("company getAll:{}", result);
        if (result.getResult() == null) {
            logger.info("获取公司接口结果为空");
            return;
        }
        List<CompanyApiView> list = JSON.parseObject(JSON.toJSONString(result.getResult()), new TypeReference<List<CompanyApiView>>() {
        });
        if (list == null || list.isEmpty()) {
            logger.info("获取公司接口结果为空");
            return;
        }
        list.stream().forEach(o -> {
            Organization org = organizationService.findByOId(o.getOId());
            if (org != null) {
                BeanUtils.copyProperties(o, org);
                if (StringUtils.isNotBlank(o.getOParentId())) {
                    Organization parent = organizationService.findByOId(o.getOParentId());
                    org.setParent(parent);
                }
                List<BusTeam> busTeamList = busTeamService.findByOOrgId(o.getOId());
                org.setCarTeamList(busTeamList);
                organizationService.updateById(org.getId(), org);
            } else {
                org = new Organization();
                BeanUtils.copyProperties(o, org);
                if (StringUtils.isNotBlank(o.getOParentId())) {
                    Organization parent = organizationService.findByOId(o.getOParentId());
                    org.setParent(parent);
                }
                List<BusTeam> busTeamList = busTeamService.findByOOrgId(o.getOId());
                org.setCarTeamList(busTeamList);
                org.setStatus(Organization.STATUS_PASS_AUDIT);
                organizationService.insert(org);
            }
        });
    }

}
