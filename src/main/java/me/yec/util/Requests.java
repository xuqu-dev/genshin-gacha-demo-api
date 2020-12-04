package me.yec.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

/**
 * @author yec
 * @date 12/4/20 6:00 PM
 */
@Slf4j
public class Requests {

    private static final List<String> USER_AGENTS;

    static {
        USER_AGENTS = List.of(
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1; rv:1.9.6.20) Gecko/2020-10-21 03:37:43 Firefox/3.8",
                "Opera/9.61.(X11; Linux i686; sw-TZ) Presto/2.9.176 Version/11.00",
                "Opera/9.24.(Windows NT 5.0; mag-IN) Presto/2.9.173 Version/11.00",
                "Mozilla/5.0 (Windows NT 5.2) AppleWebKit/533.1 (KHTML, like Gecko) Chrome/54.0.800.0 Safari/533.1",
                "Mozilla/5.0 (compatible; MSIE 5.0; Windows NT 5.1; Trident/4.1)",
                "Mozilla/5.0 (Android 2.3.7; Mobile; rv:29.0) Gecko/29.0 Firefox/29.0",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/3.1)"
        );
    }

    /**
     * 便捷发起 HTTP 的 GET 请求
     *
     * @param url 请求 URL
     * @return 响应结果体
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 便捷发起 HTTP 的 GET 请求
     *
     * @param url    请求 URL
     * @param header 请求头（项目只用到了 cookie ）
     * @return 响应结果体
     */
    public static String get(String url, Header header) {
        HttpUriRequest uriRequest = baseRequestBuilder(HttpGet.METHOD_NAME, url)
                .setHeader(header)
                .build();
        return executeHttpRequest(uriRequest);
    }

    /**
     * 发起 POST 请求
     *
     * @param url    请求链接
     * @param header 请求头（一般为 cookie）
     * @param data   请求数据（一般为 json 格式）
     * @return 响应体内容
     */
    public static String post(String url, Header header, String data) {
        StringEntity stringEntity = new StringEntity(data, "utf-8");
        HttpUriRequest uriRequest = baseRequestBuilder(HttpPost.METHOD_NAME, url)
                .setHeader("Content-Type", "application/json")
                .setHeader(header)
                .setEntity(stringEntity)
                .build();
        return executeHttpRequest(uriRequest);
    }

    /**
     * 随机获取用户代理
     *
     * @return USER_AGENTS 集合中随机一条数据
     */
    private static String randomUserAgent() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(USER_AGENTS.size());
        return USER_AGENTS.get(i);
    }

    /**
     * 获取基础的 RequestBuilder 对象，对请求头只设置了一个用户代理
     *
     * @param methodName 请求方法名（"GET"、"POST"...）
     * @param url        请求 url（http://example.org）
     * @return RequestBuilder 对象
     */
    private static RequestBuilder baseRequestBuilder(String methodName, String url) {
        return RequestBuilder
                .create(methodName)
                .setUri(url)
                .addHeader("user-agent", randomUserAgent());
    }

    /**
     * 使用 HttpClient 执行请求
     * 响应体交给 BasicResponseHandler 处理可得到字符串结果
     *
     * @param uriRequest 请求对象
     * @return HTTP响应体字符串内容（可能为 null）
     */
    private static String executeHttpRequest(HttpUriRequest uriRequest) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            return httpClient.execute(uriRequest, new BasicResponseHandler());
        } catch (IOException e) {
            log.error("http request error ({})", e.getMessage());
            return null;
        }
    }

}