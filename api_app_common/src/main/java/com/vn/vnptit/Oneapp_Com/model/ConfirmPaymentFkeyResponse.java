package com.vn.vnptit.Oneapp_Com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmPaymentFkeyResponse {

    @JsonProperty("confirmPaymentFkeyResult")
    private String result;

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

}
