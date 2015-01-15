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
 * File     : GetOffPicAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-8
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.bean.QQMsg;
import com.minws.frame.sdk.webqq.bean.content.OffPicItem;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import java.io.OutputStream;

import org.json.JSONException;

/**
 *
 * 获取聊天图片
 *
 * @author solosky
 */
public class GetOffPicAction extends AbstractHttpAction{
	private OffPicItem offpic;
	private QQMsg msg;
	private OutputStream picOut;
	
	/**
	 * <p>Constructor for GetOffPicAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param offpic a {@link com.minws.frame.sdk.webqq.bean.content.OffPicItem} object.
	 * @param msg a {@link com.minws.frame.sdk.webqq.bean.QQMsg} object.
	 * @param picOut a {@link java.io.OutputStream} object.
	 */
	public GetOffPicAction(QQContext context, QQActionListener listener, OffPicItem offpic, QQMsg msg, OutputStream picOut) {
		super(context, listener);
		this.offpic = offpic;
		this.msg = msg;
		this.picOut = picOut;
	}

	/* (non-Javadoc)
	 * @see com.minws.frame.sdk.webqq.action.AbstractHttpAction#onHttpStatusOK(com.minws.frame.sdk.webqq.http.QQHttpResponse)
	 */
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		notifyActionEvent(QQActionEvent.Type.EVT_OK, offpic);
	}

	/* (non-Javadoc)
	 * @see com.minws.frame.sdk.webqq.action.AbstractHttpAction#onBuildRequest()
	 */
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_OFFPIC);
		QQSession session  = getContext().getSession();
		req.addGetValue("clientid", session.getClientId() + "");
		req.addGetValue("f_uin", msg.getFrom().getUin() + "");
		req.addGetValue("file_path", offpic.getFilePath());
		req.addGetValue("psessionid", session.getSessionId());
		req.setOutputStream(picOut);
		return req;
	}
	
	

}
