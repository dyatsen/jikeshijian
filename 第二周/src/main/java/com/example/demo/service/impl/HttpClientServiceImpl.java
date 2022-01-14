package com.example.demo.service.impl;

import com.example.demo.common.HttpClientUtil;
import com.example.demo.service.HttpClientService;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HttpClientServiceImpl implements HttpClientService {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    @Resource
    HttpClientUtil httpClientUtil;

    @Override
    public void connectTo8801() {
        HttpResponse response = httpClientUtil.doGet("http://localhost:8801");
        LOG.info("得到8801的结果：{}" ,response);
    }
}
