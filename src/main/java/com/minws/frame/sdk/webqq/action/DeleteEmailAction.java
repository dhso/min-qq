package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.bean.QQEmail;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>DeleteEmailAction class.</p>
 */
public class DeleteEmailAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(DeleteEmailAction.class);
	private List<QQEmail> markList;

	/**
	 * <p>Constructor for DeleteEmailAction.</p>
	 *
	 * @param markList a {@link java.util.List} object.
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public DeleteEmailAction(List<QQEmail> markList,
			QQContext context, QQActionListener listener) {
		super(context, listener);
		
		this.markList = markList;

	}
	// mailaction=mail_del&mailid=C1TFACD70BB&t=mail_mgr2&resp_charset=UTF8&ef=js&sid=eEVNdM8QDlC8YWEz&folderkey=1
	/** {@inheritDoc} */
	@Override
	public QQHttpRequest buildRequest() throws QQException {
		QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_MARK_EMAIL);
		req.addPostValue("mailaction", "mail_del");
		req.addPostValue("t", "mail_mgr2");
		req.addPostValue("resp_charset", "UTF8");
		req.addPostValue("ef", "js");
		req.addPostValue("folderkey", "1");
		req.addPostValue("sid", getContext().getSession().getEmailAuthKey());
		for(QQEmail mail : markList) {
			req.addPostValue("mailid", mail.getId());
		}
		return req;
	}
	
	// ({msg : "new successful",rbkey : "1391255617",status : "false"})
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException {
		String ct = response.getResponseString();
		LOG.info("delete email: " + ct);
		if(ct.contains("success")) {
			notifyActionEvent(QQActionEvent.Type.EVT_OK, ct);
		} else {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, ct);
		}
	}

}
