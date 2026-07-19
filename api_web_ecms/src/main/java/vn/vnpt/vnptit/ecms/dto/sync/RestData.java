package vn.vnpt.vnptit.ecms.dto.sync;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RestData {
    @JsonAlias({"data"})
    private Object data;
    private boolean status;
    private String path;
    private String time;
    @JsonAlias({"message", "msg"})
    private String message;
    @JsonAlias({"messageDetail", "error_msg"})
    private String messageDetail;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }
}
