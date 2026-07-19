
package com.vn.vnptit.Oneapp_Com.model;

/**
 * @author DanhNQ
 *
 */
public class ThongtinServiceHDDTModel {

	private String	url;
	private String	publicservice;
	private String	bizservice;
	private String	portalservice;
	private String	account;
	private String	aCpass;
	private String	user;			// user sử dụng
	private String	passwword;		// pasword của user
	private Integer	tt78;


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getaCpass() {
		return aCpass;
	}

	public void setaCpass(String aCpass) {
		this.aCpass = aCpass;
	}


	public Integer getTt78() {
		return tt78;
	}

	public void setTt78(Integer tt78) {
		this.tt78 = tt78;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswword() {
		return passwword;
	}

	public void setPasswword(String passwword) {
		this.passwword = passwword;
	}

	public String getPublicservice() {
		return publicservice;
	}

	public void setPublicservice(String publicservice) {
		this.publicservice = publicservice;
	}

	public String getBizservice() {
		return bizservice;
	}

	public void setBizservice(String bizservice) {
		this.bizservice = bizservice;
	}

	public String getPortalservice() {
		return portalservice;
	}

	public void setPortalservice(String portalservice) {
		this.portalservice = portalservice;
	}


}
