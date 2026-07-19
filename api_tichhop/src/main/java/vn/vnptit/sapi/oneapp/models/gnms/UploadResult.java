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
 * UploadResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UploadResult   {
  @JsonProperty("diagnosticState")
  private String diagnosticState;

  @JsonProperty("testInterface")
  private String testInterface;

  @JsonProperty("uploadUrl")
  private String uploadUrl;

  @JsonProperty("dscp")
  private Integer dscp;

  @JsonProperty("ethernetPriority")
  private Integer ethernetPriority;

  @JsonProperty("romTime")
  private String romTime;

  @JsonProperty("bomTime")
  private String bomTime;

  @JsonProperty("eomTime")
  private String eomTime;

  @JsonProperty("testBytesReceived")
  private BigDecimal testBytesReceived;

  @JsonProperty("totalBytesReceived")
  private BigDecimal totalBytesReceived;

  @JsonProperty("tcpOpenRequestTime")
  private String tcpOpenRequestTime;

  @JsonProperty("tcpOpenResponseTime")
  private String tcpOpenResponseTime;

  @JsonProperty("speed")
  private BigDecimal speed;

  @JsonProperty("deviceTime")
  private String deviceTime;

  @JsonProperty("romTimeInMiliseconds")
  private BigDecimal romTimeInMiliseconds;

  @JsonProperty("bomTimeInMiliseconds")
  private BigDecimal bomTimeInMiliseconds;

  @JsonProperty("eomTimeInMiliseconds")
  private BigDecimal eomTimeInMiliseconds;

  @JsonProperty("tcpopenReqInMiliseconds")
  private BigDecimal tcpopenReqInMiliseconds;

  @JsonProperty("tcpopenResInMiliseconds")
  private BigDecimal tcpopenResInMiliseconds;

  public UploadResult diagnosticState(String diagnosticState) {
    this.diagnosticState = diagnosticState;
    return this;
  }

  /**
   * Get diagnosticState
   * @return diagnosticState
  */
  @ApiModelProperty(value = "")


  public String getDiagnosticState() {
    return diagnosticState;
  }

  public void setDiagnosticState(String diagnosticState) {
    this.diagnosticState = diagnosticState;
  }

  public UploadResult testInterface(String testInterface) {
    this.testInterface = testInterface;
    return this;
  }

  /**
   * Get testInterface
   * @return testInterface
  */
  @ApiModelProperty(value = "")


  public String getTestInterface() {
    return testInterface;
  }

  public void setTestInterface(String testInterface) {
    this.testInterface = testInterface;
  }

  public UploadResult uploadUrl(String uploadUrl) {
    this.uploadUrl = uploadUrl;
    return this;
  }

  /**
   * Get uploadUrl
   * @return uploadUrl
  */
  @ApiModelProperty(value = "")


  public String getUploadUrl() {
    return uploadUrl;
  }

  public void setUploadUrl(String uploadUrl) {
    this.uploadUrl = uploadUrl;
  }

  public UploadResult dscp(Integer dscp) {
    this.dscp = dscp;
    return this;
  }

  /**
   * Get dscp
   * @return dscp
  */
  @ApiModelProperty(value = "")


  public Integer getDscp() {
    return dscp;
  }

  public void setDscp(Integer dscp) {
    this.dscp = dscp;
  }

  public UploadResult ethernetPriority(Integer ethernetPriority) {
    this.ethernetPriority = ethernetPriority;
    return this;
  }

  /**
   * Get ethernetPriority
   * @return ethernetPriority
  */
  @ApiModelProperty(value = "")


  public Integer getEthernetPriority() {
    return ethernetPriority;
  }

  public void setEthernetPriority(Integer ethernetPriority) {
    this.ethernetPriority = ethernetPriority;
  }

  public UploadResult romTime(String romTime) {
    this.romTime = romTime;
    return this;
  }

  /**
   * Get romTime
   * @return romTime
  */
  @ApiModelProperty(value = "")


  public String getRomTime() {
    return romTime;
  }

  public void setRomTime(String romTime) {
    this.romTime = romTime;
  }

  public UploadResult bomTime(String bomTime) {
    this.bomTime = bomTime;
    return this;
  }

  /**
   * Get bomTime
   * @return bomTime
  */
  @ApiModelProperty(value = "")


  public String getBomTime() {
    return bomTime;
  }

  public void setBomTime(String bomTime) {
    this.bomTime = bomTime;
  }

  public UploadResult eomTime(String eomTime) {
    this.eomTime = eomTime;
    return this;
  }

  /**
   * Get eomTime
   * @return eomTime
  */
  @ApiModelProperty(value = "")


  public String getEomTime() {
    return eomTime;
  }

  public void setEomTime(String eomTime) {
    this.eomTime = eomTime;
  }

  public UploadResult testBytesReceived(BigDecimal testBytesReceived) {
    this.testBytesReceived = testBytesReceived;
    return this;
  }

  /**
   * Get testBytesReceived
   * @return testBytesReceived
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTestBytesReceived() {
    return testBytesReceived;
  }

  public void setTestBytesReceived(BigDecimal testBytesReceived) {
    this.testBytesReceived = testBytesReceived;
  }

  public UploadResult totalBytesReceived(BigDecimal totalBytesReceived) {
    this.totalBytesReceived = totalBytesReceived;
    return this;
  }

  /**
   * Get totalBytesReceived
   * @return totalBytesReceived
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalBytesReceived() {
    return totalBytesReceived;
  }

  public void setTotalBytesReceived(BigDecimal totalBytesReceived) {
    this.totalBytesReceived = totalBytesReceived;
  }

  public UploadResult tcpOpenRequestTime(String tcpOpenRequestTime) {
    this.tcpOpenRequestTime = tcpOpenRequestTime;
    return this;
  }

  /**
   * Get tcpOpenRequestTime
   * @return tcpOpenRequestTime
  */
  @ApiModelProperty(value = "")


  public String getTcpOpenRequestTime() {
    return tcpOpenRequestTime;
  }

  public void setTcpOpenRequestTime(String tcpOpenRequestTime) {
    this.tcpOpenRequestTime = tcpOpenRequestTime;
  }

  public UploadResult tcpOpenResponseTime(String tcpOpenResponseTime) {
    this.tcpOpenResponseTime = tcpOpenResponseTime;
    return this;
  }

  /**
   * Get tcpOpenResponseTime
   * @return tcpOpenResponseTime
  */
  @ApiModelProperty(value = "")


  public String getTcpOpenResponseTime() {
    return tcpOpenResponseTime;
  }

  public void setTcpOpenResponseTime(String tcpOpenResponseTime) {
    this.tcpOpenResponseTime = tcpOpenResponseTime;
  }

  public UploadResult speed(BigDecimal speed) {
    this.speed = speed;
    return this;
  }

  /**
   * Get speed
   * @return speed
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSpeed() {
    return speed;
  }

  public void setSpeed(BigDecimal speed) {
    this.speed = speed;
  }

  public UploadResult deviceTime(String deviceTime) {
    this.deviceTime = deviceTime;
    return this;
  }

  /**
   * Get deviceTime
   * @return deviceTime
  */
  @ApiModelProperty(value = "")


  public String getDeviceTime() {
    return deviceTime;
  }

  public void setDeviceTime(String deviceTime) {
    this.deviceTime = deviceTime;
  }

  public UploadResult romTimeInMiliseconds(BigDecimal romTimeInMiliseconds) {
    this.romTimeInMiliseconds = romTimeInMiliseconds;
    return this;
  }

  /**
   * Get romTimeInMiliseconds
   * @return romTimeInMiliseconds
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRomTimeInMiliseconds() {
    return romTimeInMiliseconds;
  }

  public void setRomTimeInMiliseconds(BigDecimal romTimeInMiliseconds) {
    this.romTimeInMiliseconds = romTimeInMiliseconds;
  }

  public UploadResult bomTimeInMiliseconds(BigDecimal bomTimeInMiliseconds) {
    this.bomTimeInMiliseconds = bomTimeInMiliseconds;
    return this;
  }

  /**
   * Get bomTimeInMiliseconds
   * @return bomTimeInMiliseconds
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBomTimeInMiliseconds() {
    return bomTimeInMiliseconds;
  }

  public void setBomTimeInMiliseconds(BigDecimal bomTimeInMiliseconds) {
    this.bomTimeInMiliseconds = bomTimeInMiliseconds;
  }

  public UploadResult eomTimeInMiliseconds(BigDecimal eomTimeInMiliseconds) {
    this.eomTimeInMiliseconds = eomTimeInMiliseconds;
    return this;
  }

  /**
   * Get eomTimeInMiliseconds
   * @return eomTimeInMiliseconds
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEomTimeInMiliseconds() {
    return eomTimeInMiliseconds;
  }

  public void setEomTimeInMiliseconds(BigDecimal eomTimeInMiliseconds) {
    this.eomTimeInMiliseconds = eomTimeInMiliseconds;
  }

  public UploadResult tcpopenReqInMiliseconds(BigDecimal tcpopenReqInMiliseconds) {
    this.tcpopenReqInMiliseconds = tcpopenReqInMiliseconds;
    return this;
  }

  /**
   * Get tcpopenReqInMiliseconds
   * @return tcpopenReqInMiliseconds
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTcpopenReqInMiliseconds() {
    return tcpopenReqInMiliseconds;
  }

  public void setTcpopenReqInMiliseconds(BigDecimal tcpopenReqInMiliseconds) {
    this.tcpopenReqInMiliseconds = tcpopenReqInMiliseconds;
  }

  public UploadResult tcpopenResInMiliseconds(BigDecimal tcpopenResInMiliseconds) {
    this.tcpopenResInMiliseconds = tcpopenResInMiliseconds;
    return this;
  }

  /**
   * Get tcpopenResInMiliseconds
   * @return tcpopenResInMiliseconds
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTcpopenResInMiliseconds() {
    return tcpopenResInMiliseconds;
  }

  public void setTcpopenResInMiliseconds(BigDecimal tcpopenResInMiliseconds) {
    this.tcpopenResInMiliseconds = tcpopenResInMiliseconds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UploadResult uploadResult = (UploadResult) o;
    return Objects.equals(this.diagnosticState, uploadResult.diagnosticState) &&
        Objects.equals(this.testInterface, uploadResult.testInterface) &&
        Objects.equals(this.uploadUrl, uploadResult.uploadUrl) &&
        Objects.equals(this.dscp, uploadResult.dscp) &&
        Objects.equals(this.ethernetPriority, uploadResult.ethernetPriority) &&
        Objects.equals(this.romTime, uploadResult.romTime) &&
        Objects.equals(this.bomTime, uploadResult.bomTime) &&
        Objects.equals(this.eomTime, uploadResult.eomTime) &&
        Objects.equals(this.testBytesReceived, uploadResult.testBytesReceived) &&
        Objects.equals(this.totalBytesReceived, uploadResult.totalBytesReceived) &&
        Objects.equals(this.tcpOpenRequestTime, uploadResult.tcpOpenRequestTime) &&
        Objects.equals(this.tcpOpenResponseTime, uploadResult.tcpOpenResponseTime) &&
        Objects.equals(this.speed, uploadResult.speed) &&
        Objects.equals(this.deviceTime, uploadResult.deviceTime) &&
        Objects.equals(this.romTimeInMiliseconds, uploadResult.romTimeInMiliseconds) &&
        Objects.equals(this.bomTimeInMiliseconds, uploadResult.bomTimeInMiliseconds) &&
        Objects.equals(this.eomTimeInMiliseconds, uploadResult.eomTimeInMiliseconds) &&
        Objects.equals(this.tcpopenReqInMiliseconds, uploadResult.tcpopenReqInMiliseconds) &&
        Objects.equals(this.tcpopenResInMiliseconds, uploadResult.tcpopenResInMiliseconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diagnosticState, testInterface, uploadUrl, dscp, ethernetPriority, romTime, bomTime, eomTime, testBytesReceived, totalBytesReceived, tcpOpenRequestTime, tcpOpenResponseTime, speed, deviceTime, romTimeInMiliseconds, bomTimeInMiliseconds, eomTimeInMiliseconds, tcpopenReqInMiliseconds, tcpopenResInMiliseconds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UploadResult {\n");
    
    sb.append("    diagnosticState: ").append(toIndentedString(diagnosticState)).append("\n");
    sb.append("    testInterface: ").append(toIndentedString(testInterface)).append("\n");
    sb.append("    uploadUrl: ").append(toIndentedString(uploadUrl)).append("\n");
    sb.append("    dscp: ").append(toIndentedString(dscp)).append("\n");
    sb.append("    ethernetPriority: ").append(toIndentedString(ethernetPriority)).append("\n");
    sb.append("    romTime: ").append(toIndentedString(romTime)).append("\n");
    sb.append("    bomTime: ").append(toIndentedString(bomTime)).append("\n");
    sb.append("    eomTime: ").append(toIndentedString(eomTime)).append("\n");
    sb.append("    testBytesReceived: ").append(toIndentedString(testBytesReceived)).append("\n");
    sb.append("    totalBytesReceived: ").append(toIndentedString(totalBytesReceived)).append("\n");
    sb.append("    tcpOpenRequestTime: ").append(toIndentedString(tcpOpenRequestTime)).append("\n");
    sb.append("    tcpOpenResponseTime: ").append(toIndentedString(tcpOpenResponseTime)).append("\n");
    sb.append("    speed: ").append(toIndentedString(speed)).append("\n");
    sb.append("    deviceTime: ").append(toIndentedString(deviceTime)).append("\n");
    sb.append("    romTimeInMiliseconds: ").append(toIndentedString(romTimeInMiliseconds)).append("\n");
    sb.append("    bomTimeInMiliseconds: ").append(toIndentedString(bomTimeInMiliseconds)).append("\n");
    sb.append("    eomTimeInMiliseconds: ").append(toIndentedString(eomTimeInMiliseconds)).append("\n");
    sb.append("    tcpopenReqInMiliseconds: ").append(toIndentedString(tcpopenReqInMiliseconds)).append("\n");
    sb.append("    tcpopenResInMiliseconds: ").append(toIndentedString(tcpopenResInMiliseconds)).append("\n");
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

