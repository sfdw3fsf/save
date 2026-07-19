package vn.vnptit.sapi.oneapp.models.smcs.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListHDChamSocUSSDRequest {

    @JsonProperty("account")
    public String account;

    @JsonProperty("agentCode")
    public String agentCode;

    @JsonProperty("trangThai")
    public String trangThai;

    @JsonProperty("dateFrom")
    public String dateFrom;

    @JsonProperty("dateTo")
    public String dateTo;

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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getMa_tinh() {
        return ma_tinh;
    }

    public void setMa_tinh(String ma_tinh) {
        this.ma_tinh = ma_tinh;
    }
}
