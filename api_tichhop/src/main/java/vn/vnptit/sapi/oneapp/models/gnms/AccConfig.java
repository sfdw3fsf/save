package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.vnptit.sapi.oneapp.models.gnms.AccWebGui;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AccConfig
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AccConfig   {
  @JsonProperty("serialNumber")
  private String serialNumber;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  public AccConfig serialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  /**
   * Số serial của thiết bị
   * @return serialNumber
  */
  @ApiModelProperty(value = "Số serial của thiết bị")


  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public AccConfig username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Tên truy cập
   * @return username
  */
  @ApiModelProperty(value = "Tên truy cập")


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AccConfig password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Mật khẩu đăng nhập vào thiết bị
   * @return password
  */
  @ApiModelProperty(value = "Mật khẩu đăng nhập vào thiết bị")


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccConfig accConfig = (AccConfig) o;
    return Objects.equals(this.serialNumber, accConfig.serialNumber) &&
        Objects.equals(this.username, accConfig.username) &&
        Objects.equals(this.password, accConfig.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serialNumber, username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccConfig {\n");
    
    sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

