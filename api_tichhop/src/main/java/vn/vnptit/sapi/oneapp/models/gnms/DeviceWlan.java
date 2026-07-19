package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DeviceWlan
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DeviceWlan   {
  @JsonProperty("enable")
  private Boolean enable;

  @JsonProperty("ssid")
  private String ssid;

  @JsonProperty("authMode")
  private String authMode;

  @JsonProperty("password")
  private String password;

  @JsonProperty("encrypt")
  private String encrypt;

  @JsonProperty("type")
  private String type;

  @JsonProperty("index")
  private Integer index;

  public DeviceWlan enable(Boolean enable) {
    this.enable = enable;
    return this;
  }

  /**
   * Trạng thái bật/tắt
   * @return enable
  */
  @ApiModelProperty(value = "Trạng thái bật/tắt")


  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public DeviceWlan ssid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  /**
   * Tên ssid
   * @return ssid
  */
  @ApiModelProperty(value = "Tên ssid")


  public String getSsid() {
    return ssid;
  }

  public void setSsid(String ssid) {
    this.ssid = ssid;
  }

  public DeviceWlan authMode(String authMode) {
    this.authMode = authMode;
    return this;
  }

  /**
   * Xác thực
   * @return authMode
  */
  @ApiModelProperty(value = "Xác thực")


  public String getAuthMode() {
    return authMode;
  }

  public void setAuthMode(String authMode) {
    this.authMode = authMode;
  }

  public DeviceWlan password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Mật khẩu
   * @return password
  */
  @ApiModelProperty(value = "Mật khẩu")


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public DeviceWlan encrypt(String encrypt) {
    this.encrypt = encrypt;
    return this;
  }

  /**
   * Chế độ mã hóa
   * @return encrypt
  */
  @ApiModelProperty(value = "Chế độ mã hóa")


  public String getEncrypt() {
    return encrypt;
  }

  public void setEncrypt(String encrypt) {
    this.encrypt = encrypt;
  }

  public DeviceWlan type(String type) {
    this.type = type;
    return this;
  }

  /**
   * loại wifi:   * main_wifi   * wifi_guest_0   * ... 
   * @return type
  */
  @ApiModelProperty(value = "loại wifi:   * main_wifi   * wifi_guest_0   * ... ")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public DeviceWlan index(Integer index) {
    this.index = index;
    return this;
  }

  /**
   * Get index
   * @return index
  */
  @ApiModelProperty(value = "")


  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceWlan deviceWlan = (DeviceWlan) o;
    return Objects.equals(this.enable, deviceWlan.enable) &&
        Objects.equals(this.ssid, deviceWlan.ssid) &&
        Objects.equals(this.authMode, deviceWlan.authMode) &&
        Objects.equals(this.password, deviceWlan.password) &&
        Objects.equals(this.encrypt, deviceWlan.encrypt) &&
        Objects.equals(this.type, deviceWlan.type) &&
        Objects.equals(this.index, deviceWlan.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enable, ssid, authMode, password, encrypt, type, index);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceWlan {\n");
    
    sb.append("    enable: ").append(toIndentedString(enable)).append("\n");
    sb.append("    ssid: ").append(toIndentedString(ssid)).append("\n");
    sb.append("    authMode: ").append(toIndentedString(authMode)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    encrypt: ").append(toIndentedString(encrypt)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

