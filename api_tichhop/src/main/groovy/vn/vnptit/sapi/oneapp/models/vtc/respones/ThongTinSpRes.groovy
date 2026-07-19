package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.vtc.models.*

@JsonIgnoreProperties(["metaClass"])
class ThongTinSpRes {
    @JsonProperty("custCode")
    public String custCode
    @JsonProperty("refId")
    public String refId
    @JsonProperty("locationCode")
    public String locationCode
    @JsonProperty("productCode")
    public String productCode
    @JsonProperty("productName")
    public String productName
    @JsonProperty("subscriptionId")
    public String subscriptionId
    @JsonProperty("supplyCode")
    public String supplyCode
    @JsonProperty("status")
    public String status
    @JsonProperty("produtcDetails")
    public ProdutcDetails produtcDetails

    String getCustCode() {
        return custCode
    }

    void setCustCode(String custCode) {
        this.custCode = custCode
    }

    String getRefId() {
        return refId
    }

    void setRefId(String refId) {
        this.refId = refId
    }

    String getLocationCode() {
        return locationCode
    }

    void setLocationCode(String locationCode) {
        this.locationCode = locationCode
    }

    String getProductCode() {
        return productCode
    }

    void setProductCode(String productCode) {
        this.productCode = productCode
    }

    String getProductName() {
        return productName
    }

    void setProductName(String productName) {
        this.productName = productName
    }

    String getSubscriptionId() {
        return subscriptionId
    }

    void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId
    }

    String getSupplyCode() {
        return supplyCode
    }

    void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    ProdutcDetails getProdutcDetails() {
        return produtcDetails
    }

    void setProdutcDetails(ProdutcDetails produtcDetails) {
        this.produtcDetails = produtcDetails
    }
}
