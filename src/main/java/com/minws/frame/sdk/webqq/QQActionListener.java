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
 * Package  : com.minws.frame.sdk.webqq
 * File     : QQActionListener.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-1
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq;

import com.minws.frame.sdk.webqq.event.QQActionEvent;



/**
 *
 *
 * QQ请求事件监听器
 *
 * @author solosky
 */
public interface QQActionListener {
	/**
	 * <p>onActionEvent.</p>
	 *
	 * @param event a {@link com.minws.frame.sdk.webqq.event.QQActionEvent} object.
	 */
	public void onActionEvent(QQActionEvent event); 
}
