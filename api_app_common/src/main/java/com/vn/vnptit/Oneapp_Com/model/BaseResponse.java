package com.vn.vnptit.Oneapp_Com.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JacksonXmlRootElement(localName = "BaseResponse")
public class BaseResponse {
    private String error;
    private String error_code;
    private String message;
    private String message_detail;
    private String request_id;
    private String page_info;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "data")
    private List<DataItem> data;

    // getters and setters
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

    public String getMessage_detail() {
        return message_detail;
    }

    public void setMessage_detail(String message_detail) {
        this.message_detail = message_detail;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getPage_info() {
        return page_info;
    }

    public void setPage_info(String page_info) {
        this.page_info = page_info;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    // nested class
    public static class DataItem {
        @JacksonXmlProperty(localName = "DOITUONG_ID")
        private String doituongId;

        @JacksonXmlProperty(localName = "TEN_DT")
        private String tenDt;

        // getters and setters

        public String getDoituongId() {
            return doituongId;
        }

        public String getTenDt() {
            return tenDt;
        }
    }
}
