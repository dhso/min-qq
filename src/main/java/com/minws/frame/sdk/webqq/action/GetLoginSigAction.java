 /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /**
 * Project  : WebQQCore
 * Package  : com.minws.frame.sdk.webqq.action
 * File     : GetLoginSigAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : Aug 4, 2013
 * License  : Apache License 2.0 
 */
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.json.JSONException;
import org.slf4j.LoggerFactory;

/**
 *
 * 获取从登陆页面获取LoginSig
 * 2013-08-03 接口更新
 *
 * @author solosky
 */
public class GetLoginSigAction extends AbstractHttpAction {
	private static Logger LOG = LoggerFactory.getLogger(GetLoginSigAction.class);

	/**
	 * <p>Constructor for GetLoginSigAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public GetLoginSigAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		Pattern pt = Pattern.compile(QQConstants.REGXP_LOGIN_SIG);
		Matcher mc = pt.matcher(response.getResponseString());
		if(mc.find()){
			QQSession session = getContext().getSession();
			session.setLoginSig(mc.group(1));
			LOG.info("loginSig = " + session.getLoginSig());
			notifyActionEvent(QQActionEvent.Type.EVT_OK, session.getLoginSig());
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.INVALID_RESPONSE, "Login Sig Not Found!!"));
		}
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		return createHttpRequest("GET", QQConstants.URL_LOGIN_PAGE);
	}
}
