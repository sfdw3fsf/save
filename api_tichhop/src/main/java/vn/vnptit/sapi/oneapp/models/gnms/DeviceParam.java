package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DeviceParam
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DeviceParam   {
  @JsonProperty("adslName")
  private String adslName;

  @JsonProperty("serialNumber")
  private String serialNumber;

  @JsonProperty("productClass")
  private String productClass;

  @JsonProperty("oui")
  private String oui;

  @JsonProperty("softwareVersion")
  private String softwareVersion;

  @JsonProperty("hardwareVersion")
  private String hardwareVersion;

  @JsonProperty("manufacturer")
  private String manufacturer;

  @JsonProperty("provisioningCode")
  private String provisioningCode;

  @JsonProperty("connectionRequest")
  private String connectionRequest;

  @JsonProperty("ipAddress")
  private String ipAddress;

  @JsonProperty("modelName")
  private String modelName;

  @JsonProperty("areaID")
  private String areaID;

  @JsonProperty("areaName")
  private String areaName;

  @JsonProperty("menID")
  private String menID;

  @JsonProperty("productID")
  private Integer productID;

  @JsonProperty("opticalSerial")
  private String opticalSerial;

  @JsonProperty("lastInformTime")
  private BigDecimal lastInformTime;

  @JsonProperty("joinTime")
  private BigDecimal joinTime;

  public DeviceParam adslName(String adslName) {
    this.adslName = adslName;
    return this;
  }

  /**
   * Tên thiết bị
   * @return adslName
  */
  @ApiModelProperty(value = "Tên thiết bị")


  public String getAdslName() {
    return adslName;
  }

  public void setAdslName(String adslName) {
    this.adslName = adslName;
  }

  public DeviceParam serialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  /**
   * Serial của thiết bị
   * @return serialNumber
  */
  @ApiModelProperty(value = "Serial của thiết bị")


  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public DeviceParam productClass(String productClass) {
    this.productClass = productClass;
    return this;
  }

  /**
   * Get productClass
   * @return productClass
  */
  @ApiModelProperty(value = "")


  public String getProductClass() {
    return productClass;
  }

  public void setProductClass(String productClass) {
    this.productClass = productClass;
  }

  public DeviceParam oui(String oui) {
    this.oui = oui;
    return this;
  }

  /**
   * Get oui
   * @return oui
  */
  @ApiModelProperty(value = "")


  public String getOui() {
    return oui;
  }

  public void setOui(String oui) {
    this.oui = oui;
  }

  public DeviceParam softwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
    return this;
  }

  /**
   * Get softwareVersion
   * @return softwareVersion
  */
  @ApiModelProperty(value = "")


  public String getSoftwareVersion() {
    return softwareVersion;
  }

  public void setSoftwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
  }

  public DeviceParam hardwareVersion(String hardwareVersion) {
    this.hardwareVersion = hardwareVersion;
    return this;
  }

  /**
   * Get hardwareVersion
   * @return hardwareVersion
  */
  @ApiModelProperty(value = "")


  public String getHardwareVersion() {
    return hardwareVersion;
  }

  public void setHardwareVersion(String hardwareVersion) {
    this.hardwareVersion = hardwareVersion;
  }

  public DeviceParam manufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  /**
   * Phiên bản Firmware của thiết bị
   * @return manufacturer
  */
  @ApiModelProperty(value = "Phiên bản Firmware của thiết bị")


  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public DeviceParam provisioningCode(String provisioningCode) {
    this.provisioningCode = provisioningCode;
    return this;
  }

  /**
   * Get provisioningCode
   * @return provisioningCode
  */
  @ApiModelProperty(value = "")


  public String getProvisioningCode() {
    return provisioningCode;
  }

  public void setProvisioningCode(String provisioningCode) {
    this.provisioningCode = provisioningCode;
  }

  public DeviceParam connectionRequest(String connectionRequest) {
    this.connectionRequest = connectionRequest;
    return this;
  }

  /**
   * Get connectionRequest
   * @return connectionRequest
  */
  @ApiModelProperty(value = "")


  public String getConnectionRequest() {
    return connectionRequest;
  }

  public void setConnectionRequest(String connectionRequest) {
    this.connectionRequest = connectionRequest;
  }

  public DeviceParam ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  /**
   * Địa chỉ quản lý của thiết bị
   * @return ipAddress
  */
  @ApiModelProperty(value = "Địa chỉ quản lý của thiết bị")


  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public DeviceParam modelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  /**
   * Get modelName
   * @return modelName
  */
  @ApiModelProperty(value = "")


  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public DeviceParam areaID(String areaID) {
    this.areaID = areaID;
    return this;
  }

  /**
   * Mã định danh khu vực của ONT
   * @return areaID
  */
  @ApiModelProperty(value = "Mã định danh khu vực của ONT")


  public String getAreaID() {
    return areaID;
  }

  public void setAreaID(String areaID) {
    this.areaID = areaID;
  }

  public DeviceParam areaName(String areaName) {
    this.areaName = areaName;
    return this;
  }

  /**
   * Tên khu vực
   * @return areaName
  */
  @ApiModelProperty(value = "Tên khu vực")


  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public DeviceParam menID(String menID) {
    this.menID = menID;
    return this;
  }

  /**
   * Mã MEN của thiết bị
   * @return menID
  */
  @ApiModelProperty(value = "Mã MEN của thiết bị")


  public String getMenID() {
    return menID;
  }

  public void setMenID(String menID) {
    this.menID = menID;
  }

  public DeviceParam productID(Integer productID) {
    this.productID = productID;
    return this;
  }

  /**
   * Get productID
   * @return productID
  */
  @ApiModelProperty(value = "")


  public Integer getProductID() {
    return productID;
  }

  public void setProductID(Integer productID) {
    this.productID = productID;
  }

  public DeviceParam opticalSerial(String opticalSerial) {
    this.opticalSerial = opticalSerial;
    return this;
  }

  /**
   * Get opticalSerial
   * @return opticalSerial
  */
  @ApiModelProperty(value = "")


  public String getOpticalSerial() {
    return opticalSerial;
  }

  public void setOpticalSerial(String opticalSerial) {
    this.opticalSerial = opticalSerial;
  }

  public DeviceParam lastInformTime(BigDecimal lastInformTime) {
    this.lastInformTime = lastInformTime;
    return this;
  }

  /**
   * Thời điểm cuối thiết bị gửi bản tin inform về hệ thống
   * @return lastInformTime
  */
  @ApiModelProperty(value = "Thời điểm cuối thiết bị gửi bản tin inform về hệ thống")

  @Valid

  public BigDecimal getLastInformTime() {
    return lastInformTime;
  }

  public void setLastInformTime(BigDecimal lastInformTime) {
    this.lastInformTime = lastInformTime;
  }

  public DeviceParam joinTime(BigDecimal joinTime) {
    this.joinTime = joinTime;
    return this;
  }

  /**
   * Get joinTime
   * @return joinTime
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getJoinTime() {
    return joinTime;
  }

  public void setJoinTime(BigDecimal joinTime) {
    this.joinTime = joinTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceParam deviceParam = (DeviceParam) o;
    return Objects.equals(this.adslName, deviceParam.adslName) &&
        Objects.equals(this.serialNumber, deviceParam.serialNumber) &&
        Objects.equals(this.productClass, deviceParam.productClass) &&
        Objects.equals(this.oui, deviceParam.oui) &&
        Objects.equals(this.softwareVersion, deviceParam.softwareVersion) &&
        Objects.equals(this.hardwareVersion, deviceParam.hardwareVersion) &&
        Objects.equals(this.manufacturer, deviceParam.manufacturer) &&
        Objects.equals(this.provisioningCode, deviceParam.provisioningCode) &&
        Objects.equals(this.connectionRequest, deviceParam.connectionRequest) &&
        Objects.equals(this.ipAddress, deviceParam.ipAddress) &&
        Objects.equals(this.modelName, deviceParam.modelName) &&
        Objects.equals(this.areaID, deviceParam.areaID) &&
        Objects.equals(this.areaName, deviceParam.areaName) &&
        Objects.equals(this.menID, deviceParam.menID) &&
        Objects.equals(this.productID, deviceParam.productID) &&
        Objects.equals(this.opticalSerial, deviceParam.opticalSerial) &&
        Objects.equals(this.lastInformTime, deviceParam.lastInformTime) &&
        Objects.equals(this.joinTime, deviceParam.joinTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(adslName, serialNumber, productClass, oui, softwareVersion, hardwareVersion, manufacturer, provisioningCode, connectionRequest, ipAddress, modelName, areaID, areaName, menID, productID, opticalSerial, lastInformTime, joinTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceParam {\n");
    
    sb.append("    adslName: ").append(toIndentedString(adslName)).append("\n");
    sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
    sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
    sb.append("    oui: ").append(toIndentedString(oui)).append("\n");
    sb.append("    softwareVersion: ").append(toIndentedString(softwareVersion)).append("\n");
    sb.append("    hardwareVersion: ").append(toIndentedString(hardwareVersion)).append("\n");
    sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
    sb.append("    provisioningCode: ").append(toIndentedString(provisioningCode)).append("\n");
    sb.append("    connectionRequest: ").append(toIndentedString(connectionRequest)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    modelName: ").append(toIndentedString(modelName)).append("\n");
    sb.append("    areaID: ").append(toIndentedString(areaID)).append("\n");
    sb.append("    areaName: ").append(toIndentedString(areaName)).append("\n");
    sb.append("    menID: ").append(toIndentedString(menID)).append("\n");
    sb.append("    productID: ").append(toIndentedString(productID)).append("\n");
    sb.append("    opticalSerial: ").append(toIndentedString(opticalSerial)).append("\n");
    sb.append("    lastInformTime: ").append(toIndentedString(lastInformTime)).append("\n");
    sb.append("    joinTime: ").append(toIndentedString(joinTime)).append("\n");
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

