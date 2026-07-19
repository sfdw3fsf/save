package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CreateQRCodeRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class CreateQRCodeRequest {
  @JsonProperty("loaiThanhToan")
  private Integer loaiThanhToan = null;
  @JsonProperty("billNumber")
  private String billNumber = null;
  @JsonProperty("amount")
  private String amount = null;
  @JsonProperty("consumerId")
  private String consumerId = null;
  @JsonProperty("purpose")
  private String purpose = null;
  @JsonProperty("provinceCode")
  private String provinceCode = null;
  @JsonProperty("addInfo")
  private String addInfo = null;
  @JsonProperty("systemInfo")
  private String systemInfo = null;

  public Integer getLoaiThanhToan() {
    return loaiThanhToan;
  }

  public void setLoaiThanhToan(Integer loaiThanhToan) {
    this.loaiThanhToan = loaiThanhToan;
  }

  public String getBillNumber() {
    return billNumber;
  }

  public void setBillNumber(String billNumber) {
    this.billNumber = billNumber;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(String consumerId) {
    this.consumerId = consumerId;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getAddInfo() {
    return addInfo;
  }

  public void setAddInfo(String addInfo) {
    this.addInfo = addInfo;
  }

  public String getSystemInfo() {
    return systemInfo;
  }

  public void setSystemInfo(String systemInfo) {
    this.systemInfo = systemInfo;
  }
}
