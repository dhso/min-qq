package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取群图片 key and sig
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class GetCustomFaceSigAction extends AbstractHttpAction {

	/**
	 * <p>Constructor for GetCustomFaceSigAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public GetCustomFaceSigAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_CUSTOM_FACE_SIG);
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
		QQSession session = getContext().getSession();

		JSONObject json = new JSONObject(response.getResponseString());
		if (json.getInt("retcode") == 0) {
			JSONObject obj = json.getJSONObject("result");
			session.setCfaceKey(obj.getString("gface_key"));
			session.setCfaceSig(obj.getString("gface_sig"));
			notifyActionEvent(QQActionEvent.Type.EVT_OK, session);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
				new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString()));
		}
	}

}
