package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThongTinSpKasKeyRes {
    private String subProductName;
    private long subProductCode;
    private String refId;
    private long productCode;
    private String productName;
    private ThongTinSpDetailsKasRes detail;

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
