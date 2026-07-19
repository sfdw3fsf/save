package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThanhToanBHRequest {
    @JsonProperty("phone")
    public String phone
    @JsonProperty("type")
    public String type
    @JsonProperty("stockId")
    public String stockId
    @JsonProperty("email")
    public String email
    @JsonProperty("agentCode")
    public String agentCode
    @JsonProperty("address")
    public String address
    @JsonProperty("eInvoice")
    public String eInvoice
    @JsonProperty("taxCode")
    public String taxCode
    @JsonProperty("name")
    public String name
    @JsonProperty("isPromotion")
    public String isPromotion
    @JsonProperty("reasonCode")
    public String reasonCode
    @JsonProperty("productDetail")
    public String productDetail
    @JsonProperty("account")
    public String account

    @JsonProperty("maDvqhns")
    public String maDvqhns
    @JsonProperty("soCccd")
    public String soCccd
    @JsonProperty("paymentType")
    public String paymentType

    String getPaymentType() {
        return paymentType
    }

    void setPaymentType(String paymentType) {
        this.paymentType = paymentType
    }

    String getMaDvqhns() {
        return maDvqhns
    }

    void setMaDvqhns(String maDvqhns) {
        this.maDvqhns = maDvqhns
    }

    String getSoCccd() {
        return soCccd
    }

    void setSoCccd(String soCccd) {
        this.soCccd = soCccd
    }

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getProductDetail() {
        return productDetail
    }

    void setProductDetail(String productDetail) {
        this.productDetail = productDetail
    }

    String getIsPromotion() {
        return isPromotion
    }

    void setIsPromotion(String isPromotion) {
        this.isPromotion = isPromotion
    }

    String getReasonCode() {
        return reasonCode
    }

    void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getStockId() {
        return stockId
    }

    void setStockId(String stockId) {
        this.stockId = stockId
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getAgentCode() {
        return agentCode
    }

    void setAgentCode(String agentCode) {
        this.agentCode = agentCode
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }

    String geteInvoice() {
        return eInvoice
    }

    void seteInvoice(String eInvoice) {
        this.eInvoice = eInvoice
    }

    String getTaxCode() {
        return taxCode
    }

    void setTaxCode(String taxCode) {
        this.taxCode = taxCode
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }
}