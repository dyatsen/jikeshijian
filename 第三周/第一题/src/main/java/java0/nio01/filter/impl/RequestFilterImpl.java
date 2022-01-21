package java0.nio01.filter.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import java0.nio01.filter.RequestFilter;

public class RequestFilterImpl implements RequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("RequestHeaderAddition", "AdditionalContent");
    }
}
