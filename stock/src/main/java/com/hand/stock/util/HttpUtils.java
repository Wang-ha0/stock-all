package com.hand.stock.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author 王灏
 * @Date 2019/7/4 22:29
 * @Version 1.0.0
 */
@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        this.cm.setMaxTotal(100);
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return 页面数据
     */
    public String doGetHtml(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    private RequestConfig getConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(10 * 1000).build();
    }
}
