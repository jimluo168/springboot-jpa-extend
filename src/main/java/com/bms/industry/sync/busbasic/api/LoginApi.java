package com.bms.industry.sync.busbasic.api;

import com.bms.common.util.JSON;
import com.bms.industry.sync.Http;
import com.bms.industry.sync.SyncProperties;
import com.bms.industry.sync.busbasic.view.BusApiResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录.
 *
 * @author luojimeng
 * @date 2020/4/4
 */
@Component
@RequiredArgsConstructor
public class LoginApi {
    private static final Logger logger = LoggerFactory.getLogger(LoginApi.class);

    private final SyncProperties syncProperties;
    private final Http http;

    public String login() throws IOException {
        String baseUrl = syncProperties.getBus().getBase();
        String url = baseUrl + "/bus/login";

        Map<String, Object> params = new HashMap<>();
        params.put("username", syncProperties.getBus().getUsername());
        params.put("password", syncProperties.getBus().getPassword());

        BusApiResult result = http.getObject(url, params);
        return result.getResult().toString();
    }
}
