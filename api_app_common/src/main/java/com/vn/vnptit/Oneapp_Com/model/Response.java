package com.vn.vnptit.Oneapp_Com.model;

import java.util.ArrayList;

import com.vn.vnptit.Oneapp_Com.common.ErrorCode;


/**
 * @author Danh
 *
 */

public class Response {
	private ArrayList<Object>	data;
	private String				error;
	private String				error_code	= ErrorCode.BSS_00000000;
	private String				message;
	private String				request_id;
	private Object				page_info;

	public ArrayList<Object> getData() {
		return data;
	}

	public void setData(ArrayList<Object> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public Object getPage_info() {
		return page_info;
	}

	public void setPage_info(Object page_info) {
		this.page_info = page_info;
	}

}
