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
 * File     : CheckSigAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : Aug 4, 2013
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import org.slf4j.Logger;
import org.json.JSONException;
import org.slf4j.LoggerFactory;

/**
 * <p>CheckLoginSigAction class.</p>
 *
 * @author solosky
 */
public class CheckLoginSigAction extends AbstractHttpAction{
	private static final Logger LOG = LoggerFactory.getLogger(CheckLoginSigAction.class);
	private String checkSigUrl;
	/**
	 * <p>Constructor for CheckLoginSigAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param checkSigUrl a {@link java.lang.String} object.
	 */
	public CheckLoginSigAction(QQContext context, QQActionListener listener, String checkSigUrl) {
		super(context, listener);
		this.checkSigUrl = checkSigUrl;
	}
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		LOG.info("checkSig result:" + response.getResponseString());
		LOG.info("Location:" + response.getHeader("Location"));
		notifyActionEvent(QQActionEvent.Type.EVT_OK, null);
	}
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		return createHttpRequest("GET", checkSigUrl);
	}

}
