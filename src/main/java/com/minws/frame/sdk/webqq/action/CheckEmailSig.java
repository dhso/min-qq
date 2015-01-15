
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONException;

/**
 * 通过pt4获取到的URL进行封装
 * 检测邮箱是否合法登录了
 *
 * @author 承∮诺
 */
public class CheckEmailSig extends AbstractHttpAction {
	
	private String url = "";

	/**
	 * <p>Constructor for CheckEmailSig.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public CheckEmailSig(String url, QQContext context, QQActionListener listener) {
		super(context, listener);
		this.url = url;
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		url += "&regmaster=undefined&aid=1";
		url += "&s_url=http%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Ffun%3Dpassport%26from%3Dwebqq";
		
		QQHttpRequest req = createHttpRequest("GET", url);
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		notifyActionEvent(QQActionEvent.Type.EVT_OK, "");
	}

}
