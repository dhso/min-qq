package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.bean.QQBuddy;
import com.minws.frame.sdk.webqq.bean.QQClientType;
import com.minws.frame.sdk.webqq.bean.QQStatus;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.core.QQStore;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 个人签名
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class GetOnlineFriendAction extends AbstractHttpAction {

	/**
	 * <p>Constructor for GetOnlineFriendAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public GetOnlineFriendAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();

		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_ONLINE_BUDDY_LIST);
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
		QQStore store = getContext().getStore();
		if (json.getInt("retcode") == 0) {
			JSONArray result = json.getJSONArray("result");
			for (int i = 0; i < result.length(); i++) {
				JSONObject obj = result.getJSONObject(i);
				long uin = obj.getLong("uin");
				String status = obj.getString("status");
				int clientType = obj.getInt("client_type");
				
				QQBuddy buddy = store.getBuddyByUin(uin);
				buddy.setStatus(QQStatus.valueOfRaw(status));
				buddy.setClientType(QQClientType.valueOfRaw(clientType));
			}
			
		}

		notifyActionEvent(QQActionEvent.Type.EVT_OK, store.getOnlineBuddyList());
	}

}
