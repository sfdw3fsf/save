package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThueBaoMnpRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("area")
    public String area
    @JsonProperty("status")
    public String status
    @JsonProperty("subtype")
    public String subtype

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getArea() {
        return area
    }

    void setArea(String area) {
        this.area = area
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    String getSubtype() {
        return subtype
    }

    void setSubtype(String subtype) {
        this.subtype = subtype
    }
}