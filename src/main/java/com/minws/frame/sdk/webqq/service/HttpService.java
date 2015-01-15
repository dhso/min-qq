package com.minws.frame.sdk.webqq.service;

import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.core.QQService;
import com.minws.frame.sdk.webqq.http.QQHttpCookie;
import com.minws.frame.sdk.webqq.http.QQHttpListener;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import java.util.concurrent.Future;

/**
 * <p>HttpService interface.</p>
 */
public interface HttpService extends QQService{
	
	public enum ProxyType{
		HTTP,
		SOCK4,
		SOCK5
	}
	
	/**
	 * 设置HTTP代理
	 *
	 * @param proxyType				代理类型
	 * @param proxyHost				代理主机
	 * @param proxyPort				代理端口
	 * @param proxyAuthUser			认证用户名， 如果不需要认证，设置为null
	 * @param proxyAuthPassword		认证密码，如果不需要认证，设置为null
	 */
	public void setHttpProxy(ProxyType proxyType, String proxyHost,
			int proxyPort, String proxyAuthUser, String proxyAuthPassword);

	/**
	 * 创建一个请求
	 * 这个方法会填充默认的HTTP头，比如User-Agent
	 *
	 * @param method			请求方法，POST,GET,POST
	 * @param url				请求的URL
	 * @return a {@link com.minws.frame.sdk.webqq.http.QQHttpRequest} object.
	 */
	public QQHttpRequest createHttpRequest(String method, String url);

	/**
	 * 执行一个HTTP请求
	 *
	 * @param request			请求对象
	 * @param listener			请求回调
	 * @throws com.minws.frame.sdk.webqq.QQException if any.
	 * @return a {@link java.util.concurrent.Future} object.
	 */
	public Future<QQHttpResponse> executeHttpRequest(QQHttpRequest request, QQHttpListener listener) throws QQException;

	/**
	 * 获取一个cookie
	 *
	 * @param name				Cookie名
	 * @param url				基于的URL
	 * @return a {@link com.minws.frame.sdk.webqq.http.QQHttpCookie} object.
	 */
	public QQHttpCookie getCookie(String name, String url);

	/**
	 *
	 * 设置UA，每次在HTTP请求是会附带上
	 *
	 * @param userAgent a {@link java.lang.String} object.
	 */
	public void setUserAgent(String userAgent);
}
