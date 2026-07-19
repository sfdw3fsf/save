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
 * ReportWifi
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ReportWifi   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("opticalSerial")
  private String opticalSerial;

  @JsonProperty("softwareVersion")
  private String softwareVersion;

  @JsonProperty("modelName")
  private String modelName;

  @JsonProperty("startTime")
  private BigDecimal startTime;

  @JsonProperty("endTime")
  private BigDecimal endTime;

  @JsonProperty("areaId")
  private Integer areaId;

  @JsonProperty("areaName")
  private String areaName;

  @JsonProperty("description")
  private String description;

  @JsonProperty("productID")
  private Integer productID;

  @JsonProperty("bandwidth")
  private String bandwidth;

  @JsonProperty("encryption")
  private String encryption;

  @JsonProperty("networkAuth")
  private String networkAuth;

  @JsonProperty("countryCode")
  private String countryCode;

  @JsonProperty("disableWmm")
  private String disableWmm;

  @JsonProperty("enableMulticast")
  private String enableMulticast;

  @JsonProperty("ssid")
  private String ssid;

  @JsonProperty("txPower")
  private String txPower;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("wifiMode")
  private String wifiMode;

  @JsonProperty("autoChannelTimer")
  private String autoChannelTimer;

  @JsonProperty("enableTraficSchedule")
  private String enableTraficSchedule;

  @JsonProperty("airtimeFairness")
  private String airtimeFairness;

  @JsonProperty("internetTime")
  private String internetTime;

  @JsonProperty("staticDNS")
  private String staticDNS;

  @JsonProperty("dnsIfName")
  private String dnsIfName;

  @JsonProperty("wifiInstance")
  private Integer wifiInstance;

  @JsonProperty("flagInValid")
  private Boolean flagInValid;

  public ReportWifi id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ReportWifi opticalSerial(String opticalSerial) {
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

  public ReportWifi softwareVersion(String softwareVersion) {
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

  public ReportWifi modelName(String modelName) {
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

  public ReportWifi startTime(BigDecimal startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Get startTime
   * @return startTime
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getStartTime() {
    return startTime;
  }

  public void setStartTime(BigDecimal startTime) {
    this.startTime = startTime;
  }

  public ReportWifi endTime(BigDecimal endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * Get endTime
   * @return endTime
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEndTime() {
    return endTime;
  }

  public void setEndTime(BigDecimal endTime) {
    this.endTime = endTime;
  }

  public ReportWifi areaId(Integer areaId) {
    this.areaId = areaId;
    return this;
  }

  /**
   * Get areaId
   * @return areaId
  */
  @ApiModelProperty(value = "")


  public Integer getAreaId() {
    return areaId;
  }

  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }

  public ReportWifi areaName(String areaName) {
    this.areaName = areaName;
    return this;
  }

  /**
   * Get areaName
   * @return areaName
  */
  @ApiModelProperty(value = "")


  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public ReportWifi description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ReportWifi productID(Integer productID) {
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

  public ReportWifi bandwidth(String bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  /**
   * Get bandwidth
   * @return bandwidth
  */
  @ApiModelProperty(value = "")


  public String getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(String bandwidth) {
    this.bandwidth = bandwidth;
  }

  public ReportWifi encryption(String encryption) {
    this.encryption = encryption;
    return this;
  }

  /**
   * Get encryption
   * @return encryption
  */
  @ApiModelProperty(value = "")


  public String getEncryption() {
    return encryption;
  }

  public void setEncryption(String encryption) {
    this.encryption = encryption;
  }

  public ReportWifi networkAuth(String networkAuth) {
    this.networkAuth = networkAuth;
    return this;
  }

  /**
   * Get networkAuth
   * @return networkAuth
  */
  @ApiModelProperty(value = "")


  public String getNetworkAuth() {
    return networkAuth;
  }

  public void setNetworkAuth(String networkAuth) {
    this.networkAuth = networkAuth;
  }

  public ReportWifi countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * Get countryCode
   * @return countryCode
  */
  @ApiModelProperty(value = "")


  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public ReportWifi disableWmm(String disableWmm) {
    this.disableWmm = disableWmm;
    return this;
  }

  /**
   * Get disableWmm
   * @return disableWmm
  */
  @ApiModelProperty(value = "")


  public String getDisableWmm() {
    return disableWmm;
  }

  public void setDisableWmm(String disableWmm) {
    this.disableWmm = disableWmm;
  }

  public ReportWifi enableMulticast(String enableMulticast) {
    this.enableMulticast = enableMulticast;
    return this;
  }

  /**
   * Get enableMulticast
   * @return enableMulticast
  */
  @ApiModelProperty(value = "")


  public String getEnableMulticast() {
    return enableMulticast;
  }

  public void setEnableMulticast(String enableMulticast) {
    this.enableMulticast = enableMulticast;
  }

  public ReportWifi ssid(String ssid) {
    this.ssid = ssid;
    return this;
  }

  /**
   * Get ssid
   * @return ssid
  */
  @ApiModelProperty(value = "")


  public String getSsid() {
    return ssid;
  }

  public void setSsid(String ssid) {
    this.ssid = ssid;
  }

  public ReportWifi txPower(String txPower) {
    this.txPower = txPower;
    return this;
  }

  /**
   * Get txPower
   * @return txPower
  */
  @ApiModelProperty(value = "")


  public String getTxPower() {
    return txPower;
  }

  public void setTxPower(String txPower) {
    this.txPower = txPower;
  }

  public ReportWifi channel(String channel) {
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

  public ReportWifi wifiMode(String wifiMode) {
    this.wifiMode = wifiMode;
    return this;
  }

  /**
   * Get wifiMode
   * @return wifiMode
  */
  @ApiModelProperty(value = "")


  public String getWifiMode() {
    return wifiMode;
  }

  public void setWifiMode(String wifiMode) {
    this.wifiMode = wifiMode;
  }

  public ReportWifi autoChannelTimer(String autoChannelTimer) {
    this.autoChannelTimer = autoChannelTimer;
    return this;
  }

  /**
   * Get autoChannelTimer
   * @return autoChannelTimer
  */
  @ApiModelProperty(value = "")


  public String getAutoChannelTimer() {
    return autoChannelTimer;
  }

  public void setAutoChannelTimer(String autoChannelTimer) {
    this.autoChannelTimer = autoChannelTimer;
  }

  public ReportWifi enableTraficSchedule(String enableTraficSchedule) {
    this.enableTraficSchedule = enableTraficSchedule;
    return this;
  }

  /**
   * Get enableTraficSchedule
   * @return enableTraficSchedule
  */
  @ApiModelProperty(value = "")


  public String getEnableTraficSchedule() {
    return enableTraficSchedule;
  }

  public void setEnableTraficSchedule(String enableTraficSchedule) {
    this.enableTraficSchedule = enableTraficSchedule;
  }

  public ReportWifi airtimeFairness(String airtimeFairness) {
    this.airtimeFairness = airtimeFairness;
    return this;
  }

  /**
   * Get airtimeFairness
   * @return airtimeFairness
  */
  @ApiModelProperty(value = "")


  public String getAirtimeFairness() {
    return airtimeFairness;
  }

  public void setAirtimeFairness(String airtimeFairness) {
    this.airtimeFairness = airtimeFairness;
  }

  public ReportWifi internetTime(String internetTime) {
    this.internetTime = internetTime;
    return this;
  }

  /**
   * Get internetTime
   * @return internetTime
  */
  @ApiModelProperty(value = "")


  public String getInternetTime() {
    return internetTime;
  }

  public void setInternetTime(String internetTime) {
    this.internetTime = internetTime;
  }

  public ReportWifi staticDNS(String staticDNS) {
    this.staticDNS = staticDNS;
    return this;
  }

  /**
   * Get staticDNS
   * @return staticDNS
  */
  @ApiModelProperty(value = "")


  public String getStaticDNS() {
    return staticDNS;
  }

  public void setStaticDNS(String staticDNS) {
    this.staticDNS = staticDNS;
  }

  public ReportWifi dnsIfName(String dnsIfName) {
    this.dnsIfName = dnsIfName;
    return this;
  }

  /**
   * Get dnsIfName
   * @return dnsIfName
  */
  @ApiModelProperty(value = "")


  public String getDnsIfName() {
    return dnsIfName;
  }

  public void setDnsIfName(String dnsIfName) {
    this.dnsIfName = dnsIfName;
  }

  public ReportWifi wifiInstance(Integer wifiInstance) {
    this.wifiInstance = wifiInstance;
    return this;
  }

  /**
   * Get wifiInstance
   * @return wifiInstance
  */
  @ApiModelProperty(value = "")


  public Integer getWifiInstance() {
    return wifiInstance;
  }

  public void setWifiInstance(Integer wifiInstance) {
    this.wifiInstance = wifiInstance;
  }

  public ReportWifi flagInValid(Boolean flagInValid) {
    this.flagInValid = flagInValid;
    return this;
  }

  /**
   * Get flagInValid
   * @return flagInValid
  */
  @ApiModelProperty(value = "")


  public Boolean getFlagInValid() {
    return flagInValid;
  }

  public void setFlagInValid(Boolean flagInValid) {
    this.flagInValid = flagInValid;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportWifi reportWifi = (ReportWifi) o;
    return Objects.equals(this.id, reportWifi.id) &&
        Objects.equals(this.opticalSerial, reportWifi.opticalSerial) &&
        Objects.equals(this.softwareVersion, reportWifi.softwareVersion) &&
        Objects.equals(this.modelName, reportWifi.modelName) &&
        Objects.equals(this.startTime, reportWifi.startTime) &&
        Objects.equals(this.endTime, reportWifi.endTime) &&
        Objects.equals(this.areaId, reportWifi.areaId) &&
        Objects.equals(this.areaName, reportWifi.areaName) &&
        Objects.equals(this.description, reportWifi.description) &&
        Objects.equals(this.productID, reportWifi.productID) &&
        Objects.equals(this.bandwidth, reportWifi.bandwidth) &&
        Objects.equals(this.encryption, reportWifi.encryption) &&
        Objects.equals(this.networkAuth, reportWifi.networkAuth) &&
        Objects.equals(this.countryCode, reportWifi.countryCode) &&
        Objects.equals(this.disableWmm, reportWifi.disableWmm) &&
        Objects.equals(this.enableMulticast, reportWifi.enableMulticast) &&
        Objects.equals(this.ssid, reportWifi.ssid) &&
        Objects.equals(this.txPower, reportWifi.txPower) &&
        Objects.equals(this.channel, reportWifi.channel) &&
        Objects.equals(this.wifiMode, reportWifi.wifiMode) &&
        Objects.equals(this.autoChannelTimer, reportWifi.autoChannelTimer) &&
        Objects.equals(this.enableTraficSchedule, reportWifi.enableTraficSchedule) &&
        Objects.equals(this.airtimeFairness, reportWifi.airtimeFairness) &&
        Objects.equals(this.internetTime, reportWifi.internetTime) &&
        Objects.equals(this.staticDNS, reportWifi.staticDNS) &&
        Objects.equals(this.dnsIfName, reportWifi.dnsIfName) &&
        Objects.equals(this.wifiInstance, reportWifi.wifiInstance) &&
        Objects.equals(this.flagInValid, reportWifi.flagInValid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, opticalSerial, softwareVersion, modelName, startTime, endTime, areaId, areaName, description, productID, bandwidth, encryption, networkAuth, countryCode, disableWmm, enableMulticast, ssid, txPower, channel, wifiMode, autoChannelTimer, enableTraficSchedule, airtimeFairness, internetTime, staticDNS, dnsIfName, wifiInstance, flagInValid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportWifi {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    opticalSerial: ").append(toIndentedString(opticalSerial)).append("\n");
    sb.append("    softwareVersion: ").append(toIndentedString(softwareVersion)).append("\n");
    sb.append("    modelName: ").append(toIndentedString(modelName)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    areaId: ").append(toIndentedString(areaId)).append("\n");
    sb.append("    areaName: ").append(toIndentedString(areaName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    productID: ").append(toIndentedString(productID)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    encryption: ").append(toIndentedString(encryption)).append("\n");
    sb.append("    networkAuth: ").append(toIndentedString(networkAuth)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    disableWmm: ").append(toIndentedString(disableWmm)).append("\n");
    sb.append("    enableMulticast: ").append(toIndentedString(enableMulticast)).append("\n");
    sb.append("    ssid: ").append(toIndentedString(ssid)).append("\n");
    sb.append("    txPower: ").append(toIndentedString(txPower)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    wifiMode: ").append(toIndentedString(wifiMode)).append("\n");
    sb.append("    autoChannelTimer: ").append(toIndentedString(autoChannelTimer)).append("\n");
    sb.append("    enableTraficSchedule: ").append(toIndentedString(enableTraficSchedule)).append("\n");
    sb.append("    airtimeFairness: ").append(toIndentedString(airtimeFairness)).append("\n");
    sb.append("    internetTime: ").append(toIndentedString(internetTime)).append("\n");
    sb.append("    staticDNS: ").append(toIndentedString(staticDNS)).append("\n");
    sb.append("    dnsIfName: ").append(toIndentedString(dnsIfName)).append("\n");
    sb.append("    wifiInstance: ").append(toIndentedString(wifiInstance)).append("\n");
    sb.append("    flagInValid: ").append(toIndentedString(flagInValid)).append("\n");
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

