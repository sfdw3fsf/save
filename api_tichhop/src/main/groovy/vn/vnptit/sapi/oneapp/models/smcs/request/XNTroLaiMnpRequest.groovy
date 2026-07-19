package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class XNTroLaiMnpRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("note")
    public String note
    @JsonProperty("status")
    public String status
    @JsonProperty("subtype")
    public String subtype
    @JsonProperty("poid")
    public String poid
    @JsonProperty("soTB")
    public String soTB
    @JsonProperty("packageId")
    public String packageId
    @JsonProperty("otp")
    public String otp

    String getPackageId() {
        return packageId
    }

    void setPackageId(String packageId) {
        this.packageId = packageId
    }

    String getOtp() {
        return otp
    }

    void setOtp(String otp) {
        this.otp = otp
    }

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getNote() {
        return note
    }

    void setNote(String note) {
        this.note = note
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

    String getPoid() {
        return poid
    }

    void setPoid(String poid) {
        this.poid = poid
    }

    String getSoTB() {
        return soTB
    }

    void setSoTB(String soTB) {
        this.soTB = soTB
    }
}