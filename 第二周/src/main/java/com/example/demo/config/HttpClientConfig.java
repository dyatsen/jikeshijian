package com.example.demo.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientConfig.class);

    private PoolingHttpClientConnectionManager poolManager;

    @Autowired
    public void setPoolManager(){
        LOG.info("========开始创建连接池========");
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(10);
        manager.setDefaultMaxPerRoute(5);
        LOG.info("========连接池创建完成========");
        this.poolManager = manager;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
        return HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setConnectionManager(poolManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(5, false))
                .build();
    }
}
