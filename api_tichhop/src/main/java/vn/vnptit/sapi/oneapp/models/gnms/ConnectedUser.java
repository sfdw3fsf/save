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
 * ConnectedUser
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ConnectedUser   {
  @JsonProperty("macAddress")
  private String macAddress;

  @JsonProperty("isActive")
  private Boolean isActive;

  @JsonProperty("addressSource")
  private String addressSource;

  @JsonProperty("hostName")
  private String hostName;

  @JsonProperty("ipAddress")
  private String ipAddress;

  @JsonProperty("interfaceType")
  private String interfaceType;

  @JsonProperty("leaseTimeRemaining")
  private BigDecimal leaseTimeRemaining;

  @JsonProperty("ifName")
  private String ifName;

  public ConnectedUser macAddress(String macAddress) {
    this.macAddress = macAddress;
    return this;
  }

  /**
   * Get macAddress
   * @return macAddress
  */
  @ApiModelProperty(value = "")


  public String getMacAddress() {
    return macAddress;
  }

  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  public ConnectedUser isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * Get isActive
   * @return isActive
  */
  @ApiModelProperty(value = "")


  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public ConnectedUser addressSource(String addressSource) {
    this.addressSource = addressSource;
    return this;
  }

  /**
   * Get addressSource
   * @return addressSource
  */
  @ApiModelProperty(value = "")


  public String getAddressSource() {
    return addressSource;
  }

  public void setAddressSource(String addressSource) {
    this.addressSource = addressSource;
  }

  public ConnectedUser hostName(String hostName) {
    this.hostName = hostName;
    return this;
  }

  /**
   * Get hostName
   * @return hostName
  */
  @ApiModelProperty(value = "")


  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public ConnectedUser ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  /**
   * Get ipAddress
   * @return ipAddress
  */
  @ApiModelProperty(value = "")


  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public ConnectedUser interfaceType(String interfaceType) {
    this.interfaceType = interfaceType;
    return this;
  }

  /**
   * Get interfaceType
   * @return interfaceType
  */
  @ApiModelProperty(value = "")


  public String getInterfaceType() {
    return interfaceType;
  }

  public void setInterfaceType(String interfaceType) {
    this.interfaceType = interfaceType;
  }

  public ConnectedUser leaseTimeRemaining(BigDecimal leaseTimeRemaining) {
    this.leaseTimeRemaining = leaseTimeRemaining;
    return this;
  }

  /**
   * Get leaseTimeRemaining
   * @return leaseTimeRemaining
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLeaseTimeRemaining() {
    return leaseTimeRemaining;
  }

  public void setLeaseTimeRemaining(BigDecimal leaseTimeRemaining) {
    this.leaseTimeRemaining = leaseTimeRemaining;
  }

  public ConnectedUser ifName(String ifName) {
    this.ifName = ifName;
    return this;
  }

  /**
   * Get ifName
   * @return ifName
  */
  @ApiModelProperty(value = "")


  public String getIfName() {
    return ifName;
  }

  public void setIfName(String ifName) {
    this.ifName = ifName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectedUser connectedUser = (ConnectedUser) o;
    return Objects.equals(this.macAddress, connectedUser.macAddress) &&
        Objects.equals(this.isActive, connectedUser.isActive) &&
        Objects.equals(this.addressSource, connectedUser.addressSource) &&
        Objects.equals(this.hostName, connectedUser.hostName) &&
        Objects.equals(this.ipAddress, connectedUser.ipAddress) &&
        Objects.equals(this.interfaceType, connectedUser.interfaceType) &&
        Objects.equals(this.leaseTimeRemaining, connectedUser.leaseTimeRemaining) &&
        Objects.equals(this.ifName, connectedUser.ifName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(macAddress, isActive, addressSource, hostName, ipAddress, interfaceType, leaseTimeRemaining, ifName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnectedUser {\n");
    
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    addressSource: ").append(toIndentedString(addressSource)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    interfaceType: ").append(toIndentedString(interfaceType)).append("\n");
    sb.append("    leaseTimeRemaining: ").append(toIndentedString(leaseTimeRemaining)).append("\n");
    sb.append("    ifName: ").append(toIndentedString(ifName)).append("\n");
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

