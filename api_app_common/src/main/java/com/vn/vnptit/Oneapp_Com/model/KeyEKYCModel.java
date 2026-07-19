
package com.vn.vnptit.Oneapp_Com.model;

/**
 * @author Danhnq
 *
 */
public class KeyEKYCModel {
	private String p_access_token_ekyc ;
	private String p_token_id_ekyc;
	private String  p_token_key_ekyc;
	public String getP_access_token_ekyc() {
		return p_access_token_ekyc;
	}
	public void setP_access_token_ekyc(String p_access_token_ekyc) {
		this.p_access_token_ekyc = p_access_token_ekyc;
	}
	public String getP_token_id_ekyc() {
		return p_token_id_ekyc;
	}
	public void setP_token_id_ekyc(String p_token_id_ekyc) {
		this.p_token_id_ekyc = p_token_id_ekyc;
	}
	public String getP_token_key_ekyc() {
		return p_token_key_ekyc;
	}
	public void setP_token_key_ekyc(String p_token_key_ekyc) {
		this.p_token_key_ekyc = p_token_key_ekyc;
	}
	public KeyEKYCModel(String p_access_token_ekyc, String p_token_id_ekyc, String p_token_key_ekyc) {
		super();
		this.p_access_token_ekyc = p_access_token_ekyc;
		this.p_token_id_ekyc = p_token_id_ekyc;
		this.p_token_key_ekyc = p_token_key_ekyc;
	}
	
}
