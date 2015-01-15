package com.minws.frame.sdk.webqq.module;

import com.minws.frame.sdk.webqq.QQActionListener;
import com.minws.frame.sdk.webqq.action.GetCustomFaceSigAction;
import com.minws.frame.sdk.webqq.action.GetGroupPicAction;
import com.minws.frame.sdk.webqq.action.GetOffPicAction;
import com.minws.frame.sdk.webqq.action.GetSessionMsgSigAction;
import com.minws.frame.sdk.webqq.action.GetUserPicAction;
import com.minws.frame.sdk.webqq.action.SendInputNotifyAction;
import com.minws.frame.sdk.webqq.action.SendMsgAction;
import com.minws.frame.sdk.webqq.action.ShakeWindowAction;
import com.minws.frame.sdk.webqq.action.UploadCustomFaceAction;
import com.minws.frame.sdk.webqq.action.UploadOfflinePictureAction;
import com.minws.frame.sdk.webqq.bean.QQMsg;
import com.minws.frame.sdk.webqq.bean.QQStranger;
import com.minws.frame.sdk.webqq.bean.QQUser;
import com.minws.frame.sdk.webqq.bean.content.CFaceItem;
import com.minws.frame.sdk.webqq.bean.content.OffPicItem;
import com.minws.frame.sdk.webqq.event.QQActionEvent;
import com.minws.frame.sdk.webqq.event.QQActionFuture;
import com.minws.frame.sdk.webqq.event.future.ProcActionFuture;

import java.io.File;
import java.io.OutputStream;

/**
 * 消息处理
 *
 * @author ChenZhiHui
 * @since 2013-2-25
 */
public class ChatModule extends AbstractModule {
	private QQActionFuture doSendMsg( QQMsg msg, QQActionListener listener) {
		return pushHttpAction(new SendMsgAction(getContext(), listener, msg));
	}
	
	/**
	 * <p>sendMsg.</p>
	 *
	 * @param msg a {@link com.minws.frame.sdk.webqq.bean.QQMsg} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture sendMsg(final QQMsg msg, QQActionListener listener) {
		if(msg.getType() == QQMsg.Type.SESSION_MSG) {
			final ProcActionFuture future = new ProcActionFuture(listener, true);
			QQStranger stranger = (QQStranger) msg.getTo();
			if(stranger.getGroupSig() == null || stranger.getGroupSig().equals("")) {
				getSessionMsgSig(stranger, new QQActionListener() {
					@Override
					public void onActionEvent(QQActionEvent event) {
						if(event.getType() == QQActionEvent.Type.EVT_OK) {
							if(!future.isCanceled()){
								doSendMsg(msg, future);
							}
						}else if(event.getType() == QQActionEvent.Type.EVT_ERROR){
							future.notifyActionEvent(event.getType(), event.getTarget());
						}
					}
				});
			}
			return future;
		} else if(msg.getType() == QQMsg.Type.GROUP_MSG || msg.getType() == QQMsg.Type.DISCUZ_MSG) {
			if(getContext().getSession().getCfaceKey() == null || getContext().getSession().getCfaceKey().equals("")) {
				final ProcActionFuture future = new ProcActionFuture(listener, true);
				getCFaceSig(new QQActionListener() {
					
					@Override
					public void onActionEvent(QQActionEvent event) {
						if(event.getType() == QQActionEvent.Type.EVT_OK) {
							if(!future.isCanceled()){
								doSendMsg(msg, future);
							}
						}else if(event.getType() == QQActionEvent.Type.EVT_ERROR){
							future.notifyActionEvent(event.getType(), event.getTarget());
						}
					}
				});
				return future;
			}
		}
		return doSendMsg(msg, listener);
	}
	
	/**
	 * <p>getSessionMsgSig.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQStranger} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getSessionMsgSig(QQStranger user, QQActionListener listener) {
		return pushHttpAction(new GetSessionMsgSigAction(getContext(), listener, user));
	}

	/**
	 * <p>uploadOffPic.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param file a {@link java.io.File} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture uploadOffPic(QQUser user, File file, QQActionListener listener) {
		return pushHttpAction(new UploadOfflinePictureAction(getContext(), listener, user, file));
	}
	
	/**
	 * <p>uploadCFace.</p>
	 *
	 * @param file a {@link java.io.File} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture uploadCFace(File file, QQActionListener listener) {
		return pushHttpAction(new UploadCustomFaceAction(getContext(),
				listener, file));
	}
	
	/**
	 * <p>getCFaceSig.</p>
	 *
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getCFaceSig(QQActionListener listener) {
		return pushHttpAction(new GetCustomFaceSigAction(getContext(), listener));
	}
	
	/**
	 * <p>sendShake.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture sendShake(QQUser user, QQActionListener listener){
		return pushHttpAction(new ShakeWindowAction(getContext(), listener, user));
	}
	
	/**
	 * <p>getOffPic.</p>
	 *
	 * @param offpic a {@link com.minws.frame.sdk.webqq.bean.content.OffPicItem} object.
	 * @param msg a {@link com.minws.frame.sdk.webqq.bean.QQMsg} object.
	 * @param picout a {@link java.io.OutputStream} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getOffPic(OffPicItem offpic, QQMsg msg,
									OutputStream picout, QQActionListener listener){
		return pushHttpAction(new GetOffPicAction(getContext(), listener, offpic, msg, picout));
	}
	
	/**
	 * <p>getUserPic.</p>
	 *
	 * @param cface a {@link com.minws.frame.sdk.webqq.bean.content.CFaceItem} object.
	 * @param msg a {@link com.minws.frame.sdk.webqq.bean.QQMsg} object.
	 * @param picout a {@link java.io.OutputStream} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getUserPic(CFaceItem cface, QQMsg msg,
			OutputStream picout, QQActionListener listener){
		return pushHttpAction(new GetUserPicAction(getContext(), listener, cface, msg, picout));
	}
	
	/**
	 * <p>getGroupPic.</p>
	 *
	 * @param cface a {@link com.minws.frame.sdk.webqq.bean.content.CFaceItem} object.
	 * @param msg a {@link com.minws.frame.sdk.webqq.bean.QQMsg} object.
	 * @param picout a {@link java.io.OutputStream} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture getGroupPic(CFaceItem cface, QQMsg msg,
			OutputStream picout, QQActionListener listener){
		return pushHttpAction(new GetGroupPicAction(getContext(), listener, cface, msg, picout));
	}
	
	/**
	 * <p>sendInputNotify.</p>
	 *
	 * @param user a {@link com.minws.frame.sdk.webqq.bean.QQUser} object.
	 * @param listener a {@link com.minws.frame.sdk.webqq.QQActionListener} object.
	 * @return a {@link com.minws.frame.sdk.webqq.event.QQActionFuture} object.
	 */
	public QQActionFuture sendInputNotify(QQUser user, QQActionListener listener){
		return pushHttpAction(new SendInputNotifyAction(getContext(), listener, user));
	}
}
