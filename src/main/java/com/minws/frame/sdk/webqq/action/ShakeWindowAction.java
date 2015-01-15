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
 * Project  : WebQQCoreAsync
 * Package  : com.minws.frame.sdk.webqq.action
 * File     : GetFriendInfoAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
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
import com.minws.frame.sdk.webqq.util.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 震动聊天窗口
 *
 * @author solosky
 */
public class ShakeWindowAction extends AbstractHttpAction{
	private QQUser user;
	/**
	 * <p>Constructor for ShakeWindowAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 */
	public ShakeWindowAction(QQContext context, QQActionListener listener, QQUser user) {
		super(context, listener);
		this.user = user;
	}
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_SHAKE_WINDOW);
		req.addGetValue("to_uin", user.getUin() + "");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("t", DateUtils.nowTimestamp() + "");

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}
	
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
        if (json.getInt("retcode") == 0) {
        	 notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
        }else{
        	 notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNEXPECTED_RESPONSE));
        }
       
	}
}
