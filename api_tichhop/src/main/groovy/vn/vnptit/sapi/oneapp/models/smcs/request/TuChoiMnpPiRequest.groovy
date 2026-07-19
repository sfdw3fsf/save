package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class TuChoiMnpPiRequest {
    @JsonProperty("status")
    public String status
    @JsonProperty("note")
    public String note
    @JsonProperty("so_tb")
    public String so_tb
    @JsonProperty("piid")
    public String piid

    @JsonProperty("account")
    public String account

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    String getNote() {
        return note
    }

    void setNote(String note) {
        this.note = note
    }

    String getSo_tb() {
        return so_tb
    }

    void setSo_tb(String so_tb) {
        this.so_tb = so_tb
    }

    String getPiid() {
        return piid
    }

    void setPiid(String piid) {
        this.piid = piid
    }
}