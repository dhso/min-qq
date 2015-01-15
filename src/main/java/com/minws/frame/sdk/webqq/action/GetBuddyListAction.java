
package com.minws.frame.sdk.webqq.action;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.bean.QQAccount;
import com.minws.frame.sdk.webqq.bean.QQBuddy;
import com.minws.frame.sdk.webqq.bean.QQCategory;
import com.minws.frame.sdk.webqq.bean.QQClientType;
import com.minws.frame.sdk.webqq.bean.QQStatus;
import com.minws.frame.sdk.webqq.core.QQConstants;
import com.minws.frame.sdk.webqq.core.QQContext;
import com.minws.frame.sdk.webqq.core.QQService;
import com.minws.frame.sdk.webqq.core.QQSession;
import com.minws.frame.sdk.webqq.core.QQStore;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.http.QQHttpCookie;
import com.minws.frame.sdk.webqq.http.QQHttpRequest;
import com.minws.frame.sdk.webqq.http.QQHttpResponse;
import com.minws.frame.sdk.webqq.service.HttpService;
import com.minws.frame.sdk.webqq.util.QQEncryptor;

import org.slf4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 * <p>GetBuddyListAction class.</p>
 *
 * @author ChenZhiHui
 * @since 2013-2-21
 */
public class GetBuddyListAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(GetBuddyListAction.class);

	/**
	 * <p>Constructor for GetBuddyListAction.</p>
	 *
	 * @param context a {@link com.minws.frame.sdk.webqq.core.QQContext} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 */
	public GetBuddyListAction(QQContext context, QQActionListener listener) {
		super(context, listener);
	}

	/** {@inheritDoc} */
	@Override
	public QQHttpRequest onBuildRequest() throws QQException, JSONException {
		QQSession session = getContext().getSession();
		QQAccount account = getContext().getAccount();
		HttpService httpService = (HttpService) getContext().getSerivce(QQService.Type.HTTP);
		QQHttpCookie ptwebqq = httpService.getCookie("ptwebqq", QQConstants.URL_GET_USER_CATEGORIES);
		
		JSONObject json = new JSONObject();
		json.put("h", "hello");
		json.put("vfwebqq", session.getVfwebqq()); // 同上
		json.put("hash", QQEncryptor.hash(account.getUin() + "", ptwebqq.getValue()));

		QQHttpRequest req = createHttpRequest("POST",
				QQConstants.URL_GET_USER_CATEGORIES);
		req.addPostValue("r", json.toString());

		req.addHeader("Referer", QQConstants.REFFER);

		return req;
	}

	/** {@inheritDoc} */
	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException,
			JSONException {
		JSONObject json = new JSONObject(response.getResponseString());
		int retcode = json.getInt("retcode");
		QQStore store = getContext().getStore();
		if (retcode == 0) {
			// 处理好友列表
			JSONObject results = json.getJSONObject("result");
			// 获取JSON列表信息
			JSONArray jsonCategories = results.getJSONArray("categories");
			// 获取JSON好友基本信息列表 flag/uin/categories
			JSONArray jsonFriends = results.getJSONArray("friends");
			// face/flag/nick/uin
			JSONArray jsonInfo = results.getJSONArray("info");
			// uin/markname/
			JSONArray jsonMarknames = results.getJSONArray("marknames");
			// vip_level/u/is_vip
			JSONArray jsonVipinfo = results.getJSONArray("vipinfo");

			// 默认好友列表
			QQCategory c = new QQCategory();
			c.setIndex(0);
			c.setName("我的好友");
			c.setSort(0);
			store.addCategory(c);
			// 初始化好友列表
			for (int i = 0; i < jsonCategories.length(); i++) {
				JSONObject jsonCategory = jsonCategories.getJSONObject(i);
				QQCategory qqc = new QQCategory();
				qqc.setIndex(jsonCategory.getInt("index"));
				qqc.setName(jsonCategory.getString("name"));
				qqc.setSort(jsonCategory.getInt("sort"));
				store.addCategory(qqc);
			}
			// 处理好友基本信息列表 flag/uin/categories
			for (int i = 0; i < jsonFriends.length(); i++) {
				QQBuddy buddy = new QQBuddy();
				JSONObject jsonFriend = jsonFriends.getJSONObject(i);
				long uin = jsonFriend.getLong("uin");
				buddy.setUin(uin);
				buddy.setStatus(QQStatus.OFFLINE);
				buddy.setClientType(QQClientType.UNKNOWN);
				// 添加到列表中
				int category = jsonFriend.getInt("categories");
				QQCategory qqCategory = store.getCategoryByIndex(category);
				buddy.setCategory(qqCategory);
				qqCategory.getBuddyList().add(buddy);

				// 记录引用
				store.addBuddy(buddy);
			}
			// face/flag/nick/uin
			for (int i = 0; i < jsonInfo.length(); i++) {
				JSONObject info = jsonInfo.getJSONObject(i);
				long uin = info.getLong("uin");
				QQBuddy buddy = store.getBuddyByUin(uin);
				buddy.setNickname(info.getString("nick"));
			}
			// uin/markname
			for (int i = 0; i < jsonMarknames.length(); i++) {
				JSONObject jsonMarkname = jsonMarknames.getJSONObject(i);
				long uin = jsonMarkname.getLong("uin");
				QQBuddy buddy = store.getBuddyByUin(uin);
				if(buddy != null){
					buddy.setMarkname(jsonMarkname.getString("markname"));
				}
			}
			// vip_level/u/is_vip
			for (int i = 0; i < jsonVipinfo.length(); i++) {
				JSONObject vipInfo = jsonVipinfo.getJSONObject(i);
				long uin = vipInfo.getLong("u");
				QQBuddy buddy = store.getBuddyByUin(uin);
				buddy.setVipLevel(vipInfo.getInt("vip_level"));
				int isVip = vipInfo.getInt("is_vip");
				if(isVip != 0) {
					buddy.setVip(true);
				} else {
					buddy.setVip(false);
				}
			}

			notifyActionEvent(QQActionEvent.Type.EVT_OK, store.getCategoryList());

		} else {
			LOG.warn("unknown retcode: " + retcode);
			notifyActionEvent(QQActionEvent.Type.EVT_ERROR, null);
		}

	}

}
