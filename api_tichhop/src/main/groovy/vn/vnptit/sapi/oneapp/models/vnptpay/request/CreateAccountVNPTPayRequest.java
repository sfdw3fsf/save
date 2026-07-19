package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CreateAccountVNPTPayRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class CreateAccountVNPTPayRequest {
  @JsonProperty("soDienThoai")
  private String phone = null;
  @JsonProperty("tenKhachHang")
  private String name = null;
  @JsonProperty("soGiayTo")
  private String id = null;
  @JsonProperty("maThamChieu")
  private String refCode = null;
  @JsonProperty("otpId")
  private String otpId = null;
  @JsonProperty("otp")
  private String otp = null;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRefCode() {
    return refCode;
  }

  public void setRefCode(String refCode) {
    this.refCode = refCode;
  }

  public String getOtpId() {
    return otpId;
  }

  public void setOtpId(String otpId) {
    this.otpId = otpId;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }
}
