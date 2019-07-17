package com.shop.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @Description: HTTP工具类
 * @email 465119455@qq.com
 * @date 2016年8月5日
 */
@SuppressWarnings("deprecation")
public class HttpUtil {
    public static final String   UTF_8         = "UTF-8";

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000)
                                                   .setConnectTimeout(60000)
                                                   .setConnectionRequestTimeout(60000).build();

    public static String sendGet(String url) {
        String responseContent = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseContent;
    }

    public static String sendPost(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return sendPost(httpPost);
    }

    public static String sendPost(String url, Map<String, String> header, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        for (String key : header.keySet()) {
            httpPost.setHeader(key, header.get(key));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return sendPost(httpPost);
    }

    //    public static String sendMultipartPost(String url, Map<String, String> params) {
    //        HttpPost httpPost = new HttpPost(url);// 创建httpPost
    //        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    //        for (Map.Entry<String, String> entry : params.entrySet()) {
    //            builder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.create("text/plain", "utf-8")));
    //        }
    //        httpPost.setEntity(builder.build());
    //        return sendPost(httpPost);
    //    }

    public static String sendPost(String url, String params) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setEntity(new StringEntity(params, UTF_8));
        return sendPost(httpPost);
    }

    public static String sendJsonPost(String url, String params) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(params, UTF_8));
        return sendPost(httpPost);
    }

    public static String sendXmlPost(String url, String params) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Content-Type", "text/xml");
        httpPost.setEntity(new StringEntity(params, UTF_8));
        return sendPost(httpPost);
    }

    private static String sendPost(HttpPost httpPost) {
        String responseContent = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseContent;
    }

    public static String sendPost(String url, Map<String, String> params, String charset) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sendPost(httpPost);
    }

    public static String sendHttpsPost(String url, Map<String, String> params) {
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, UTF_8));
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseContent;
    }

    public static byte[] sendHttpsPost(String url, InputStream params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new InputStreamEntity(params));
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                return EntityUtils.toByteArray(httpEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String sendJsonHttpsPost(String url, String params) {
        //        System.out.println("url---> " + url);
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);// 创建httpPost
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(params, UTF_8));
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    /**
     * @param url
     * @param params
     * @return
     */
    public static String sendPostLocation(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sendPostLocation(httpPost);
    }

    private static String sendPostLocation(HttpPost httpPost) {
        String responseContent = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (302 == statusCode) {
                Header[] headers = httpResponse.getHeaders("Location");
                if (headers != null && headers.length > 0) {
                    String redirectUrl = headers[0].getValue();
                    System.out.println("重定向的URL:" + redirectUrl);
                    return redirectUrl;
                    //                    redirectUrl = redirectUrl.replace(" ", "%20");
                }
            }

            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    /**
     * 开联通,指定编码 ISO-8859-1
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendHttpsPost(String url, Map<String, String> params, String charset) {
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            //			UrlEncodedFormEntity entity =new UrlEncodedFormEntity(nvps, "UTF-8");
            //			System.out.println(entity.getContentType());
            //			System.out.println(entity.getContentLength());
            //			System.out.println(EntityUtils.getContentCharSet(entity));
            //			System.out.println(EntityUtils.toString(entity));

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset="
                                               + charset);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, UTF_8));
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    public static String sendHttpsPost(String url, String params) {
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(params, UTF_8));
            httpPost.setHeader("Content-Type", "application/json");
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseContent;

    }

    public static String sendHttpsPost(String url, String params, String charset) {
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(params, charset));
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, charset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseContent;
    }

    public static String sendHttpsPost(String url, String params, String charset, int timeout) {
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(params, charset));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, charset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"respCode\":\"2408\",\"respDesc\":\"渠道请求超时或异常,请稍后重试\",\"respData\":{}}";
        }
        return responseContent;
    }

    public static String sendHttpsPostWithForm(String url, String params) {
        String responseContent = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(params, UTF_8));
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setDefaultRequestConfig(requestConfig).build();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                    Charset.forName(UTF_8)));) {
            String line = "";
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> getFormParameters(ServletRequest request) {
        Map<String, String> parametersMap = new HashMap<String, String>();
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String[] values = entry.getValue();
                parametersMap.put(entry.getKey(), values == null || values.length == 0 ? null
                    : values[0]);

            }
        } catch (Exception e) {
            //ignore
        }
        return parametersMap;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                new TrustStrategy() {

                    @Override
                    public boolean isTrusted(X509Certificate[] chain, String authType)
                                                                                      throws CertificateException {
                        return true;
                    }
                }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                public void verify(String host, String[] cns, String[] subjectAlts)
                                                                                   throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    /**
     * get
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String host, String path, String method,
                                     Map<String, String> headers, Map<String, String> querys)
                                                                                             throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        if (null != headers) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        return httpClient.execute(request);
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(
                ctx);
            ssf.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String buildUrl(String host, String path, Map<String, String> querys)
                                                                                        throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }
}