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
 * Package  : com.minws.frame.sdk.webqq.module
 * File     : BuddyModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-7-31
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.module;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.action.GetGroupAccoutAction;
import com.minws.frame.sdk.webqq.action.GetGroupFaceAction;
import com.minws.frame.sdk.webqq.action.GetGroupInfoAction;
import com.minws.frame.sdk.webqq.action.GetGroupListAction;
import com.minws.frame.sdk.webqq.action.GetGroupMemberStatusAction;
import com.minws.frame.sdk.webqq.action.SearchGroupInfoAction;
import com.minws.frame.sdk.webqq.action.UpdateGroupMessageFilterAction;
import com.minws.frame.sdk.webqq.bean.QQGroup;
import com.minws.frame.sdk.webqq.bean.QQGroupSearchList;
import com.minws.frame.sdk.webqq.event.QQActionFuture;

/**
 *
 * 好友列表模块，处理好友的添加和删除
 *
 * @author solosky
 */
public class GroupModule extends AbstractModule {

	/**
	 * <p>getGroupList.</p>
	 *
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getGroupList(QQActionListener listener) {
		return pushHttpAction(new GetGroupListAction(getContext(), listener));
	}
	
	/**
	 * <p>updateGroupMessageFilter.</p>
	 *
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture updateGroupMessageFilter(QQActionListener listener) {
		return pushHttpAction(new UpdateGroupMessageFilterAction(getContext(), listener));
	}
	
	/**
	 * <p>getGroupFace.</p>
	 *
	 * @param group a {@link com.minws.frame.sdk.webqq.bean.QQGroup} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getGroupFace(QQGroup group, QQActionListener listener){
		return pushHttpAction(new GetGroupFaceAction(getContext(), listener, group));
	}

	/**
	 * <p>getGroupInfo.</p>
	 *
	 * @param group a {@link com.minws.frame.sdk.webqq.bean.QQGroup} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getGroupInfo(QQGroup group, QQActionListener listener) {
		return pushHttpAction(new GetGroupInfoAction(getContext(), listener, group));
	}

	/**
	 * <p>getGroupGid.</p>
	 *
	 * @param group a {@link com.minws.frame.sdk.webqq.bean.QQGroup} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getGroupGid(QQGroup group, QQActionListener listener) {
		return pushHttpAction(new GetGroupAccoutAction(getContext(), listener, group));
	}
	
	/**
	 * <p>getMemberStatus.</p>
	 *
	 * @param group a {@link com.minws.frame.sdk.webqq.bean.QQGroup} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getMemberStatus(QQGroup group, QQActionListener listener) {
		return pushHttpAction(new GetGroupMemberStatusAction(getContext(), listener, group));
	}
	
	/**
	 * <p>searchGroup.</p>
	 *
	 * @param resultList a {@link com.minws.frame.sdk.webqq.bean.QQGroupSearchList} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture searchGroup(QQGroupSearchList resultList, QQActionListener listener)
	{
		return pushHttpAction(new SearchGroupInfoAction(getContext(), listener, resultList));
	}
}
