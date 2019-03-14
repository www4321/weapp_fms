package com.bupt.common;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtils {
    public static String doPost(String url, Map<String, String> map, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public static String doGet(String url, Map<String, String> map, String charset) {
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            URIBuilder uriBuilder = new URIBuilder(url);
            //设置参数
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                uriBuilder.addParameter(elem.getKey(), elem.getValue());
            }
            httpGet = new HttpGet(uriBuilder.build());
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public static class SSLClient extends DefaultHttpClient {
        //用于进行Https请求的HttpClient
        public SSLClient() throws Exception {
            super();
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,String authType) throws CertificateException { }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException { }
                @Override
                public X509Certificate[] getAcceptedIssuers() {return null; }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);
            HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
    }
    public static String httpGetRequest(String url, Map<String, String> headers, String method,Map<String, String> params){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
            if (params != null && params.size() > 0){
                for(Map.Entry<String, String> entry : params.entrySet()){
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            HttpGet httpget = new HttpGet(uriBuilder.build());
            if (headers != null && headers.size() > 0){
                for(Map.Entry<String, String> entry : headers.entrySet()){
                    httpget.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpclient.execute(httpget);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
