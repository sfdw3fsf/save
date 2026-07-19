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
 * DownloadResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DownloadResult   {
  @JsonProperty("diagnosticState")
  private String diagnosticState;

  @JsonProperty("testInterface")
  private String testInterface;

  @JsonProperty("downloadUrl")
  private String downloadUrl;

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

  public DownloadResult diagnosticState(String diagnosticState) {
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

  public DownloadResult testInterface(String testInterface) {
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

  public DownloadResult downloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
    return this;
  }

  /**
   * Get downloadUrl
   * @return downloadUrl
  */
  @ApiModelProperty(value = "")


  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  public DownloadResult dscp(Integer dscp) {
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

  public DownloadResult ethernetPriority(Integer ethernetPriority) {
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

  public DownloadResult romTime(String romTime) {
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

  public DownloadResult bomTime(String bomTime) {
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

  public DownloadResult eomTime(String eomTime) {
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

  public DownloadResult testBytesReceived(BigDecimal testBytesReceived) {
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

  public DownloadResult totalBytesReceived(BigDecimal totalBytesReceived) {
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

  public DownloadResult tcpOpenRequestTime(String tcpOpenRequestTime) {
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

  public DownloadResult tcpOpenResponseTime(String tcpOpenResponseTime) {
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

  public DownloadResult speed(BigDecimal speed) {
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

  public DownloadResult deviceTime(String deviceTime) {
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

  public DownloadResult romTimeInMiliseconds(BigDecimal romTimeInMiliseconds) {
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

  public DownloadResult bomTimeInMiliseconds(BigDecimal bomTimeInMiliseconds) {
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

  public DownloadResult eomTimeInMiliseconds(BigDecimal eomTimeInMiliseconds) {
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

  public DownloadResult tcpopenReqInMiliseconds(BigDecimal tcpopenReqInMiliseconds) {
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

  public DownloadResult tcpopenResInMiliseconds(BigDecimal tcpopenResInMiliseconds) {
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
    DownloadResult downloadResult = (DownloadResult) o;
    return Objects.equals(this.diagnosticState, downloadResult.diagnosticState) &&
        Objects.equals(this.testInterface, downloadResult.testInterface) &&
        Objects.equals(this.downloadUrl, downloadResult.downloadUrl) &&
        Objects.equals(this.dscp, downloadResult.dscp) &&
        Objects.equals(this.ethernetPriority, downloadResult.ethernetPriority) &&
        Objects.equals(this.romTime, downloadResult.romTime) &&
        Objects.equals(this.bomTime, downloadResult.bomTime) &&
        Objects.equals(this.eomTime, downloadResult.eomTime) &&
        Objects.equals(this.testBytesReceived, downloadResult.testBytesReceived) &&
        Objects.equals(this.totalBytesReceived, downloadResult.totalBytesReceived) &&
        Objects.equals(this.tcpOpenRequestTime, downloadResult.tcpOpenRequestTime) &&
        Objects.equals(this.tcpOpenResponseTime, downloadResult.tcpOpenResponseTime) &&
        Objects.equals(this.speed, downloadResult.speed) &&
        Objects.equals(this.deviceTime, downloadResult.deviceTime) &&
        Objects.equals(this.romTimeInMiliseconds, downloadResult.romTimeInMiliseconds) &&
        Objects.equals(this.bomTimeInMiliseconds, downloadResult.bomTimeInMiliseconds) &&
        Objects.equals(this.eomTimeInMiliseconds, downloadResult.eomTimeInMiliseconds) &&
        Objects.equals(this.tcpopenReqInMiliseconds, downloadResult.tcpopenReqInMiliseconds) &&
        Objects.equals(this.tcpopenResInMiliseconds, downloadResult.tcpopenResInMiliseconds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diagnosticState, testInterface, downloadUrl, dscp, ethernetPriority, romTime, bomTime, eomTime, testBytesReceived, totalBytesReceived, tcpOpenRequestTime, tcpOpenResponseTime, speed, deviceTime, romTimeInMiliseconds, bomTimeInMiliseconds, eomTimeInMiliseconds, tcpopenReqInMiliseconds, tcpopenResInMiliseconds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DownloadResult {\n");
    
    sb.append("    diagnosticState: ").append(toIndentedString(diagnosticState)).append("\n");
    sb.append("    testInterface: ").append(toIndentedString(testInterface)).append("\n");
    sb.append("    downloadUrl: ").append(toIndentedString(downloadUrl)).append("\n");
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

