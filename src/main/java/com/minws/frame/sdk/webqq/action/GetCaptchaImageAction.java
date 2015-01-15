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
 * File     : GetCaptchaImageAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-6
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONException;

/**
 *
 * 获取验证码图片
 *
 * @author solosky
 */
public class GetCaptchaImageAction extends AbstractHttpAction {
	private long uin;
	/**
	 * <p>Constructor for GetCaptchaImageAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param uin a long.
	 */
	public GetCaptchaImageAction(QQContext context, QQActionListener listener, long uin) {
		super(context, listener);
		this.uin = uin;
	}
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(response.getResponseData());
			notifyActionEvent(QQActionEvent.Type.EVT_OK, ImageIO.read(in));
		} catch (IOException e) {
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNKNOWN_ERROR, e));
		}
	}
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_CAPTCHA);
		req.addGetValue("aid", QQConstants.APPID);
		req.addGetValue("r", Math.random() + "");
		req.addGetValue("uin", uin + "");
		return req;
	}

	
}
