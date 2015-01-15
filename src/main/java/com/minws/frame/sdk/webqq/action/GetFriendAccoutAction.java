package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.bean.QQUser;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取QQ账号
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class GetFriendAccoutAction extends AbstractHttpAction {

	private QQUser buddy;

	/**
	 * <p>Constructor for GetFriendAccoutAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param buddy a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 */
	public GetFriendAccoutAction(QQContext context, QQActionListener listener, QQUser buddy) {
		super(context, listener);
		this.buddy = buddy;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		// tuin=4245757755&verifysession=&type=1&code=&vfwebqq=**&t=1361631644492
		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_USER_ACCOUNT);
		req.addGetValue("tuin", buddy.getUin() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		req.addGetValue("verifysession", ""); // 验证码？？
		req.addGetValue("type", 1 + "");
		req.addGetValue("code", "");

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if (json.getInt("retcode") == 0) {
			JSONObject obj = json.getJSONObject("result");
			buddy.setQQ(obj.getLong("account"));
		}
		
		notifyActionEvent(QQActionEvent.Type.EVT_OK, buddy);
	}

}
