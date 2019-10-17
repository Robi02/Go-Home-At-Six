package com.robi.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestHttpUtil.class);
    private static HttpComponentsClientHttpRequestFactory httpFactory = null;

    static {
        httpFactory = new HttpComponentsClientHttpRequestFactory();
        httpFactory.setReadTimeout(5000);
        httpFactory.setConnectTimeout(5000);
        httpFactory.setHttpClient(HttpClientBuilder.create()
            .setMaxConnTotal(1000)
            .setMaxConnPerRoute(100)
            .build());
    }

    public static RestTemplate getInstance() {
        return new RestTemplate(httpFactory);
    }
}