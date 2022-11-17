package com.scaffold.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 80275131
 * @version 1.0
 * @date 2022/8/24 16:59
 * @since 1.0
 **/
public class Main {

	public static void main(final String[] args) throws Exception {
		HttpAsyncClient httpclient = new HttpAsyncClient();
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json;charset=utf-8");
		header.put("Accept-Encoding", "gzip");
		header.put("x-openrtb-version", "2.5");
		header.put("Connection", "Keep-Alive");
		for (int i = 0; i < 10; i++) {
			httpclient.doHttpGet("https://www.baidu.com/", header, String.valueOf(i), new FutureCallback<>() {
				@Override
				public void completed(HttpResponse httpResponse) {
					System.out.println("completed");
				}

				@Override
				public void failed(Exception e) {
					e.printStackTrace();
				}

				@Override
				public void cancelled() {
					System.out.println("cancel");
				}
			});
		}
		Thread.sleep(5000L);
		System.out.println("finish");

	}
}