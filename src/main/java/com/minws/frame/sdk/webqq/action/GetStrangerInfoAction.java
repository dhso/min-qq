
package com.minws.frame.sdk.webqq.action;

import java.text.ParseException;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.bean.QQAllow;
import com.minws.frame.sdk.webqq.bean.QQClientType;
import com.minws.frame.sdk.webqq.bean.QQUser;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;
import com.minws.frame.sdk.webqq.util.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>GetStrangerInfoAction class.</p>
 *
 * @author ZhiHui_Chen
 * @since date 2013-4-21
 */
public class GetStrangerInfoAction extends AbstractHttpAction {

	private QQUser user;
	

	/**
	 * <p>Constructor for GetStrangerInfoAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 */
	public GetStrangerInfoAction(QQContext context, QQActionListener listener,
			QQUser user) {
		super(context, listener);
		
		this.user = user;
	}
	
	/** {@inheritDoc} */
	@Override
	protected QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_GET_STRANGER_INFO);
		req.addGetValue("tuin", user.getUin() + "");
		req.addGetValue("verifysession", "");	// ?
		req.addGetValue("gid", "0");
		req.addGetValue("code", "");
		req.addGetValue("vfwebqq", session.getVfwebqq());
		req.addGetValue("t", System.currentTimeMillis()/1000 + "");
		return req;
	}
	
	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		/*
		 * {"retcode":0,"result":
		 * {"face":0,"birthday":{"month":0,"year":0,"day":0},
		 * "phone":"","occupation":"","allow":1,"college":"","uin":2842465527,"blood":0,
		 * "constel":0,"homepage":"","stat":10,"country":"","city":"","personal":"","nick":"平凡",
		 * "shengxiao":0,"email":"","token":"d04e802bda6ff115e31c3792199f15aa74f92eb435e75d93",
		 * "client_type":1,"province":"","gender":"male","mobile":"-"}}
		 */
		try{
		JSONObject json = new JSONObject(response.getResponseString());
        if (json.getInt("retcode") == 0) {
            JSONObject obj = json.getJSONObject("result");
            try {
				user.setBirthday(DateUtils.parse(obj.getJSONObject("birthday")));
			} catch (ParseException e) {
				user.setBirthday(null);
			}
            user.setOccupation(obj.getString("occupation"));
            user.setPhone(obj.getString("phone"));
            user.setAllow(QQAllow.values()[obj.getInt("allow")]);
            user.setCollege(obj.getString("college"));
            if (obj.has("reg_time")) {
                user.setRegTime(obj.getInt("reg_time"));
            }
            user.setUin(obj.getLong("uin"));
            user.setConstel(obj.getInt("constel"));
            user.setBlood(obj.getInt("blood"));
            user.setHomepage(obj.getString("homepage"));
            user.setStat(obj.getInt("stat"));
            if(obj.has("vip_info")) {
            	user.setVipLevel(obj.getInt("vip_info")); // VIP等级 0为非VIP
            }
            user.setCountry(obj.getString("country"));
            user.setCity(obj.getString("city"));
            user.setPersonal(obj.getString("personal"));
            user.setNickname(obj.getString("nick"));
            user.setChineseZodiac(obj.getInt("shengxiao"));
            user.setEmail("email");
            user.setProvince(obj.getString("province"));
            user.setGender(obj.getString("gender"));
            user.setMobile(obj.getString("mobile"));
            if (obj.has("client_type")) {
                user.setClientType(QQClientType.valueOfRaw(obj.getInt("client_type")));
            }
        }
		} catch(Exception e) {
			e.printStackTrace();
		}
        
        notifyActionEvent(QQActionEvent.Type.EVT_OK, user);
	}

}