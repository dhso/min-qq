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
 * Package  : com.minws.frame.sdk.webqq.protocol
 * File     : CheckVerifyAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-1
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.event.QQActionEventArgs;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;
import com.minws.frame.sdk.webqq.util.StringHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 检查账号，如果账号安全则不需要验证码
 *
 * @author solosky
 */
public class CheckVerifyAction extends AbstractHttpAction {
	private String qqAccount;
	/**
	 * <p>Constructor for CheckVerifyAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param qqAccount a {@link java.lang.String} object.
	 */
	public CheckVerifyAction(QQContext context, QQActionListener listener, String qqAccount) {
		super(context, listener);
		this.qqAccount = qqAccount;
	}

	/** {@inheritDoc} */
	@Override
	public void onHttpStatusOK(QQHttpResponse response) {
		Pattern p = Pattern.compile(QQConstants.REGXP_CHECK_VERIFY);
        Matcher m = p.matcher(response.getResponseString());
        if(m.find()){
        	String qqHex = m.group(3);
			qqHex = qqHex.replaceAll("\\\\x", "");
        	QQActionEventArgs.CheckVerifyArgs args = new QQActionEventArgs.CheckVerifyArgs();
        	args.result = Integer.parseInt(m.group(1));
        	args.code   = m.group(2);
        	args.uin    = Long.parseLong(qqHex, 16);
        	notifyActionEvent(QQActionEvent.Type.EVT_OK, args);
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
        }
	}

	/** {@inheritDoc} */
	@Override
	public QQHttpRequest buildRequest() {
		String url = StringHelper.format(QQConstants.URL_CHECK_VERIFY, qqAccount, 
				getContext().getSession().getLoginSig(), Math.random());
		return createHttpRequest("GET", url);
	}
}
