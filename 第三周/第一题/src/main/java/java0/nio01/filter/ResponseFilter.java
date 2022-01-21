package java0.nio01.filter;


import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.http.HttpResponse;

public interface ResponseFilter {

    void filter(HttpResponse response);

}
