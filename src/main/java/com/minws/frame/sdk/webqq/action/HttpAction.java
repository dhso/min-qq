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
 * File     : QQHttpAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-2
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import java.util.concurrent.Future;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.event.QQActionFuture;
import com.minws.frame.sdk.webqq.http.QQHttpListener;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

/**
 * <p>HttpAction interface.</p>
 *
 * @author solosky
 */
public interface HttpAction extends QQHttpListener {
	/**
	 * <p>buildRequest.</p>
	 *
	 * @throws com.minws.frame.sdk.webqq.QQException if any.
	 * @return a {@link com.minws.frame.sdk.webqq.http.QQHttpRequest} object.
	 */
	public QQHttpRequest buildRequest() throws QQException;
	/**
	 * <p>cancelRequest.</p>
	 *
	 * @throws com.minws.frame.sdk.webqq.QQException if any.
	 */
	public void cancelRequest() throws QQException;
	/**
	 * <p>isCancelable.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isCancelable();
	/**
	 * <p>notifyActionEvent.</p>
	 *
	 * @param type a {@link com.minws.frame.sdk.webqq.event.QQActionEvent.Type} object.
	 * @param target a {@link java.lang.Object} object.
	 */
	public void notifyActionEvent(QQActionEvent.Type type, Object target);
	/**
	 * <p>getActionListener.</p>
	 *
	 * @return a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public QQActionListener getActionListener();
	/**
	 * <p>setActionListener.</p>
	 *
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public void setActionListener(QQActionListener listener);
	/**
	 * <p>setActionFuture.</p>
	 *
	 * @param future a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public void setActionFuture(QQActionFuture future);
	/**
	 * <p>setResponseFuture.</p>
	 *
	 * @param future a {@link java.util.concurrent.Future} object.
	 */
	public void setResponseFuture(Future<QQHttpResponse> future);
}
