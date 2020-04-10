package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.entity.Organization;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusBasicResult;
import com.bms.industry.sync.busbasic.view.CompanyView;
import com.bms.sys.service.OrganizationService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/coreCompany/getAll";

        BusBasicResult result = http.getObject(url, null);
        logger.debug("company getAll:{}", result);
        List<CompanyView> list = JSON.parseObject(result.getResult(), new TypeReference<List<CompanyView>>() {
        });
        list.stream().forEach(o -> {
            Organization org = organizationService.findByOId(o.getOId());
            if (org != null) {
                BeanUtils.copyProperties(o, org);
                organizationService.updateById(org.getId(), org);
            } else {
                Organization target = new Organization();
                BeanUtils.copyProperties(o, target);
                target.setStatus(Organization.STATUS_PASS_AUDIT);
                organizationService.insert(target);
            }
        });
    }

}
