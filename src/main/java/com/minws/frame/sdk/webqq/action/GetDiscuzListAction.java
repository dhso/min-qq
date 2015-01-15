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
 * File     : GetDiscuzListAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-4-4
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.bean.QQDiscuz;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.core.QQStore;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;
import com.minws.frame.sdk.webqq.util.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 获取讨论组列表
 *
 * @author solosky
 */
public class GetDiscuzListAction extends AbstractHttpAction{

	/**
	 * <p>Constructor for GetDiscuzListAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public GetDiscuzListAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}


	/* (non-Javadoc)
	 * @see com.minws.frame.sdk.webqq.action.AbstractHttpAction#onBuildRequest()
	 */
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_DISCUZ_LIST);
		req.addGetValue("clientid", session.getClientId()+"");
		req.addGetValue("psessionid", session.getSessionId());
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", DateUtils.nowTimestamp() + "");
		return req;
	}
	
	
	/* (non-Javadoc)
	 * @see com.minws.frame.sdk.webqq.action.AbstractHttpAction#onHttpStatusOK(com.minws.frame.sdk.webqq.http.QQHttpResponse)
	 */
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
		//{"retcode":0,"result":{"dnamelist":[{"did":3536443553,"name":"\u8FD9\u662F\u6807\u9898"},
		//{"did":625885728,"name":""}],"dmasklist":[{"did":1000,"mask":0}]}}
		
		JSONObject json = new JSONObject(response.getResponseString());
		QQStore store = getContext().getStore();
        if (json.getInt("retcode") == 0) {
            JSONObject result = json.getJSONObject("result");
            JSONArray  dizlist = result.getJSONArray("dnamelist");
            for(int i=0; i<dizlist.length(); i++){
            	 QQDiscuz discuz = new QQDiscuz();
            	 JSONObject dizjson = dizlist.getJSONObject(i);
            	 discuz.setDid(dizjson.getLong("did"));
            	 discuz.setName(dizjson.getString("name"));
            	 store.addDiscuz(discuz);
            }
            notifyActionEvent(QQActionEvent.Type.EVT_OK, store.getDiscuzList());
        }else{
        	notifyActionEvent(QQActionEvent.Type.EVT_ERROR, new QQException(QQErrorCode.UNEXPECTED_RESPONSE));
        }
	}
}
