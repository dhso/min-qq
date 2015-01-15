package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.bean.QQGroup;
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
public class GetGroupFaceAction extends AbstractHttpAction {

	private QQGroup group;

	/**
	 * <p>Constructor for GetGroupFaceAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param group a {@link com.minws.frame.sdk.webqq.bean.QQGroup} object.
	 */
	public GetGroupFaceAction(QQContext context, QQActionListener listener,
			QQGroup group) {
		super(context, listener);
		this.group = group;
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET",
				QQConstants.URL_GET_USER_FACE);
		req.addGetValue("uin", group.getCode() + "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis() / 1000 + "");
		req.addGetValue("cache", "0");
		req.addGetValue("type", "4");
		req.addGetValue("fid", "0");
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
			group.setFace(image);
		} catch (IOException e) {
			new QQException(QQErrorCode.IO_ERROR, e);
		}
		notifyActionEvent(QQActionEvent.Type.EVT_OK, group);
	}

}
