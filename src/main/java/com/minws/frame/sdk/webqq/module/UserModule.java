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
 * Package  : com.minws.frame.sdk.webqq.module
 * File     : UserModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-5
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.module;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.action.ChangeStatusAction;
import com.minws.frame.sdk.webqq.action.GetFriendAccoutAction;
import com.minws.frame.sdk.webqq.action.GetFriendFaceAction;
import com.minws.frame.sdk.webqq.action.GetFriendInfoAction;
import com.minws.frame.sdk.webqq.action.GetFriendSignAction;
import com.minws.frame.sdk.webqq.action.GetStrangerInfoAction;
import com.minws.frame.sdk.webqq.action.GetUserLevelAction;
import com.minws.frame.sdk.webqq.bean.QQStatus;
import com.minws.frame.sdk.webqq.bean.QQUser;
import com.minws.frame.sdk.webqq.event.QQActionFuture;

/**
 *
 * 个人信息模块
 *
 * @author solosky
 */
public class UserModule extends AbstractModule {
	/**
	 * <p>getUserFace.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getUserFace(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetFriendFaceAction(getContext(), listener, user));
	}
	
	/**
	 * <p>getUserInfo.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getUserInfo(QQUser user, QQActionListener listener){
		return pushHttpAction(new GetFriendInfoAction(getContext(), listener, user));
	}
	
	/**
	 * <p>getUserAccount.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getUserAccount(QQUser user, QQActionListener listener){
		return pushHttpAction(new GetFriendAccoutAction(getContext(), listener, user));
	}
	
	/**
	 * <p>getUserSign.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getUserSign(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetFriendSignAction(getContext(), listener, user));
	}
	
	/**
	 * <p>getUserLevel.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getUserLevel(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetUserLevelAction(getContext(), listener, user));
	}
	

	/**
	 * <p>changeStatus.</p>
	 *
	 * @param status a {@link com.minws.frame.sdk.webqq.bean.QQStatus} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture changeStatus(QQStatus status, QQActionListener listener) {
		return pushHttpAction(new ChangeStatusAction(getContext(), listener, status));
	}
	
	/**
	 * <p>getStrangerInfo.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getStrangerInfo(QQUser user, QQActionListener listener) {
		return pushHttpAction(new GetStrangerInfoAction(getContext(), listener, user));
	}
}
