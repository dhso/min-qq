package com.minws.frame.sdk.webqq.bean.content;

import com.minws.frame.sdk.webqq.QQException;



/**
 * 内容接口
 *
 * @author ChenZhiHui
 * @since 2013-2-25
 */
public interface ContentItem {
	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link com.minws.frame.sdk.webqq.bean.content.ContentItem.Type} object.
	 */
	public Type getType();
	/**
	 * <p>toJson.</p>
	 *
	 * @throws com.minws.frame.sdk.webqq.QQException if any.
	 * @return a {@link java.lang.Object} object.
	 */
	public Object toJson() throws QQException;
	/**
	 * <p>fromJson.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @throws com.minws.frame.sdk.webqq.QQException if any.
	 */
	public void fromJson(String text) throws QQException;

	public enum Type {
		/**字体*/
		FONT("font"), 
		/** 文字*/
		TEXT("text"), 
		/**表情*/
		FACE("face"), 
		/**离线图片*/
		OFFPIC("offpic"),
		/**群图片*/
		CFACE("cface");

		private String name;
		Type(String name){
			this.name = name; 
		}
		public String getName(){
			return name;
		}
		public static Type valueOfRaw(String txt){
			if(txt.equals("font")){
				return FONT;
			}else if(txt.equals("face")){
				return FACE;
			}else if(txt.equals("offpic")){
				return OFFPIC;
			}else if(txt.equals("cface")){
				return CFACE;
			}else{
				return TEXT;
			}
		}
	}
}
