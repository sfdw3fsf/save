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
 * AdslDevice
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AdslDevice   {
  @JsonProperty("id")
  private Integer id;

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

  @JsonProperty("ipAddress")
  private String ipAddress;

  @JsonProperty("opticalSerial")
  private String opticalSerial;

  @JsonProperty("connectionRequest")
  private String connectionRequest;

  @JsonProperty("productType")
  private String productType;

  @JsonProperty("productTypeID")
  private String productTypeID;

  @JsonProperty("menID")
  private String menID;

  @JsonProperty("validate")
  private Integer validate;

  @JsonProperty("areaID")
  private String areaID;

  @JsonProperty("areaName")
  private String areaName;

  @JsonProperty("provinceName")
  private String provinceName;

  @JsonProperty("provinceId")
  private String provinceId;

  @JsonProperty("status")
  private Integer status;

  @JsonProperty("lastInformTime")
  private BigDecimal lastInformTime;

  public AdslDevice id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Tên thiết bị
   * @return id
  */
  @ApiModelProperty(value = "Tên thiết bị")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public AdslDevice adslName(String adslName) {
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

  public AdslDevice serialNumber(String serialNumber) {
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

  public AdslDevice productClass(String productClass) {
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

  public AdslDevice oui(String oui) {
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

  public AdslDevice softwareVersion(String softwareVersion) {
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

  public AdslDevice hardwareVersion(String hardwareVersion) {
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

  public AdslDevice manufacturer(String manufacturer) {
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

  public AdslDevice ipAddress(String ipAddress) {
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

  public AdslDevice opticalSerial(String opticalSerial) {
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

  public AdslDevice connectionRequest(String connectionRequest) {
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

  public AdslDevice productType(String productType) {
    this.productType = productType;
    return this;
  }

  /**
   * Get productType
   * @return productType
  */
  @ApiModelProperty(value = "")


  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public AdslDevice productTypeID(String productTypeID) {
    this.productTypeID = productTypeID;
    return this;
  }

  /**
   * Get productTypeID
   * @return productTypeID
  */
  @ApiModelProperty(value = "")


  public String getProductTypeID() {
    return productTypeID;
  }

  public void setProductTypeID(String productTypeID) {
    this.productTypeID = productTypeID;
  }

  public AdslDevice menID(String menID) {
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

  public AdslDevice validate(Integer validate) {
    this.validate = validate;
    return this;
  }

  /**
   * Get validate
   * @return validate
  */
  @ApiModelProperty(value = "")


  public Integer getValidate() {
    return validate;
  }

  public void setValidate(Integer validate) {
    this.validate = validate;
  }

  public AdslDevice areaID(String areaID) {
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

  public AdslDevice areaName(String areaName) {
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

  public AdslDevice provinceName(String provinceName) {
    this.provinceName = provinceName;
    return this;
  }

  /**
   * Get provinceName
   * @return provinceName
  */
  @ApiModelProperty(value = "")


  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public AdslDevice provinceId(String provinceId) {
    this.provinceId = provinceId;
    return this;
  }

  /**
   * Get provinceId
   * @return provinceId
  */
  @ApiModelProperty(value = "")


  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public AdslDevice status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public AdslDevice lastInformTime(BigDecimal lastInformTime) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdslDevice adslDevice = (AdslDevice) o;
    return Objects.equals(this.id, adslDevice.id) &&
        Objects.equals(this.adslName, adslDevice.adslName) &&
        Objects.equals(this.serialNumber, adslDevice.serialNumber) &&
        Objects.equals(this.productClass, adslDevice.productClass) &&
        Objects.equals(this.oui, adslDevice.oui) &&
        Objects.equals(this.softwareVersion, adslDevice.softwareVersion) &&
        Objects.equals(this.hardwareVersion, adslDevice.hardwareVersion) &&
        Objects.equals(this.manufacturer, adslDevice.manufacturer) &&
        Objects.equals(this.ipAddress, adslDevice.ipAddress) &&
        Objects.equals(this.opticalSerial, adslDevice.opticalSerial) &&
        Objects.equals(this.connectionRequest, adslDevice.connectionRequest) &&
        Objects.equals(this.productType, adslDevice.productType) &&
        Objects.equals(this.productTypeID, adslDevice.productTypeID) &&
        Objects.equals(this.menID, adslDevice.menID) &&
        Objects.equals(this.validate, adslDevice.validate) &&
        Objects.equals(this.areaID, adslDevice.areaID) &&
        Objects.equals(this.areaName, adslDevice.areaName) &&
        Objects.equals(this.provinceName, adslDevice.provinceName) &&
        Objects.equals(this.provinceId, adslDevice.provinceId) &&
        Objects.equals(this.status, adslDevice.status) &&
        Objects.equals(this.lastInformTime, adslDevice.lastInformTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, adslName, serialNumber, productClass, oui, softwareVersion, hardwareVersion, manufacturer, ipAddress, opticalSerial, connectionRequest, productType, productTypeID, menID, validate, areaID, areaName, provinceName, provinceId, status, lastInformTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdslDevice {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    adslName: ").append(toIndentedString(adslName)).append("\n");
    sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
    sb.append("    productClass: ").append(toIndentedString(productClass)).append("\n");
    sb.append("    oui: ").append(toIndentedString(oui)).append("\n");
    sb.append("    softwareVersion: ").append(toIndentedString(softwareVersion)).append("\n");
    sb.append("    hardwareVersion: ").append(toIndentedString(hardwareVersion)).append("\n");
    sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    opticalSerial: ").append(toIndentedString(opticalSerial)).append("\n");
    sb.append("    connectionRequest: ").append(toIndentedString(connectionRequest)).append("\n");
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    productTypeID: ").append(toIndentedString(productTypeID)).append("\n");
    sb.append("    menID: ").append(toIndentedString(menID)).append("\n");
    sb.append("    validate: ").append(toIndentedString(validate)).append("\n");
    sb.append("    areaID: ").append(toIndentedString(areaID)).append("\n");
    sb.append("    areaName: ").append(toIndentedString(areaName)).append("\n");
    sb.append("    provinceName: ").append(toIndentedString(provinceName)).append("\n");
    sb.append("    provinceId: ").append(toIndentedString(provinceId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    lastInformTime: ").append(toIndentedString(lastInformTime)).append("\n");
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

