package com.minws.blog.ctrl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.ParseException;
import org.json.JSONException;

import com.minws.frame.sdk.simsimi.SimsimiKit;
import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.QQClient;
import com.minws.frame.sdk.webqq.QQException;
import com.minws.frame.sdk.webqq.WebQQClient;
import com.minws.frame.sdk.webqq.actor.ThreadActorDispatcher;
import com.minws.frame.sdk.webqq.bean.QQBuddy;
import com.minws.frame.sdk.webqq.bean.QQCategory;
import com.minws.frame.sdk.webqq.bean.QQDiscuz;
import com.minws.frame.sdk.webqq.bean.QQEmail;
import com.minws.frame.sdk.webqq.bean.QQGroup;
import com.minws.frame.sdk.webqq.bean.QQGroupSearchList;
import com.minws.frame.sdk.webqq.bean.QQMsg;
import com.minws.frame.sdk.webqq.bean.QQStatus;
import com.minws.frame.sdk.webqq.bean.content.ContentItem;
import com.minws.frame.sdk.webqq.bean.content.FaceItem;
import com.minws.frame.sdk.webqq.bean.content.FontItem;
import com.minws.frame.sdk.webqq.bean.content.TextItem;
import com.minws.frame.sdk.webqq.bean.content.OffPicItem;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.event.QQActionEvent.Type;
import com.minws.frame.sdk.webqq.event.QQNotifyEvent;
import com.minws.frame.sdk.webqq.event.QQNotifyEventArgs;
import com.minws.frame.sdk.webqq.event.QQNotifyHandler;
import com.minws.frame.sdk.webqq.event.QQNotifyHandlerProxy;

public class WebQQClientKit {

	QQClient qqClient;

	public WebQQClientKit(String user, String pwd) {
		qqClient = new WebQQClient(user, pwd, new QQNotifyHandlerProxy(this), new ThreadActorDispatcher());
	}

	/**
	 * 聊天消息通知，使用这个注解可以收到QQ消息
	 * 
	 * 接收到消息然后组装消息发送回去
	 * 
	 * @throws QQException
	 * @throws IOException
	 * @throws ParseException
	 * @throws JSONException 
	 */
	@QQNotifyHandler(QQNotifyEvent.Type.CHAT_MSG)
	public void processBuddyMsg(QQNotifyEvent event) throws QQException, ParseException, IOException, JSONException {
		QQMsg msg = (QQMsg) event.getTarget();
		String ask = "";
		String reply = "";
		System.out.println("[消息] " + msg.getFrom().getNickname() + "说:" + msg.packContentList());
		List<ContentItem> items = msg.getContentList();
		for (ContentItem item : items) {
			if (item.getType() == ContentItem.Type.FACE) {
				System.out.println("消息内容  Face:" + ((FaceItem) item).getId());
			} else if (item.getType() == ContentItem.Type.OFFPIC) {
				System.out.println("消息内容  Picture:" + ((OffPicItem) item).getFilePath());
			} else if (item.getType() == ContentItem.Type.TEXT) {
				ask = ((TextItem) item).getContent();
				System.out.println("消息内容  Text:" + ask);
				reply = new SimsimiKit().ask(ask);
			}
		}

		// 组装QQ消息发送回去
		QQMsg sendMsg = new QQMsg();
		sendMsg.setTo(msg.getFrom()); // QQ好友UIN
		sendMsg.setType(QQMsg.Type.BUDDY_MSG); // 发送类型为好友
		// QQ内容
		sendMsg.addContentItem(new TextItem(reply)); // 添加文本内容
		//sendMsg.addContentItem(new FaceItem(0)); // QQ id为0的表情
		sendMsg.addContentItem(new FontItem()); // 使用默认字体
		qqClient.sendMsg(sendMsg, null); // 调用接口发送消息
	}

	/**
	 * 被踢下线通知
	 * 
	 */
	@QQNotifyHandler(QQNotifyEvent.Type.KICK_OFFLINE)
	protected void processKickOff(QQNotifyEvent event) {
		System.out.println("被踢下线: " + (String) event.getTarget());
	}

	/**
	 * 需要验证码通知
	 * 
	 * @throws IOException
	 */
	@QQNotifyHandler(QQNotifyEvent.Type.CAPACHA_VERIFY)
	protected void processVerify(QQNotifyEvent event) throws IOException {
		QQNotifyEventArgs.ImageVerify verify = (QQNotifyEventArgs.ImageVerify) event.getTarget();
		ImageIO.write(verify.image, "png", new File("verify.png"));
		System.out.println(verify.reason);
		System.out.print("请输入在项目根目录下verify.png图片里面的验证码:");
		String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
		qqClient.submitVerify(code, event);
	}

	/**
	 * 登录
	 */
	public void login() {
		final QQActionListener listener = new QQActionListener() {
			public void onActionEvent(QQActionEvent event) {
				System.out.println("LOGIN_STATUS:" + event.getType() + ":" + event.getTarget());
				if (event.getType() == Type.EVT_OK) {
					// 到这里就算是登录成功了

					// 获取下用户信息
					qqClient.getUserInfo(qqClient.getAccount(), new QQActionListener() {
						public void onActionEvent(QQActionEvent event) {
							System.out.println("LOGIN_STATUS:" + event.getType() + ":" + event.getTarget());
						}
					});

					// 获取好友列表..TODO.
					// 不一定调用，可能会有本地缓存
					qqClient.getBuddyList(new QQActionListener() {

						@Override
						public void onActionEvent(QQActionEvent event) {
							// TODO Auto-generated method stub
							System.out.println("******** " + event.getType() + " ********");
							if (event.getType() == QQActionEvent.Type.EVT_OK) {
								System.out.println("******** 好友列表  ********");
								List<QQCategory> qqCategoryList = (List<QQCategory>) event.getTarget();

								for (QQCategory c : qqCategoryList) {
									System.out.println("分组名称:" + c.getName());
									List<QQBuddy> buddyList = c.getBuddyList();
									for (QQBuddy b : buddyList) {
										System.out.println("---- QQ nick:" + b.getNickname() + " markname:" + b.getMarkname() + " uin:" + b.getUin() + " isVip:" + b.isVip() + " vip_level:" + b.getVipLevel());
									}

								}
							} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
								System.out.println("** 好友列表获取失败，处理重新获取");
							}
						}
					});
					// 获取群列表
					qqClient.getGroupList(new QQActionListener() {

						@Override
						public void onActionEvent(QQActionEvent event) {
							if (event.getType() == Type.EVT_OK) {
								for (QQGroup g : qqClient.getGroupList()) {
									qqClient.getGroupInfo(g, null);
									System.out.println("Group: " + g.getName());
								}
							} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
								System.out.println("** 群列表获取失败，处理重新获取");
							}
						}
					});
					// 获取讨论组列表
					qqClient.getDiscuzList(new QQActionListener() {

						@Override
						public void onActionEvent(QQActionEvent event) {
							if (event.getType() == Type.EVT_OK) {
								for (QQDiscuz d : qqClient.getDiscuzList()) {
									qqClient.getDiscuzInfo(d, null);
									System.out.println("Discuz: " + d.getName());
								}
							}
						}
					});

					// 查群测试
					QQGroupSearchList list = new QQGroupSearchList();
					list.setKeyStr("QQ");
					qqClient.searchGroupGetList(list, new QQActionListener() {
						@Override
						public void onActionEvent(QQActionEvent event) {
							if (event.getType() == Type.EVT_OK) {

							}
						}
					});

					// 启动轮询时，需要获取所有好友、群成员、讨论组成员
					// 所有的逻辑完了后，启动消息轮询
					qqClient.beginPollMsg();
				}
			}
		};

		// String ua =
		// "Mozilla/5.0 (@os.name; @os.version; @os.arch) AppleWebKit/537.36 (KHTML, like Gecko) @appName Safari/537.36";
		// ua = ua.replaceAll("@appName", QQConstants.USER_AGENT);
		// ua = ua.replaceAll("@os.name", System.getProperty("os.name"));
		// ua = ua.replaceAll("@os.version", System.getProperty("os.version"));
		// ua = ua.replaceAll("@os.arch", System.getProperty("os.arch"));
		String ua = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
		qqClient.setHttpUserAgent(ua);
		qqClient.login(QQStatus.ONLINE, listener);
	}

	/**
	 * 新邮件通知
	 * 
	 * 这个暂时没有启用
	 * 
	 * @throws QQException
	 */
	@QQNotifyHandler(QQNotifyEvent.Type.EMAIL_NOTIFY)
	public void processEmailMsg(QQNotifyEvent event) throws QQException {
		List<QQEmail> list = (List<QQEmail>) event.getTarget();
		for (QQEmail mail : list) {
			System.out.println("邮件: " + mail.getSubject());
		}
	}
}
