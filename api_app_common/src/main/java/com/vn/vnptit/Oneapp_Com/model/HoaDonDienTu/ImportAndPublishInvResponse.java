package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportAndPublishInvResponse {

    @JsonProperty("ImportAndPublishInvResult")
    private String result;

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

}
