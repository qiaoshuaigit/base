package com.minxin.base.web.util;

import com.minxin.base.common.utils.JsonUtil;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http 工具类. TODO 方法需要测试
 *
 * @author todd
 */
public class HttpConnectionUtil {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionUtil.class);

    /**
     * 最大连接数
     */
    private static final int MAX_TOTAL_CONNECTIONS = 200;

    /**
     * 每个路由最大连接数
     */
    private static final int MAX_ROUTE_CONNECTIONS = 10;

    /**
     * 从连接池中取连接的超时时间，单位ms
     */
    private static final int WAIT_TIMEOUT = 1000;

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 2000;

    /**
     * 请求超时时间
     */
    private static final int READ_TIMEOUT = 120000;

    /**
     * http client
     */
    private static CloseableHttpClient HTTPCLIENT;

    /**
     * http连接池内部监控线程，用于连接监控、释放
     */
    private static IdleConnectionMonitorThread IDLECONNMONITOR;

    // 初始化连接池
    static {
        LOGGER.info("初始化Http连接池开始>>>>>>");
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom().loadTrustMaterial(null,
                    new TrustStrategy() {
                        // 自定义校验，信任所有
                        @Override
                        public boolean isTrusted(
                                X509Certificate[] arg0, String arg1
                        )
                                throws CertificateException {
                            return true;
                        }
                    }).build();
        } catch (KeyManagementException e) {
            LOGGER.error("初始化连接池过程时出错", e);
        } catch (KeyStoreException e) {
            LOGGER.error("初始化连接池过程时出错", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("初始化连接池过程时出错", e);
        }
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        // 双重锁
        // 默认重试3次链接
        HTTPCLIENT = HttpClients.custom().setConnectionManager(cm).setRetryHandler(new DefaultHttpRequestRetryHandler()).build();

        IDLECONNMONITOR = new IdleConnectionMonitorThread(cm);
        // 设置为监护线程
        IDLECONNMONITOR.setDaemon(true);
        IDLECONNMONITOR.start();
        LOGGER.info("初始化Http连接池结束>>>>>");
    }

    /**
     * 销毁监护线程
     */
    public static void destory() {
        IDLECONNMONITOR.interrupt();
    }

    /**
     * 配置请求的相关参数
     *
     * @param httpRequestBase http请求
     */
    private static void config(HttpRequestBase httpRequestBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(WAIT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * 发送Post请求，utf-8编码
     *
     * @param url    请求url
     * @param reqMap 请求参数
     * @return 返回结果
     */
    public static String doPost(String url, Map<String, Object> reqMap) {
        return actualDoPost(url, reqMap);
    }

    /**
     * 发送post请求，获取响应头
     *
     * @param url    请求url
     * @param reqMap post请求参数
     * @param header 要获取的响应头的关键字
     * @return 对应响应头信息
     */
    public static String doPostReturnHeader(String url, Map<String, Object> reqMap, String header) {
        return actualDoPost(url, reqMap, header);
    }

    /**
     * 发送Post请求
     *
     * @param url    请求url
     * @param reqMap 请求参数
     * @return 返回值
     */
    public static String doPostByJson(String url, Map<String, Object> reqMap) {
        return actualDoPost(url, reqMap, null, true);
    }

    /**
     * 发送请求，返回响应
     *
     * @param url    请求url
     * @param reqMap post请求参数
     * @return 返回响应结果
     */
    private static String actualDoPost(String url, Map<String, Object> reqMap) {
        return actualDoPost(url, reqMap, null);
    }

    /**
     * 发送请求，返回响应
     *
     * @param url    请求url
     * @param reqMap post请求参数
     * @param header 响应头
     * @return 返回响应结果
     */
    private static String actualDoPost(String url, Map<String, Object> reqMap, String header) {
        return actualDoPost(url, reqMap, header, false);
    }

    /**
     * 发送请求，返回响应
     *
     * @param url                请求url
     * @param reqMap             post请求参数
     * @param header             响应头
     * @param convertParamToJson 是否需要将请求参数转为json
     * @return 响应内容
     */
    private static String actualDoPost(String url, Map<String, Object> reqMap, String header, boolean convertParamToJson) {
        HttpPost httpRequest = buildHttpPostRequest(url, reqMap, convertParamToJson);

        long current = System.currentTimeMillis();
        try {
            CloseableHttpResponse httpResponse = HTTPCLIENT.execute(httpRequest);
            LOGGER.info("调用远程接口{}，耗时：{}", url, System.currentTimeMillis() - current);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                LOGGER.error("远程调用错误返回码", httpResponse.getStatusLine().getStatusCode());
                return null;
            }

            //如果不需要获取头，则将响应内容返回
            if (header == null) {
                HttpEntity httpEntity = httpResponse.getEntity();

                try {
                    InputStream is = httpEntity.getContent();
                    return inputStreamToString(is);
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("获取响应内容出错,{}", e);
                }
            } else {
                //返回响应头
                Header[] headers = httpResponse.getHeaders(header);
                LOGGER.info("" + headers.length);
                if (headers.length > 0) {
                    for (Header someHeader : headers) {
                        if (header.equals(someHeader.getName())) {
                            return someHeader.getValue();
                        }
                    }
                }
            }

            return "";
        } catch (IOException e) {
            LOGGER.error("调用远程接口{}失败,耗时：{}, 异常信息：{}", url, System.currentTimeMillis() - current, e);
        } finally {
            // 将连接释放回连接池
            httpRequest.releaseConnection();
        }
        return "";
    }

    /**
     * 构建请求参数
     *
     * @param url                请求url
     * @param reqMap             post请求参数
     * @param convertParamToJson 是否需要将请求参数转为json
     * @return 请求
     */
    private static HttpPost buildHttpPostRequest(String url, Map<String, Object> reqMap, boolean convertParamToJson) {
        HttpPost httpPostRequest = new HttpPost(url);

        if (convertParamToJson) {
            String jsonStr = JsonUtil.toJSONString(reqMap);
            try {
                StringEntity stringEntity = new StringEntity(jsonStr);
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPostRequest.setEntity(stringEntity);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("构建请求失败,封装StringEntity出错:{}", e);
            }
        } else {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : reqMap.entrySet()) {
                Object value = entry.getValue();
                params.add(new BasicNameValuePair(entry.getKey(), value == null ? null : value.toString()));
            }

            try {
                httpPostRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                config(httpPostRequest);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("构建请求失败,不支持的编码>>,{}, {}", url, e);
            }
        }
        return httpPostRequest;
    }

    /**
     * 发送get请求，utf-8编码
     *
     * @param url 请求url
     * @return 返回请求结果
     */
    public static String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        config(httpGet);
        long current = System.currentTimeMillis();
        try (CloseableHttpResponse httpResponse = HTTPCLIENT.execute(httpGet)) {
            LOGGER.info("远程调用使用时间,{},{}", url, System.currentTimeMillis() - current);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK && httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
                LOGGER.error("远程调用错误返回码:{}", httpResponse.getStatusLine().getStatusCode());
                return "";
            }
            HttpEntity entity = httpResponse.getEntity();
            InputStream is = entity.getContent();
            return inputStreamToString(is);
        } catch (Exception e) {
            LOGGER.error("远程调用失败,{},{}", url, System.currentTimeMillis() - current, e);
        } finally {
            // 将链接释放回连接池
            httpGet.releaseConnection();
        }
        return "";
    }

    /**
     * http delete方法
     *
     * @param url 请求url
     * @return 执行结果
     */
    public static String doDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        config(httpDelete);
        long current = System.currentTimeMillis();
        try {
            CloseableHttpResponse httpResponse = HTTPCLIENT.execute(httpDelete);
            httpResponse.getEntity();

        } catch (Exception e) {
            LOGGER.error("远程调用失败,{},{}", url, System.currentTimeMillis() - current, e);
        } finally {
            // 将链接释放回连接池
            httpDelete.releaseConnection();
        }

        return "";
    }

    /**
     * 将输入流转换为string
     *
     * @param is 输入流
     * @return 输入流转换后的string
     */
    private static String inputStreamToString(InputStream is) {
        if (is == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, Consts.UTF_8.name()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            LOGGER.info("第三方接口返回数据");
            return result;
        } catch (IOException e) {
            LOGGER.error("读取返回数据出错", e);
            return "";
        }
    }

    /**
     * http连接池内部监控线程，用于连接监控、释放
     */
    private static class IdleConnectionMonitorThread extends Thread {
        /**
         * 连接池内部日志
         */
        private final Logger logger = LoggerFactory.getLogger(IdleConnectionMonitorThread.class);

        /**
         * 连接管理器
         */
        private final HttpClientConnectionManager connMgr;

        IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    sleep(5000L);
                    // 关闭已过时链接
                    connMgr.closeExpiredConnections();
                    //关闭超过30秒的空闲链接
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            } catch (InterruptedException ex) {
                logger.info("连接池监控线程已退出");
            }
        }
    }
}
