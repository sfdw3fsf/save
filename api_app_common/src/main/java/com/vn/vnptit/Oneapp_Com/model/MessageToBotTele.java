package com.vn.vnptit.Oneapp_Com.model;

public class MessageToBotTele {
	private String text;
	private boolean disable_web_page_preview = true;
	private boolean disable_notification = false;
	private String reply_to_message_id;
	private String chat_id;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
	public boolean isDisable_web_page_preview() {
		return disable_web_page_preview;
	}
	public void setDisable_web_page_preview(boolean disable_web_page_preview) {
		this.disable_web_page_preview = disable_web_page_preview;
	}
	public boolean isDisable_notification() {
		return disable_notification;
	}
	public void setDisable_notification(boolean disable_notification) {
		this.disable_notification = disable_notification;
	}
	public String getReply_to_message_id() {
		return reply_to_message_id;
	}
	public void setReply_to_message_id(String reply_to_message_id) {
		this.reply_to_message_id = reply_to_message_id;
	}
	public String getChat_id() {
		return chat_id;
	}
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	public MessageToBotTele() {
		super();
	}
	public MessageToBotTele(String text, boolean disable_web_page_preview,
			boolean disable_notification, String reply_to_message_id, String chat_id) {
		super();
		this.text = text;
		this.disable_web_page_preview = disable_web_page_preview;
		this.disable_notification = disable_notification;
		this.reply_to_message_id = reply_to_message_id;
		this.chat_id = chat_id;
	}


}
