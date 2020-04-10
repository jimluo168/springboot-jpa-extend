package com.bms.industry.sync.busbasic.api;

import com.bms.industry.sync.Http;

import java.io.IOException;

/**
 * 基础API.
 *
 * @author luojimeng
 * @date 2020/4/10
 */
public abstract class AbstractApi {

    public void login() throws IOException {
        getHttp().setHeader("Content-Type", "application/x-www-form-urlencoded");
        String accessToken = getLoginApi().login();
        getHttp().setHeader("accessToken", accessToken);
    }

    public abstract Http getHttp();

    public abstract LoginApi getLoginApi();
}
