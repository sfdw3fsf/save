
package com.vn.vnptit.Oneapp_Com.model;

/**
 * @author DanhNQ
 *
 */
public class VideoCallConfig {
	private Integer	active;
	private String	token_id_app;
	private String	token_id_app_dest;
	private String	token_id;
	private String	token_key;
	private String	client_id;
	private String	client_secret;
	private String vcall_customer_token;
	private Integer is_use_vcall;
	
	
	
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public String getToken_id_app() {
		return token_id_app;
	}
	public void setToken_id_app(String token_id_app) {
		this.token_id_app = token_id_app;
	}
	public String getToken_id_app_dest() {
		return token_id_app_dest;
	}
	public void setToken_id_app_dest(String token_id_app_dest) {
		this.token_id_app_dest = token_id_app_dest;
	}
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}
	public String getToken_key() {
		return token_key;
	}
	public void setToken_key(String token_key) {
		this.token_key = token_key;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getVcall_customer_token() {
		return vcall_customer_token;
	}
	public void setVcall_customer_token(String vcall_customer_token) {
		this.vcall_customer_token = vcall_customer_token;
	}
	public Integer getIs_use_vcall() {
		return is_use_vcall;
	}
	public void setIs_use_vcall(Integer is_use_vcall) {
		this.is_use_vcall = is_use_vcall;
	}
	
	
}
