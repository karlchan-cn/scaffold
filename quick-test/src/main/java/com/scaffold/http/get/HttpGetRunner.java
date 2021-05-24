package com.scaffold.http.get;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * http get方法测试类
 */
public class HttpGetRunner {

    private static final void testGetMethodUrlLimitation() {
        //HttpClient client
    }

    /**
     * 发送get请求,  下载图片
     *
     * @param client   httpclient
     * @param savePath 保存地址
     * @param imgUrl   图片http路径
     */
    public static void httpGetImg(CloseableHttpClient client, String imgUrl, String savePath) {


        // 发送get请求
        HttpGet request = new HttpGet(imgUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).build();

        //设置请求头
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");

        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();

                InputStream in = entity.getContent();

                FileUtils.copyInputStreamToFile(in, new File(savePath));
                System.out.println("下载图片成功:" + imgUrl);

            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            request.releaseConnection();

        }
    }

    /**
     * 组装图片地址
     *
     * @param prefix      前缀
     * @param index       当前图片索引
     * @param indexLength 图片索引长度
     * @return 组装后的图片地址
     */
    private static String constructImgUrl(String prefix, int index, int indexLength) {
        String indexStr = String.valueOf(index);
        for (int i = indexStr.length(); i < indexLength; i++) {
            indexStr = "0" + indexStr;
        }
        return prefix + indexStr + ".jpg";
    }

    public static void main(String[] args) {

        CloseableHttpClient client = null;

        try {
            client = HttpClients.createDefault();
            String prefix = "https://static001.geekbang.org/con/30/pdf/2044952145/image/page-";
            for (int i = 1; i <= 38; i++) {
                String path = "E:\\dev\\ArchSubmit\\archsubmmit-images-container\\百度智能流量监控实战AIOps";
                httpGetImg(client, constructImgUrl(prefix, i, 3), path + "/" + i + ".jpg");
                System.out.println("ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}




