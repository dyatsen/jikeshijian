package java0.nio01.filter.impl;

import io.netty.handler.codec.http.FullHttpResponse;
import java0.nio01.filter.ResponseFilter;
import org.apache.http.HttpResponse;

public class ResponseFilterImpl implements ResponseFilter {
    @Override
    public void filter(HttpResponse response) {

        response.setHeader("ResponseHeaderAddition", "AdditionalContent");
    }
}
