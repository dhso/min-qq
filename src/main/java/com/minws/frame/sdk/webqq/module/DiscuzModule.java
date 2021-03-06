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
 * File     : DiscuzModule.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-4
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.module;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.action.GetDiscuzInfoAction;
import com.minws.frame.sdk.webqq.action.GetDiscuzListAction;
import com.minws.frame.sdk.webqq.bean.QQDiscuz;
import com.minws.frame.sdk.webqq.event.QQActionFuture;

/**
 *
 * 讨论组模块
 *
 * @author solosky
 */
public class DiscuzModule extends AbstractModule{
	
	/**
	 * <p>getDiscuzList.</p>
	 *
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getDiscuzList(QQActionListener listener) {
		return pushHttpAction(new GetDiscuzListAction(getContext(), listener));
	}
	
	/**
	 * <p>getDiscuzInfo.</p>
	 *
	 * @param discuz a {@link com.minws.frame.sdk.webqq.bean.QQDiscuz} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getDiscuzInfo(QQDiscuz discuz, QQActionListener listener) {
		return pushHttpAction(new GetDiscuzInfoAction(getContext(), listener, discuz));
	}
}
