package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 通过登录得到的sid，获取到wpkey
 *
 * @author 承∮诺
 * @since 2014年1月25日
 */
public class GetWPKeyAction extends AbstractHttpAction {
	private String sid = "";
	/**
	 * <p>Constructor for GetWPKeyAction.</p>
	 *
	 * @param sid a {@link java.lang.String} object.
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public GetWPKeyAction(String sid, QQContext context, QQActionListener listener) {
		super(context, listener);
		this.sid = sid;
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_WP_KEY);
		req.addGetValue("r", "0.7975904128979892");
		req.addGetValue("resp_charset", "UTF8");
		req.addGetValue("ef", "js");
		req.addGetValue("sid", sid);
		req.addGetValue("Referer", "http://mail.qq.com/cgi-bin/frame_html?sid=" + sid);
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		String resp = response.getResponseString();
		resp = resp.substring(1, resp.length() - 1);
		JSONObject json = new JSONObject(resp);
		if(json.has("k")) {
			notifyActionEvent(QQActionEvent.Type.EVT_OK, json.getString("k"));
		} else {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
		}
		System.out.println("GetWPKeyAction: " + response.getResponseString());
	}

}
