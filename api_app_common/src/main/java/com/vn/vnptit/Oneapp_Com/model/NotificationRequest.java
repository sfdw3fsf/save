package com.vn.vnptit.Oneapp_Com.model;

public class NotificationRequest {
	private String target; //device_id của thiết bị
    private String title;  //tiêu đề tin nhắn
    private String body;   // nội dung tin nhắn
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public NotificationRequest(String target, String title, String body) {
		super();
		this.target = target;
		this.title = title;
		this.body = body;
	}
	

}
