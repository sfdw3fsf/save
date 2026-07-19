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
 * TemplateInf
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TemplateInf   {
  @JsonProperty("pppoe_user")
  private String pppoeUser;

  @JsonProperty("pppoe_vlanid")
  private String pppoeVlanid;

  @JsonProperty("wifi_mytv_pass")
  private String wifiMytvPass;

  @JsonProperty("wifi_name")
  private String wifiName;

  @JsonProperty("wifi_5g_pass")
  private String wifi5gPass;

  @JsonProperty("serial")
  private String serial;

  @JsonProperty("bridge_vlanid")
  private String bridgeVlanid;

  @JsonProperty("wifi_mytv_name")
  private String wifiMytvName;

  @JsonProperty("wifi_pass")
  private String wifiPass;

  @JsonProperty("pppoe_pass")
  private String pppoePass;

  @JsonProperty("wifi_5g_name")
  private String wifi5gName;

  public TemplateInf pppoeUser(String pppoeUser) {
    this.pppoeUser = pppoeUser;
    return this;
  }

  /**
   * Get pppoeUser
   * @return pppoeUser
  */
  @ApiModelProperty(value = "")


  public String getPppoeUser() {
    return pppoeUser;
  }

  public void setPppoeUser(String pppoeUser) {
    this.pppoeUser = pppoeUser;
  }

  public TemplateInf pppoeVlanid(String pppoeVlanid) {
    this.pppoeVlanid = pppoeVlanid;
    return this;
  }

  /**
   * Get pppoeVlanid
   * @return pppoeVlanid
  */
  @ApiModelProperty(value = "")


  public String getPppoeVlanid() {
    return pppoeVlanid;
  }

  public void setPppoeVlanid(String pppoeVlanid) {
    this.pppoeVlanid = pppoeVlanid;
  }

  public TemplateInf wifiMytvPass(String wifiMytvPass) {
    this.wifiMytvPass = wifiMytvPass;
    return this;
  }

  /**
   * Get wifiMytvPass
   * @return wifiMytvPass
  */
  @ApiModelProperty(value = "")


  public String getWifiMytvPass() {
    return wifiMytvPass;
  }

  public void setWifiMytvPass(String wifiMytvPass) {
    this.wifiMytvPass = wifiMytvPass;
  }

  public TemplateInf wifiName(String wifiName) {
    this.wifiName = wifiName;
    return this;
  }

  /**
   * Get wifiName
   * @return wifiName
  */
  @ApiModelProperty(value = "")


  public String getWifiName() {
    return wifiName;
  }

  public void setWifiName(String wifiName) {
    this.wifiName = wifiName;
  }

  public TemplateInf wifi5gPass(String wifi5gPass) {
    this.wifi5gPass = wifi5gPass;
    return this;
  }

  /**
   * Get wifi5gPass
   * @return wifi5gPass
  */
  @ApiModelProperty(value = "")


  public String getWifi5gPass() {
    return wifi5gPass;
  }

  public void setWifi5gPass(String wifi5gPass) {
    this.wifi5gPass = wifi5gPass;
  }

  public TemplateInf serial(String serial) {
    this.serial = serial;
    return this;
  }

  /**
   * Get serial
   * @return serial
  */
  @ApiModelProperty(value = "")


  public String getSerial() {
    return serial;
  }

  public void setSerial(String serial) {
    this.serial = serial;
  }

  public TemplateInf bridgeVlanid(String bridgeVlanid) {
    this.bridgeVlanid = bridgeVlanid;
    return this;
  }

  /**
   * Get bridgeVlanid
   * @return bridgeVlanid
  */
  @ApiModelProperty(value = "")


  public String getBridgeVlanid() {
    return bridgeVlanid;
  }

  public void setBridgeVlanid(String bridgeVlanid) {
    this.bridgeVlanid = bridgeVlanid;
  }

  public TemplateInf wifiMytvName(String wifiMytvName) {
    this.wifiMytvName = wifiMytvName;
    return this;
  }

  /**
   * Get wifiMytvName
   * @return wifiMytvName
  */
  @ApiModelProperty(value = "")


  public String getWifiMytvName() {
    return wifiMytvName;
  }

  public void setWifiMytvName(String wifiMytvName) {
    this.wifiMytvName = wifiMytvName;
  }

  public TemplateInf wifiPass(String wifiPass) {
    this.wifiPass = wifiPass;
    return this;
  }

  /**
   * Get wifiPass
   * @return wifiPass
  */
  @ApiModelProperty(value = "")


  public String getWifiPass() {
    return wifiPass;
  }

  public void setWifiPass(String wifiPass) {
    this.wifiPass = wifiPass;
  }

  public TemplateInf pppoePass(String pppoePass) {
    this.pppoePass = pppoePass;
    return this;
  }

  /**
   * Get pppoePass
   * @return pppoePass
  */
  @ApiModelProperty(value = "")


  public String getPppoePass() {
    return pppoePass;
  }

  public void setPppoePass(String pppoePass) {
    this.pppoePass = pppoePass;
  }

  public TemplateInf wifi5gName(String wifi5gName) {
    this.wifi5gName = wifi5gName;
    return this;
  }

  /**
   * Get wifi5gName
   * @return wifi5gName
  */
  @ApiModelProperty(value = "")


  public String getWifi5gName() {
    return wifi5gName;
  }

  public void setWifi5gName(String wifi5gName) {
    this.wifi5gName = wifi5gName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateInf templateInf = (TemplateInf) o;
    return Objects.equals(this.pppoeUser, templateInf.pppoeUser) &&
        Objects.equals(this.pppoeVlanid, templateInf.pppoeVlanid) &&
        Objects.equals(this.wifiMytvPass, templateInf.wifiMytvPass) &&
        Objects.equals(this.wifiName, templateInf.wifiName) &&
        Objects.equals(this.wifi5gPass, templateInf.wifi5gPass) &&
        Objects.equals(this.serial, templateInf.serial) &&
        Objects.equals(this.bridgeVlanid, templateInf.bridgeVlanid) &&
        Objects.equals(this.wifiMytvName, templateInf.wifiMytvName) &&
        Objects.equals(this.wifiPass, templateInf.wifiPass) &&
        Objects.equals(this.pppoePass, templateInf.pppoePass) &&
        Objects.equals(this.wifi5gName, templateInf.wifi5gName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pppoeUser, pppoeVlanid, wifiMytvPass, wifiName, wifi5gPass, serial, bridgeVlanid, wifiMytvName, wifiPass, pppoePass, wifi5gName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateInf {\n");
    
    sb.append("    pppoeUser: ").append(toIndentedString(pppoeUser)).append("\n");
    sb.append("    pppoeVlanid: ").append(toIndentedString(pppoeVlanid)).append("\n");
    sb.append("    wifiMytvPass: ").append(toIndentedString(wifiMytvPass)).append("\n");
    sb.append("    wifiName: ").append(toIndentedString(wifiName)).append("\n");
    sb.append("    wifi5gPass: ").append(toIndentedString(wifi5gPass)).append("\n");
    sb.append("    serial: ").append(toIndentedString(serial)).append("\n");
    sb.append("    bridgeVlanid: ").append(toIndentedString(bridgeVlanid)).append("\n");
    sb.append("    wifiMytvName: ").append(toIndentedString(wifiMytvName)).append("\n");
    sb.append("    wifiPass: ").append(toIndentedString(wifiPass)).append("\n");
    sb.append("    pppoePass: ").append(toIndentedString(pppoePass)).append("\n");
    sb.append("    wifi5gName: ").append(toIndentedString(wifi5gName)).append("\n");
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

