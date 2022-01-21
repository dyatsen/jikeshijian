package java0.nio01.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface RequestFilter {

    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
