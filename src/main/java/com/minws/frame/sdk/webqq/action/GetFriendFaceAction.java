package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.bean.QQUser;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONException;

/**
 * 获取用户头像
 *
 * @author ChenZhiHui
 * @since 2013-2-23
 */
public class GetFriendFaceAction extends AbstractHttpAction {

	private QQUser user;

	/**
	 * <p>Constructor for GetFriendFaceAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 */
	public GetFriendFaceAction(QQContext context, QQActionListener listener,
			QQUser user) {
		super(context, listener);
		this.user = user;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_USER_FACE);
		req.addGetValue("uin", user.getUin() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		req.addGetValue("cache", 0 + ""); // ??
		req.addGetValue("type", 1 + ""); // ??
		req.addGetValue("fid", 0 + ""); // ??

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		ByteArrayInputStream in = new ByteArrayInputStream(
				response.getResponseData()); // 输入流
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
			user.setFace(image);
		} catch (IOException e) {
			new QQException(QQErrorCode.IO_ERROR, e);
		}
		notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
	}

}
