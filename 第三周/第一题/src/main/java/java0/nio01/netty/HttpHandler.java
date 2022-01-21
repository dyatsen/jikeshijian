package java0.nio01.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java0.nio01.filter.RequestFilter;
import java0.nio01.filter.ResponseFilter;
import java0.nio01.filter.impl.RequestFilterImpl;
import java0.nio01.filter.impl.ResponseFilterImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpHandler extends ChannelInboundHandlerAdapter {

    private final RequestFilter requestFilter = new RequestFilterImpl();
    private final ResponseFilter responseFilter = new ResponseFilterImpl();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();

            // 请求添加过滤器
            requestFilter.filter(fullRequest, ctx);

            //logger.info("接收到的请求url为{}", uri);
            if (uri.contains("/test")) {
                handlerTest(fullRequest, ctx, "hello,kimmking");
            } else {
                handlerTest(fullRequest, ctx, "hello,others");
            }
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String body) {

        CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(5, false)).build();

        FullHttpResponse response = null;
        try {
            String value = body; // 对接上次作业的httpclient或者okhttp请求另一个url的响应数据

//            httpGet ...  http://localhost:8801
//            返回的响应，"hello,nio";
//            value = reponse....

            HttpGet httpGet = new HttpGet("http://localhost:8801");
            HttpResponse httpResponse = null;

            try {
                httpResponse = httpClient.execute(httpGet);

                // 拿到response并经过过滤器处理
                responseFilter.filter(httpResponse);
                value = httpResponse.getEntity().toString();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());

        } catch (Exception e) {
            System.out.println("处理出错:"+e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
                ctx.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
