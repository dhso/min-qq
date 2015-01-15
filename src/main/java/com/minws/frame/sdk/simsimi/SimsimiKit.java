/**
 * 
 */
/**
 * @author hadong
 *
 */
package com.minws.frame.sdk.simsimi;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class SimsimiKit {

	/**
	 * 小黄鸡问答
	 * 
	 * @param ask
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String ask(String ask) throws ParseException, IOException, JSONException {
		System.out.println("[simsimi] ask: " + ask);
		HttpClient httpclient = new DefaultHttpClient();
		String resStr = "";
		String uri = "http://www.simsimi.com/func/reqN?lc=ch&ft=0.0&req=" + URLEncoder.encode(ask, "UTF-8") + "&fl=http%3A%2F%2Fwww.simsimi.com%2Ftalk.htm";
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("USER_AGENT", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
			httpget.addHeader("Cookie", "simsimi_uid=59406430");
			// 执行get请求.
			HttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			if (entity != null) {
				resStr = EntityUtils.toString(entity);
				resStr = new JSONObject(resStr).getString("sentence_resp");
			}
		} finally {
			// 关闭连接,释放资源
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println("[simsimi] reply: " + resStr);
		return resStr;
	}
}