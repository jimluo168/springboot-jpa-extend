package com.bms.industry.sync.busbasic;

import com.bms.common.util.JSON;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
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

    public void getAll() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/coreCompany/getAll";

        String result = http.get(url, null);
        Map<String, Object> json = JSON.parseObject(result, HashMap.class);
        logger.debug("company getAll:{}", json);
    }

}
