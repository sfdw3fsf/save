package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.smcs.model.ProductDetail

@JsonIgnoreProperties(["metaClass"])
class OrderApproveRequest {
    @JsonProperty("order_id")
    public String orderId
    @JsonProperty("order_date")
    public String date
    @JsonProperty("order_status")
    public String status
    @JsonProperty("eload")
    public String eload
    @JsonProperty("product_detail")
    public List<ProductDetail> productDetails
    @JsonProperty("change")
    public String change
    @JsonProperty("sms_900")
    public String sms900

    String getOrderId() {
        return orderId
    }

    void setOrderId(String orderId) {
        this.orderId = orderId
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    String getEload() {
        return eload
    }

    void setEload(String eload) {
        this.eload = eload
    }

    List<ProductDetail> getProductDetails() {
        return productDetails
    }

    void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails
    }

    String getChange() {
        return change
    }

    void setChange(String change) {
        this.change = change
    }

    String getSms900() {
        return sms900
    }

    void setSms900(String sms900) {
        this.sms900 = sms900
    }
}