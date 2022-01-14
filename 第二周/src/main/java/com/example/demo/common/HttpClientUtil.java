package com.example.demo.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class HttpClientUtil {

    @Resource
    private CloseableHttpClient httpClient;

    public HttpResponse doGet(String url){
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            // 拿到结果进行初步处理，然后return出去给业务处理

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

}
