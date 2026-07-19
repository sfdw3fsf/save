
package com.vn.vnptit.Oneapp_Com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class DownloadInvNoPayResultModel {
	 @JsonProperty("downloadInvNoPayResult")
	    private String downloadInvNoPayResult;

	public String getDownloadInvNoPayResult() {
		return downloadInvNoPayResult;
	}

	public void setDownloadInvNoPayResult(String downloadInvNoPayResult) {
		this.downloadInvNoPayResult = downloadInvNoPayResult;
	}


	    
}
