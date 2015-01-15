package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.bean.QQStatus;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 改变在线状态
 *
 * @author ChenZhiHui
 */
public class ChangeStatusAction extends AbstractHttpAction {

	private QQStatus status;

	/**
	 * <p>Constructor for ChangeStatusAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param status a {@link com.minws.frame.sdk.webqq.bean.QQStatus} object.
	 */
	public ChangeStatusAction(QQContext context, QQActionListener listener,
			QQStatus status) {
		super(context, listener);
		this.status = status;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_CHANGE_STATUS);
		req.addGetValue("newstatus", status.getValue());
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
		if (json.getInt("retcode") == 0) {
			getContext().getAccount().setStatus(status);
			notifyActionEvent(QQActionEvent.Type.EVT_OK, status);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, 
				new QQException(QQErrorCode.UNEXPECTED_RESPONSE, response.getResponseString()));
		}
	}

}
