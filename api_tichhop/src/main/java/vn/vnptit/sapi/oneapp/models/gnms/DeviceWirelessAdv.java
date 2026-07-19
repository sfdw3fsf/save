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
 * DeviceWirelessAdv
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DeviceWirelessAdv   {
  @JsonProperty("channel")
  private BigDecimal channel;

  @JsonProperty("bandwidth")
  private BigDecimal bandwidth;

  @JsonProperty("country")
  private String country;

  @JsonProperty("autoChannelTimer")
  private BigDecimal autoChannelTimer;

  @JsonProperty("transmitPower")
  private BigDecimal transmitPower;

  public DeviceWirelessAdv channel(BigDecimal channel) {
    this.channel = channel;
    return this;
  }

  /**
   * Get channel
   * @return channel
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getChannel() {
    return channel;
  }

  public void setChannel(BigDecimal channel) {
    this.channel = channel;
  }

  public DeviceWirelessAdv bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  /**
   * Get bandwidth
   * @return bandwidth
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  public DeviceWirelessAdv country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
  */
  @ApiModelProperty(value = "")


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public DeviceWirelessAdv autoChannelTimer(BigDecimal autoChannelTimer) {
    this.autoChannelTimer = autoChannelTimer;
    return this;
  }

  /**
   * Get autoChannelTimer
   * @return autoChannelTimer
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAutoChannelTimer() {
    return autoChannelTimer;
  }

  public void setAutoChannelTimer(BigDecimal autoChannelTimer) {
    this.autoChannelTimer = autoChannelTimer;
  }

  public DeviceWirelessAdv transmitPower(BigDecimal transmitPower) {
    this.transmitPower = transmitPower;
    return this;
  }

  /**
   * Get transmitPower
   * @return transmitPower
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTransmitPower() {
    return transmitPower;
  }

  public void setTransmitPower(BigDecimal transmitPower) {
    this.transmitPower = transmitPower;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceWirelessAdv deviceWirelessAdv = (DeviceWirelessAdv) o;
    return Objects.equals(this.channel, deviceWirelessAdv.channel) &&
        Objects.equals(this.bandwidth, deviceWirelessAdv.bandwidth) &&
        Objects.equals(this.country, deviceWirelessAdv.country) &&
        Objects.equals(this.autoChannelTimer, deviceWirelessAdv.autoChannelTimer) &&
        Objects.equals(this.transmitPower, deviceWirelessAdv.transmitPower);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channel, bandwidth, country, autoChannelTimer, transmitPower);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceWirelessAdv {\n");
    
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    autoChannelTimer: ").append(toIndentedString(autoChannelTimer)).append("\n");
    sb.append("    transmitPower: ").append(toIndentedString(transmitPower)).append("\n");
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

