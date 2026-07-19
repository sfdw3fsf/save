package vn.vnptit.sapi.oneapp.models.smcs.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoiDiChamSocUSSDRequest {

    @JsonProperty("account")
    public String account;

    @JsonProperty("agentCode")
    public String agentCode;

    @JsonProperty("longitude")
    public String longitude;

    @JsonProperty("latitude")
    public String latitude;

    @JsonProperty("picture")
    public String picture;

    @JsonProperty("ma_tinh")
    public String ma_tinh;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMa_tinh() {
        return ma_tinh;
    }

    public void setMa_tinh(String ma_tinh) {
        this.ma_tinh = ma_tinh;
    }
}
