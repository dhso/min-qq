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
 * File     : GetGroupInfoAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2013-3-28
 * License  : Apache License 2.0 
 */
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.QQException.QQErrorCode;
import com.minws.frame.sdk.webqq.bean.QQClientType;
import com.minws.frame.sdk.webqq.bean.QQGroup;
import com.minws.frame.sdk.webqq.bean.QQGroupMember;
import com.minws.frame.sdk.webqq.bean.QQStatus;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 获取群信息， 包括群信息和群成员
 *
 * @author solosky
 */
public class GetGroupInfoAction extends AbstractHttpAction{
	private QQGroup group;
	/**
	 * <p>Constructor for GetGroupInfoAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param group a {@link com.minws.frame.sdk.webqq.bean.QQGroup} object.
	 */
	public GetGroupInfoAction(QQContext context, QQActionListener listener, QQGroup group) {
		super(context, listener);
		this.group = group;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		if(json.getInt("retcode") == 0){
			json = json.getJSONObject("result");
			JSONObject ginfo = json.getJSONObject("ginfo");
			group.setMemo(ginfo.getString("memo"));
			group.setLevel(ginfo.getInt("level"));
			group.setCreateTime(new Date(ginfo.getInt("createtime")));
			
			JSONArray members = ginfo.getJSONArray("members");
			for(int i=0; i<members.length(); i++){
				JSONObject memjson = members.getJSONObject(i);
				QQGroupMember member = group.getMemberByUin(memjson.getLong("muin"));
				if(member == null) {
					member = new QQGroupMember();
					group.getMembers().add(member);
				}
				member.setUin(memjson.getLong("muin"));
				member.setGroup(group); 
				//memjson.getLong("mflag"); //TODO ...
			}
			
			//result/minfo
			JSONArray minfos = json.getJSONArray("minfo");
			for(int i=0; i<minfos.length(); i++){
				JSONObject minfo = minfos.getJSONObject(i);
				QQGroupMember member = group.getMemberByUin(minfo.getLong("uin"));
				member.setNickname(minfo.getString("nick"));
				member.setProvince(minfo.getString("province"));
				member.setCountry(minfo.getString("country"));
				member.setCity(minfo.getString("city"));
				member.setGender(minfo.getString("gender"));
			}
			
			//result/stats
			JSONArray stats = json.getJSONArray("stats");
			for(int i=0; i<stats.length(); i++){
				// 下面重新设置最新状态
				JSONObject stat = stats.getJSONObject(i);
				QQGroupMember member = group.getMemberByUin(stat.getLong("uin"));
				member.setClientType(QQClientType.valueOfRaw(stat.getInt("client_type")));
				member.setStatus(QQStatus.valueOfRaw(stat.getInt("stat")));
			}
			
			//results/cards
			if(json.has("cards")){
				JSONArray cards = json.getJSONArray("cards");
				for(int i=0; i<cards.length(); i++){
					JSONObject card = cards.getJSONObject(i);
					QQGroupMember member = group.getMemberByUin(card.getLong("muin"));
					if( card != null && card.has("card") && member != null ) {
						member.setCard(card.getString("card"));
					}
				}
			}
			
			//results/vipinfo
			JSONArray vipinfos = json.getJSONArray("vipinfo");
			for(int i=0; i<vipinfos.length(); i++){
				JSONObject vipinfo = vipinfos.getJSONObject(i);
				QQGroupMember member = group.getMemberByUin(vipinfo.getLong("u"));
				member.setVipLevel(vipinfo.getInt("vip_level"));
				member.setVip(vipinfo.getInt("is_vip") == 1);
			}
			
			notifyActionEvent(QQActionEvent.Type.EVT_OK, group);
		}else{
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, QQErrorCode.UNEXPECTED_RESPONSE);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_GROUP_INFO_EXT);
		req.addGetValue("gcode", group.getCode() + "");
		req.addGetValue("vfwebqq", getContext().getSession().getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis()/1000 + "");
		return req;
	}
	
	

}
