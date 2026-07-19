package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThuPhiMnpPiRequest {
    @JsonProperty("sotb")
    public String sotb
    @JsonProperty("matinh")
    public String matinh
    @JsonProperty("tienphi")
    public String tienphi
    @JsonProperty("ngaytt")
    public String ngaytt
    @JsonProperty("loaitb")
    public String loaitb
    @JsonProperty("nguoithu")
    public String nguoithu
    @JsonProperty("schema")
    public String schema
    @JsonProperty("tentt")
    public String tentt
    @JsonProperty("diachitt")
    public String diachitt

    String getSotb() {
        return sotb
    }

    void setSotb(String sotb) {
        this.sotb = sotb
    }

    String getMatinh() {
        return matinh
    }

    void setMatinh(String matinh) {
        this.matinh = matinh
    }

    String getTienphi() {
        return tienphi
    }

    void setTienphi(String tienphi) {
        this.tienphi = tienphi
    }

    String getNgaytt() {
        return ngaytt
    }

    void setNgaytt(String ngaytt) {
        this.ngaytt = ngaytt
    }

    String getLoaitb() {
        return loaitb
    }

    void setLoaitb(String loaitb) {
        this.loaitb = loaitb
    }

    String getNguoithu() {
        return nguoithu
    }

    void setNguoithu(String nguoithu) {
        this.nguoithu = nguoithu
    }

    String getSchema() {
        return schema
    }

    void setSchema(String schema) {
        this.schema = schema
    }

    String getTentt() {
        return tentt
    }

    void setTentt(String tentt) {
        this.tentt = tentt
    }

    String getDiachitt() {
        return diachitt
    }

    void setDiachitt(String diachitt) {
        this.diachitt = diachitt
    }
}