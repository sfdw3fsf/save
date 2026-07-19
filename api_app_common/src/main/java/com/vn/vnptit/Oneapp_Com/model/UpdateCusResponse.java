package com.vn.vnptit.Oneapp_Com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateCusResponse {

    @JsonProperty("UpdateCusResult")
    private Integer updateCusResult;
    
    public Integer getUpdateCusResult() {
	return updateCusResult;
    }

    public void setUpdateCusResult(Integer updateCusResult) {
	this.updateCusResult = updateCusResult;
    }
}
