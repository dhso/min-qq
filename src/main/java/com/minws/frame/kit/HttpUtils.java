package com.minws.frame.kit;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 获得用户远程地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	public static String httpGet(String url) throws ClientProtocolException, IOException {
		return url;
		/*CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		RequestConfig requestconfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setCookieSpec(CookieSpecs.BEST_MATCH).build();
		httpget.setConfig(requestconfig);
		response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String str = null;
		if (entity != null) {
			InputStream instreams = entity.getContent();
			str = StringUtils.convertStreamToString(instreams);
			try {
				instreams.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;*/
	}

	public static <T> T httpGetClazz(String url, Class<T> t) throws ClientProtocolException, IOException {
		return null;
		/*CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		RequestConfig requestconfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).setCookieSpec(CookieSpecs.BEST_MATCH).build();
		httpget.setConfig(requestconfig);
		response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String str = null;
		T tClass = null;
		if (entity != null) {
			InputStream instreams = entity.getContent();
			str = StringUtils.convertStreamToString(instreams);
			ObjectMapper mapper = new ObjectMapper();
			tClass = mapper.convertValue(str, t);
			try {
				instreams.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tClass;*/
	}

	/**
	 * 设置全局代理
	 * 
	 * @param proxyHost
	 * @param proxyPort
	 * @param proxyUser
	 * @param proxyPassword
	 */
	public static void setProxy(String proxyHost, String proxyPort, String proxyUser, String proxyPassword) {
		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyUser);
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyUser);
		// System.setProperty("socksProxyHost", "proxy.ace.aliyun.com");
		// System.setProperty("socksProxyPort", "3128");
		// System.setProperty("socket.proxyHost", "proxy.ace.aliyun.com");
		// System.setProperty("socket.proxyPort", "3128");
		Authenticator.setDefault(new MyAuthenticator(proxyUser, proxyPassword));
	}

	static class MyAuthenticator extends Authenticator {
		private String user = "";
		private String password = "";

		public MyAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password.toCharArray());
		}
	}
}
