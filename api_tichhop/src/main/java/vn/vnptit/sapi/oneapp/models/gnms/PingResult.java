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
 * PingResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class PingResult   {
  @JsonProperty("diagnosticsState")
  private String diagnosticsState;

  @JsonProperty("host")
  private String host;

  @JsonProperty("dscp")
  private Integer dscp;

  @JsonProperty("numberOfRepetitions")
  private Integer numberOfRepetitions;

  @JsonProperty("timeout")
  private Integer timeout;

  @JsonProperty("dataBlockSize")
  private Integer dataBlockSize;

  @JsonProperty("successCount")
  private Integer successCount;

  @JsonProperty("failureCount")
  private Integer failureCount;

  @JsonProperty("averageResponseTime")
  private Integer averageResponseTime;

  @JsonProperty("minimumResponseTime")
  private Integer minimumResponseTime;

  @JsonProperty("maximumResponseTime")
  private Integer maximumResponseTime;

  @JsonProperty("jitter")
  private Integer jitter;

  @JsonProperty("deviceTime")
  private String deviceTime;

  public PingResult diagnosticsState(String diagnosticsState) {
    this.diagnosticsState = diagnosticsState;
    return this;
  }

  /**
   * Get diagnosticsState
   * @return diagnosticsState
  */
  @ApiModelProperty(value = "")


  public String getDiagnosticsState() {
    return diagnosticsState;
  }

  public void setDiagnosticsState(String diagnosticsState) {
    this.diagnosticsState = diagnosticsState;
  }

  public PingResult host(String host) {
    this.host = host;
    return this;
  }

  /**
   * Get host
   * @return host
  */
  @ApiModelProperty(value = "")


  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public PingResult dscp(Integer dscp) {
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

  public PingResult numberOfRepetitions(Integer numberOfRepetitions) {
    this.numberOfRepetitions = numberOfRepetitions;
    return this;
  }

  /**
   * Get numberOfRepetitions
   * @return numberOfRepetitions
  */
  @ApiModelProperty(value = "")


  public Integer getNumberOfRepetitions() {
    return numberOfRepetitions;
  }

  public void setNumberOfRepetitions(Integer numberOfRepetitions) {
    this.numberOfRepetitions = numberOfRepetitions;
  }

  public PingResult timeout(Integer timeout) {
    this.timeout = timeout;
    return this;
  }

  /**
   * Get timeout
   * @return timeout
  */
  @ApiModelProperty(value = "")


  public Integer getTimeout() {
    return timeout;
  }

  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }

  public PingResult dataBlockSize(Integer dataBlockSize) {
    this.dataBlockSize = dataBlockSize;
    return this;
  }

  /**
   * Get dataBlockSize
   * @return dataBlockSize
  */
  @ApiModelProperty(value = "")


  public Integer getDataBlockSize() {
    return dataBlockSize;
  }

  public void setDataBlockSize(Integer dataBlockSize) {
    this.dataBlockSize = dataBlockSize;
  }

  public PingResult successCount(Integer successCount) {
    this.successCount = successCount;
    return this;
  }

  /**
   * Get successCount
   * @return successCount
  */
  @ApiModelProperty(value = "")


  public Integer getSuccessCount() {
    return successCount;
  }

  public void setSuccessCount(Integer successCount) {
    this.successCount = successCount;
  }

  public PingResult failureCount(Integer failureCount) {
    this.failureCount = failureCount;
    return this;
  }

  /**
   * Get failureCount
   * @return failureCount
  */
  @ApiModelProperty(value = "")


  public Integer getFailureCount() {
    return failureCount;
  }

  public void setFailureCount(Integer failureCount) {
    this.failureCount = failureCount;
  }

  public PingResult averageResponseTime(Integer averageResponseTime) {
    this.averageResponseTime = averageResponseTime;
    return this;
  }

  /**
   * Get averageResponseTime
   * @return averageResponseTime
  */
  @ApiModelProperty(value = "")


  public Integer getAverageResponseTime() {
    return averageResponseTime;
  }

  public void setAverageResponseTime(Integer averageResponseTime) {
    this.averageResponseTime = averageResponseTime;
  }

  public PingResult minimumResponseTime(Integer minimumResponseTime) {
    this.minimumResponseTime = minimumResponseTime;
    return this;
  }

  /**
   * Get minimumResponseTime
   * @return minimumResponseTime
  */
  @ApiModelProperty(value = "")


  public Integer getMinimumResponseTime() {
    return minimumResponseTime;
  }

  public void setMinimumResponseTime(Integer minimumResponseTime) {
    this.minimumResponseTime = minimumResponseTime;
  }

  public PingResult maximumResponseTime(Integer maximumResponseTime) {
    this.maximumResponseTime = maximumResponseTime;
    return this;
  }

  /**
   * Get maximumResponseTime
   * @return maximumResponseTime
  */
  @ApiModelProperty(value = "")


  public Integer getMaximumResponseTime() {
    return maximumResponseTime;
  }

  public void setMaximumResponseTime(Integer maximumResponseTime) {
    this.maximumResponseTime = maximumResponseTime;
  }

  public PingResult jitter(Integer jitter) {
    this.jitter = jitter;
    return this;
  }

  /**
   * Get jitter
   * @return jitter
  */
  @ApiModelProperty(value = "")


  public Integer getJitter() {
    return jitter;
  }

  public void setJitter(Integer jitter) {
    this.jitter = jitter;
  }

  public PingResult deviceTime(String deviceTime) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PingResult pingResult = (PingResult) o;
    return Objects.equals(this.diagnosticsState, pingResult.diagnosticsState) &&
        Objects.equals(this.host, pingResult.host) &&
        Objects.equals(this.dscp, pingResult.dscp) &&
        Objects.equals(this.numberOfRepetitions, pingResult.numberOfRepetitions) &&
        Objects.equals(this.timeout, pingResult.timeout) &&
        Objects.equals(this.dataBlockSize, pingResult.dataBlockSize) &&
        Objects.equals(this.successCount, pingResult.successCount) &&
        Objects.equals(this.failureCount, pingResult.failureCount) &&
        Objects.equals(this.averageResponseTime, pingResult.averageResponseTime) &&
        Objects.equals(this.minimumResponseTime, pingResult.minimumResponseTime) &&
        Objects.equals(this.maximumResponseTime, pingResult.maximumResponseTime) &&
        Objects.equals(this.jitter, pingResult.jitter) &&
        Objects.equals(this.deviceTime, pingResult.deviceTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(diagnosticsState, host, dscp, numberOfRepetitions, timeout, dataBlockSize, successCount, failureCount, averageResponseTime, minimumResponseTime, maximumResponseTime, jitter, deviceTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PingResult {\n");
    
    sb.append("    diagnosticsState: ").append(toIndentedString(diagnosticsState)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    dscp: ").append(toIndentedString(dscp)).append("\n");
    sb.append("    numberOfRepetitions: ").append(toIndentedString(numberOfRepetitions)).append("\n");
    sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
    sb.append("    dataBlockSize: ").append(toIndentedString(dataBlockSize)).append("\n");
    sb.append("    successCount: ").append(toIndentedString(successCount)).append("\n");
    sb.append("    failureCount: ").append(toIndentedString(failureCount)).append("\n");
    sb.append("    averageResponseTime: ").append(toIndentedString(averageResponseTime)).append("\n");
    sb.append("    minimumResponseTime: ").append(toIndentedString(minimumResponseTime)).append("\n");
    sb.append("    maximumResponseTime: ").append(toIndentedString(maximumResponseTime)).append("\n");
    sb.append("    jitter: ").append(toIndentedString(jitter)).append("\n");
    sb.append("    deviceTime: ").append(toIndentedString(deviceTime)).append("\n");
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

