package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录退出
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class WebLogoutAction extends AbstractHttpAction {

	/**
	 * <p>Constructor for WebLogoutAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public WebLogoutAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_LOGOUT);
		req.addGetValue("ids", ""); // 产生过会话才出现ID，如何获取？？
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		String isOK = json.getString("result");
		if (json.getInt("retcode") == 0) {
			if (isOK.equalsIgnoreCase("ok")) {
				notifyActionEvent(QQActionEvent.Type.EVT_OK, isOK);
				return;
			}
		}

		notifyActionEvent(QQActionEvent.Type.EVT_ERROR, isOK);
	}

}
