package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.vtc.models.ProdutcDetails

@JsonIgnoreProperties(["metaClass"])
class ThongTinSpKasRes {
    private long status;
    private String statusDesc;
    private String subProductName;
    private long subProductCode;
    private String refId;
    private long productCode;
    private String productName;
    private ThongTinSpDetailsKasRes detail;

    @JsonProperty("status")
    public long getStatus() { return status; }

    @JsonProperty("status")
    public void setStatus(long value) { this.status = value; }

    @JsonProperty("statusDesc")
    public String getStatusDesc() { return statusDesc; }

    @JsonProperty("statusDesc")
    public void setStatusDesc(String value) { this.statusDesc = value; }

    @JsonProperty("subProductName")
    public String getSubProductName() { return subProductName; }

    @JsonProperty("subProductName")
    public void setSubProductName(String value) { this.subProductName = value; }

    @JsonProperty("subProductCode")
    public long getSubProductCode() { return subProductCode; }

    @JsonProperty("subProductCode")
    public void setSubProductCode(long value) { this.subProductCode = value; }

    @JsonProperty("refId")
    public String getRefId() { return refId; }

    @JsonProperty("refId")
    public void setRefId(String value) { this.refId = value; }

    @JsonProperty("productCode")
    public long getProductCode() { return productCode; }

    @JsonProperty("productCode")
    public void setProductCode(long value) { this.productCode = value; }

    @JsonProperty("productName")
    public String getProductName() { return productName; }

    @JsonProperty("productName")
    public void setProductName(String value) { this.productName = value; }

    @JsonProperty("detail")
    public ThongTinSpDetailsKasRes getDetail() { return detail; }

    @JsonProperty("detail")
    public void setDetail(ThongTinSpDetailsKasRes value) { this.detail = value; }
}
