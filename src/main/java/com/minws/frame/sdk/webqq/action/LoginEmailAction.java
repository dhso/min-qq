
package com.minws.frame.sdk.webqq.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

/**
 * 登录邮箱
 *
 * @author 承∮诺
 * @since 2014年1月24日
 */
public class LoginEmailAction extends AbstractHttpAction {


	/**
	 * <p>Constructor for LoginEmailAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public LoginEmailAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_LOGIN_EMAIL);
		req.addGetValue("fun", "passport");
		req.addGetValue("from", "webqq");
		req.addGetValue("Referer", "https://mail.qq.com/cgi-bin/loginpage");
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		String REGXP = "sid=(.*?)\"";
		Pattern p = Pattern.compile(REGXP);
        Matcher m = p.matcher(response.getResponseString());
        if(m.find()){
        	String sid = m.group(1);
        	notifyActionEvent(QQActionEvent.Type.EVT_OK, sid);
        	System.out.println("LoginEmailAction***" + sid);
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
        }
	}

}
