package vn.vnptit.sapi.oneapp.models.vtc.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.vtc.models.*

@JsonIgnoreProperties(["metaClass"])
class NgungDichVuReq {
    @JsonProperty("subscriptionId")
    public String subscriptionId
    @JsonProperty("refId")
    public String refId
    @JsonProperty("reason")
    public String reason
    @JsonProperty("thongTinQuanLyBh")
    public ThongTinQuanLyBh thongTinQuanLyBh

    String getSubscriptionId() {
        return subscriptionId
    }

    void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId
    }

    String getRefId() {
        return refId
    }

    void setRefId(String refId) {
        this.refId = refId
    }

    String getReason() {
        return reason
    }

    void setReason(String reason) {
        this.reason = reason
    }

    ThongTinQuanLyBh getThongTinQuanLyBh() {
        return thongTinQuanLyBh
    }

    void setThongTinQuanLyBh(ThongTinQuanLyBh thongTinQuanLyBh) {
        this.thongTinQuanLyBh = thongTinQuanLyBh
    }
}
