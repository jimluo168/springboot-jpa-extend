package com.bms.industry.sync;

import com.bms.common.util.JSON;
import com.bms.industry.sync.busbasic.view.BusBasicBaseView;
import com.bms.industry.sync.busbasic.view.BusBasicResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Http工具类.
 *
 * @author luojimeng
 * @date 2020/4/5
 */
@Component
public class Http {
    private static final Logger logger = LoggerFactory.getLogger(Http.class);

    private static Map<String, String> DEFAULT_HEADERS = new HashMap<>();

    static {
        DEFAULT_HEADERS.put("Charset", StandardCharsets.UTF_8.name());
    }

    public String get(String url, Map<String, Object> params) throws IOException {
        return get(url, params, null);
    }

    public BusBasicResult getObject(String url, Map<String, Object> params) throws IOException {
        return getObject(url, params, null);
    }

    public String get(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
        return request("GET", url, params, headers);
    }

    public BusBasicResult getObject(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
        String json = request("GET", url, params, headers);
        return JSON.parseObject(json, BusBasicResult.class);
    }

    public String post(String url, Map<String, Object> params) throws IOException {
        return post(url, params, null);
    }

    public String post(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
        return request("POST", url, params, headers);
    }

    private String request(String method, String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
        long start = System.currentTimeMillis();
        URL requestUtl = new URL(url);
        HttpURLConnection con = null;
        String result = null;
        try {
            con = (HttpURLConnection) requestUtl.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setConnectTimeout(30 * 1000);
            con.setRequestMethod(method);
            // set header
            for (Map.Entry<String, String> entry : DEFAULT_HEADERS.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            con.setDefaultUseCaches(false);

            if (params != null && !params.isEmpty()) {
                OutputStream outputStream = con.getOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream);
                StringBuilder requestString = new StringBuilder();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    requestString.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8.name()).trim()).append("&");
                }
                out.writeBytes(requestString.substring(0, requestString.length() - 1));
                out.flush();
                out.close();
            }
            int code = con.getResponseCode();
            if (code != HttpServletResponse.SC_OK) {
                logger.error("接口返回错误,错误码:{}", code);
                throw new RuntimeException("接口返回错误,错误码:" + code);
            }
            InputStream in = con.getInputStream();
            result = IOUtils.toString(in, StandardCharsets.UTF_8);
            return result;
        } finally {
            logger.debug("<--- request:{} result:{} total time:{}ms", url, result, (System.currentTimeMillis() - start));
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public void setHeader(String key, String value) {
        DEFAULT_HEADERS.put(key, value);
    }
}
