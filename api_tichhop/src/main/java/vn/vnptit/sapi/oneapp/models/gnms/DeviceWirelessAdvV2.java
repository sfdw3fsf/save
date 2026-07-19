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
 * DeviceWirelessAdvV2
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DeviceWirelessAdvV2   {
  @JsonProperty("instance")
  private String instance;

  @JsonProperty("channelWidth")
  private String channelWidth;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("autoChannelTimer")
  private BigDecimal autoChannelTimer;

  @JsonProperty("transmitPower")
  private BigDecimal transmitPower;

  @JsonProperty("enable")
  private Boolean enable;

  public DeviceWirelessAdvV2 instance(String instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Get instance
   * @return instance
  */
  @ApiModelProperty(value = "")


  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  public DeviceWirelessAdvV2 channelWidth(String channelWidth) {
    this.channelWidth = channelWidth;
    return this;
  }

  /**
   * Get channelWidth
   * @return channelWidth
  */
  @ApiModelProperty(value = "")


  public String getChannelWidth() {
    return channelWidth;
  }

  public void setChannelWidth(String channelWidth) {
    this.channelWidth = channelWidth;
  }

  public DeviceWirelessAdvV2 channel(String channel) {
    this.channel = channel;
    return this;
  }

  /**
   * Get channel
   * @return channel
  */
  @ApiModelProperty(value = "")


  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public DeviceWirelessAdvV2 autoChannelTimer(BigDecimal autoChannelTimer) {
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

  public DeviceWirelessAdvV2 transmitPower(BigDecimal transmitPower) {
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

  public DeviceWirelessAdvV2 enable(Boolean enable) {
    this.enable = enable;
    return this;
  }

  /**
   * Get enable
   * @return enable
  */
  @ApiModelProperty(value = "")


  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceWirelessAdvV2 deviceWirelessAdvV2 = (DeviceWirelessAdvV2) o;
    return Objects.equals(this.instance, deviceWirelessAdvV2.instance) &&
        Objects.equals(this.channelWidth, deviceWirelessAdvV2.channelWidth) &&
        Objects.equals(this.channel, deviceWirelessAdvV2.channel) &&
        Objects.equals(this.autoChannelTimer, deviceWirelessAdvV2.autoChannelTimer) &&
        Objects.equals(this.transmitPower, deviceWirelessAdvV2.transmitPower) &&
        Objects.equals(this.enable, deviceWirelessAdvV2.enable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(instance, channelWidth, channel, autoChannelTimer, transmitPower, enable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceWirelessAdvV2 {\n");
    
    sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
    sb.append("    channelWidth: ").append(toIndentedString(channelWidth)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    autoChannelTimer: ").append(toIndentedString(autoChannelTimer)).append("\n");
    sb.append("    transmitPower: ").append(toIndentedString(transmitPower)).append("\n");
    sb.append("    enable: ").append(toIndentedString(enable)).append("\n");
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

