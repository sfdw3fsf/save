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
 * DeviceWan
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DeviceWan   {
  @JsonProperty("wanIfname")
  private String wanIfname;

  @JsonProperty("wanName")
  private String wanName;

  @JsonProperty("wanInstancePath")
  private String wanInstancePath;

  @JsonProperty("wanType")
  private String wanType;

  @JsonProperty("connectionType")
  private String connectionType;

  @JsonProperty("connectionStatus")
  private String connectionStatus;

  @JsonProperty("vlanPriority")
  private Integer vlanPriority;

  @JsonProperty("vlanMuxID")
  private Integer vlanMuxID;

  @JsonProperty("vlanTPID")
  private Integer vlanTPID;

  @JsonProperty("ipv4Enable")
  private Boolean ipv4Enable;

  @JsonProperty("ipv6Enable")
  private Boolean ipv6Enable;

  @JsonProperty("natEnable")
  private Boolean natEnable;

  @JsonProperty("firewallEnable")
  private Boolean firewallEnable;

  @JsonProperty("igmpSourceEnable")
  private Boolean igmpSourceEnable;

  @JsonProperty("mldSourceEnable")
  private Boolean mldSourceEnable;

  @JsonProperty("igmpEnabled")
  private Boolean igmpEnabled;

  @JsonProperty("mldEnabled")
  private Boolean mldEnabled;

  @JsonProperty("fullConeNatEnable")
  private Boolean fullConeNatEnable;

  @JsonProperty("enable")
  private Boolean enable;

  @JsonProperty("connectionID")
  private Integer connectionID;

  @JsonProperty("wanIndex")
  private Integer wanIndex;

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("password")
  private String password;

  @JsonProperty("vpiValue")
  private Integer vpiValue;

  @JsonProperty("vciValue")
  private Integer vciValue;

  @JsonProperty("addressingType")
  private String addressingType;

  @JsonProperty("externalIPAdress")
  private String externalIPAdress;

  @JsonProperty("subnetMask")
  private String subnetMask;

  @JsonProperty("defaultGateway")
  private String defaultGateway;

  @JsonProperty("dnsServers")
  private String dnsServers;

  @JsonProperty("dnsIfName")
  private String dnsIfName;

  @JsonProperty("ipv6AddressingType")
  private String ipv6AddressingType;

  @JsonProperty("ipv6Status")
  private String ipv6Status;

  @JsonProperty("unnumberedModel")
  private Boolean unnumberedModel;

  @JsonProperty("dhcp6cForAddress")
  private String dhcp6cForAddress;

  @JsonProperty("dhcp6cForPrefixDelegation")
  private String dhcp6cForPrefixDelegation;

  @JsonProperty("dhcp6cPrefixDelegationEnable")
  private String dhcp6cPrefixDelegationEnable;

  @JsonProperty("externalIPv6Address")
  private String externalIPv6Address;

  @JsonProperty("defaultIPv6Gateway")
  private String defaultIPv6Gateway;

  @JsonProperty("ipv6DnsServer")
  private String ipv6DnsServer;

  public DeviceWan wanIfname(String wanIfname) {
    this.wanIfname = wanIfname;
    return this;
  }

  /**
   * wanIfname
   * @return wanIfname
  */
  @ApiModelProperty(value = "wanIfname")


  public String getWanIfname() {
    return wanIfname;
  }

  public void setWanIfname(String wanIfname) {
    this.wanIfname = wanIfname;
  }

  public DeviceWan wanName(String wanName) {
    this.wanName = wanName;
    return this;
  }

  /**
   * wanName
   * @return wanName
  */
  @ApiModelProperty(value = "wanName")


  public String getWanName() {
    return wanName;
  }

  public void setWanName(String wanName) {
    this.wanName = wanName;
  }

  public DeviceWan wanInstancePath(String wanInstancePath) {
    this.wanInstancePath = wanInstancePath;
    return this;
  }

  /**
   * wanInstancePath
   * @return wanInstancePath
  */
  @ApiModelProperty(value = "wanInstancePath")


  public String getWanInstancePath() {
    return wanInstancePath;
  }

  public void setWanInstancePath(String wanInstancePath) {
    this.wanInstancePath = wanInstancePath;
  }

  public DeviceWan wanType(String wanType) {
    this.wanType = wanType;
    return this;
  }

  /**
   * wanType
   * @return wanType
  */
  @ApiModelProperty(value = "wanType")


  public String getWanType() {
    return wanType;
  }

  public void setWanType(String wanType) {
    this.wanType = wanType;
  }

  public DeviceWan connectionType(String connectionType) {
    this.connectionType = connectionType;
    return this;
  }

  /**
   * connectionType
   * @return connectionType
  */
  @ApiModelProperty(value = "connectionType")


  public String getConnectionType() {
    return connectionType;
  }

  public void setConnectionType(String connectionType) {
    this.connectionType = connectionType;
  }

  public DeviceWan connectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
    return this;
  }

  /**
   * connectionStatus
   * @return connectionStatus
  */
  @ApiModelProperty(value = "connectionStatus")


  public String getConnectionStatus() {
    return connectionStatus;
  }

  public void setConnectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
  }

  public DeviceWan vlanPriority(Integer vlanPriority) {
    this.vlanPriority = vlanPriority;
    return this;
  }

  /**
   * vlanPriority
   * @return vlanPriority
  */
  @ApiModelProperty(value = "vlanPriority")


  public Integer getVlanPriority() {
    return vlanPriority;
  }

  public void setVlanPriority(Integer vlanPriority) {
    this.vlanPriority = vlanPriority;
  }

  public DeviceWan vlanMuxID(Integer vlanMuxID) {
    this.vlanMuxID = vlanMuxID;
    return this;
  }

  /**
   * vlanMuxID
   * @return vlanMuxID
  */
  @ApiModelProperty(value = "vlanMuxID")


  public Integer getVlanMuxID() {
    return vlanMuxID;
  }

  public void setVlanMuxID(Integer vlanMuxID) {
    this.vlanMuxID = vlanMuxID;
  }

  public DeviceWan vlanTPID(Integer vlanTPID) {
    this.vlanTPID = vlanTPID;
    return this;
  }

  /**
   * vlanTPID
   * @return vlanTPID
  */
  @ApiModelProperty(value = "vlanTPID")


  public Integer getVlanTPID() {
    return vlanTPID;
  }

  public void setVlanTPID(Integer vlanTPID) {
    this.vlanTPID = vlanTPID;
  }

  public DeviceWan ipv4Enable(Boolean ipv4Enable) {
    this.ipv4Enable = ipv4Enable;
    return this;
  }

  /**
   * ipv4Enable
   * @return ipv4Enable
  */
  @ApiModelProperty(value = "ipv4Enable")


  public Boolean getIpv4Enable() {
    return ipv4Enable;
  }

  public void setIpv4Enable(Boolean ipv4Enable) {
    this.ipv4Enable = ipv4Enable;
  }

  public DeviceWan ipv6Enable(Boolean ipv6Enable) {
    this.ipv6Enable = ipv6Enable;
    return this;
  }

  /**
   * ipv6Enable
   * @return ipv6Enable
  */
  @ApiModelProperty(value = "ipv6Enable")


  public Boolean getIpv6Enable() {
    return ipv6Enable;
  }

  public void setIpv6Enable(Boolean ipv6Enable) {
    this.ipv6Enable = ipv6Enable;
  }

  public DeviceWan natEnable(Boolean natEnable) {
    this.natEnable = natEnable;
    return this;
  }

  /**
   * natEnable
   * @return natEnable
  */
  @ApiModelProperty(value = "natEnable")


  public Boolean getNatEnable() {
    return natEnable;
  }

  public void setNatEnable(Boolean natEnable) {
    this.natEnable = natEnable;
  }

  public DeviceWan firewallEnable(Boolean firewallEnable) {
    this.firewallEnable = firewallEnable;
    return this;
  }

  /**
   * firewallEnable
   * @return firewallEnable
  */
  @ApiModelProperty(value = "firewallEnable")


  public Boolean getFirewallEnable() {
    return firewallEnable;
  }

  public void setFirewallEnable(Boolean firewallEnable) {
    this.firewallEnable = firewallEnable;
  }

  public DeviceWan igmpSourceEnable(Boolean igmpSourceEnable) {
    this.igmpSourceEnable = igmpSourceEnable;
    return this;
  }

  /**
   * igmpSourceEnable
   * @return igmpSourceEnable
  */
  @ApiModelProperty(value = "igmpSourceEnable")


  public Boolean getIgmpSourceEnable() {
    return igmpSourceEnable;
  }

  public void setIgmpSourceEnable(Boolean igmpSourceEnable) {
    this.igmpSourceEnable = igmpSourceEnable;
  }

  public DeviceWan mldSourceEnable(Boolean mldSourceEnable) {
    this.mldSourceEnable = mldSourceEnable;
    return this;
  }

  /**
   * mldSourceEnable
   * @return mldSourceEnable
  */
  @ApiModelProperty(value = "mldSourceEnable")


  public Boolean getMldSourceEnable() {
    return mldSourceEnable;
  }

  public void setMldSourceEnable(Boolean mldSourceEnable) {
    this.mldSourceEnable = mldSourceEnable;
  }

  public DeviceWan igmpEnabled(Boolean igmpEnabled) {
    this.igmpEnabled = igmpEnabled;
    return this;
  }

  /**
   * igmpEnabled
   * @return igmpEnabled
  */
  @ApiModelProperty(value = "igmpEnabled")


  public Boolean getIgmpEnabled() {
    return igmpEnabled;
  }

  public void setIgmpEnabled(Boolean igmpEnabled) {
    this.igmpEnabled = igmpEnabled;
  }

  public DeviceWan mldEnabled(Boolean mldEnabled) {
    this.mldEnabled = mldEnabled;
    return this;
  }

  /**
   * mldEnabled
   * @return mldEnabled
  */
  @ApiModelProperty(value = "mldEnabled")


  public Boolean getMldEnabled() {
    return mldEnabled;
  }

  public void setMldEnabled(Boolean mldEnabled) {
    this.mldEnabled = mldEnabled;
  }

  public DeviceWan fullConeNatEnable(Boolean fullConeNatEnable) {
    this.fullConeNatEnable = fullConeNatEnable;
    return this;
  }

  /**
   * fullConeNatEnable
   * @return fullConeNatEnable
  */
  @ApiModelProperty(value = "fullConeNatEnable")


  public Boolean getFullConeNatEnable() {
    return fullConeNatEnable;
  }

  public void setFullConeNatEnable(Boolean fullConeNatEnable) {
    this.fullConeNatEnable = fullConeNatEnable;
  }

  public DeviceWan enable(Boolean enable) {
    this.enable = enable;
    return this;
  }

  /**
   * enable
   * @return enable
  */
  @ApiModelProperty(value = "enable")


  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public DeviceWan connectionID(Integer connectionID) {
    this.connectionID = connectionID;
    return this;
  }

  /**
   * connectionID
   * @return connectionID
  */
  @ApiModelProperty(value = "connectionID")


  public Integer getConnectionID() {
    return connectionID;
  }

  public void setConnectionID(Integer connectionID) {
    this.connectionID = connectionID;
  }

  public DeviceWan wanIndex(Integer wanIndex) {
    this.wanIndex = wanIndex;
    return this;
  }

  /**
   * wanIndex
   * @return wanIndex
  */
  @ApiModelProperty(value = "wanIndex")


  public Integer getWanIndex() {
    return wanIndex;
  }

  public void setWanIndex(Integer wanIndex) {
    this.wanIndex = wanIndex;
  }

  public DeviceWan userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * userName
   * @return userName
  */
  @ApiModelProperty(value = "userName")


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public DeviceWan password(String password) {
    this.password = password;
    return this;
  }

  /**
   * password
   * @return password
  */
  @ApiModelProperty(value = "password")


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public DeviceWan vpiValue(Integer vpiValue) {
    this.vpiValue = vpiValue;
    return this;
  }

  /**
   * vpiValue
   * @return vpiValue
  */
  @ApiModelProperty(value = "vpiValue")


  public Integer getVpiValue() {
    return vpiValue;
  }

  public void setVpiValue(Integer vpiValue) {
    this.vpiValue = vpiValue;
  }

  public DeviceWan vciValue(Integer vciValue) {
    this.vciValue = vciValue;
    return this;
  }

  /**
   * vciValue
   * @return vciValue
  */
  @ApiModelProperty(value = "vciValue")


  public Integer getVciValue() {
    return vciValue;
  }

  public void setVciValue(Integer vciValue) {
    this.vciValue = vciValue;
  }

  public DeviceWan addressingType(String addressingType) {
    this.addressingType = addressingType;
    return this;
  }

  /**
   * addressingType
   * @return addressingType
  */
  @ApiModelProperty(value = "addressingType")


  public String getAddressingType() {
    return addressingType;
  }

  public void setAddressingType(String addressingType) {
    this.addressingType = addressingType;
  }

  public DeviceWan externalIPAdress(String externalIPAdress) {
    this.externalIPAdress = externalIPAdress;
    return this;
  }

  /**
   * externalIPAdress
   * @return externalIPAdress
  */
  @ApiModelProperty(value = "externalIPAdress")


  public String getExternalIPAdress() {
    return externalIPAdress;
  }

  public void setExternalIPAdress(String externalIPAdress) {
    this.externalIPAdress = externalIPAdress;
  }

  public DeviceWan subnetMask(String subnetMask) {
    this.subnetMask = subnetMask;
    return this;
  }

  /**
   * subnetMask
   * @return subnetMask
  */
  @ApiModelProperty(value = "subnetMask")


  public String getSubnetMask() {
    return subnetMask;
  }

  public void setSubnetMask(String subnetMask) {
    this.subnetMask = subnetMask;
  }

  public DeviceWan defaultGateway(String defaultGateway) {
    this.defaultGateway = defaultGateway;
    return this;
  }

  /**
   * defaultGateway
   * @return defaultGateway
  */
  @ApiModelProperty(value = "defaultGateway")


  public String getDefaultGateway() {
    return defaultGateway;
  }

  public void setDefaultGateway(String defaultGateway) {
    this.defaultGateway = defaultGateway;
  }

  public DeviceWan dnsServers(String dnsServers) {
    this.dnsServers = dnsServers;
    return this;
  }

  /**
   * dnsServers
   * @return dnsServers
  */
  @ApiModelProperty(value = "dnsServers")


  public String getDnsServers() {
    return dnsServers;
  }

  public void setDnsServers(String dnsServers) {
    this.dnsServers = dnsServers;
  }

  public DeviceWan dnsIfName(String dnsIfName) {
    this.dnsIfName = dnsIfName;
    return this;
  }

  /**
   * dnsIfName
   * @return dnsIfName
  */
  @ApiModelProperty(value = "dnsIfName")


  public String getDnsIfName() {
    return dnsIfName;
  }

  public void setDnsIfName(String dnsIfName) {
    this.dnsIfName = dnsIfName;
  }

  public DeviceWan ipv6AddressingType(String ipv6AddressingType) {
    this.ipv6AddressingType = ipv6AddressingType;
    return this;
  }

  /**
   * ipv6AddressingType
   * @return ipv6AddressingType
  */
  @ApiModelProperty(value = "ipv6AddressingType")


  public String getIpv6AddressingType() {
    return ipv6AddressingType;
  }

  public void setIpv6AddressingType(String ipv6AddressingType) {
    this.ipv6AddressingType = ipv6AddressingType;
  }

  public DeviceWan ipv6Status(String ipv6Status) {
    this.ipv6Status = ipv6Status;
    return this;
  }

  /**
   * ipv6Status
   * @return ipv6Status
  */
  @ApiModelProperty(value = "ipv6Status")


  public String getIpv6Status() {
    return ipv6Status;
  }

  public void setIpv6Status(String ipv6Status) {
    this.ipv6Status = ipv6Status;
  }

  public DeviceWan unnumberedModel(Boolean unnumberedModel) {
    this.unnumberedModel = unnumberedModel;
    return this;
  }

  /**
   * unnumberedModel
   * @return unnumberedModel
  */
  @ApiModelProperty(value = "unnumberedModel")


  public Boolean getUnnumberedModel() {
    return unnumberedModel;
  }

  public void setUnnumberedModel(Boolean unnumberedModel) {
    this.unnumberedModel = unnumberedModel;
  }

  public DeviceWan dhcp6cForAddress(String dhcp6cForAddress) {
    this.dhcp6cForAddress = dhcp6cForAddress;
    return this;
  }

  /**
   * dhcp6cForAddress
   * @return dhcp6cForAddress
  */
  @ApiModelProperty(value = "dhcp6cForAddress")


  public String getDhcp6cForAddress() {
    return dhcp6cForAddress;
  }

  public void setDhcp6cForAddress(String dhcp6cForAddress) {
    this.dhcp6cForAddress = dhcp6cForAddress;
  }

  public DeviceWan dhcp6cForPrefixDelegation(String dhcp6cForPrefixDelegation) {
    this.dhcp6cForPrefixDelegation = dhcp6cForPrefixDelegation;
    return this;
  }

  /**
   * dhcp6cForPrefixDelegation
   * @return dhcp6cForPrefixDelegation
  */
  @ApiModelProperty(value = "dhcp6cForPrefixDelegation")


  public String getDhcp6cForPrefixDelegation() {
    return dhcp6cForPrefixDelegation;
  }

  public void setDhcp6cForPrefixDelegation(String dhcp6cForPrefixDelegation) {
    this.dhcp6cForPrefixDelegation = dhcp6cForPrefixDelegation;
  }

  public DeviceWan dhcp6cPrefixDelegationEnable(String dhcp6cPrefixDelegationEnable) {
    this.dhcp6cPrefixDelegationEnable = dhcp6cPrefixDelegationEnable;
    return this;
  }

  /**
   * dhcp6cPrefixDelegationEnable
   * @return dhcp6cPrefixDelegationEnable
  */
  @ApiModelProperty(value = "dhcp6cPrefixDelegationEnable")


  public String getDhcp6cPrefixDelegationEnable() {
    return dhcp6cPrefixDelegationEnable;
  }

  public void setDhcp6cPrefixDelegationEnable(String dhcp6cPrefixDelegationEnable) {
    this.dhcp6cPrefixDelegationEnable = dhcp6cPrefixDelegationEnable;
  }

  public DeviceWan externalIPv6Address(String externalIPv6Address) {
    this.externalIPv6Address = externalIPv6Address;
    return this;
  }

  /**
   * externalIPv6Address
   * @return externalIPv6Address
  */
  @ApiModelProperty(value = "externalIPv6Address")


  public String getExternalIPv6Address() {
    return externalIPv6Address;
  }

  public void setExternalIPv6Address(String externalIPv6Address) {
    this.externalIPv6Address = externalIPv6Address;
  }

  public DeviceWan defaultIPv6Gateway(String defaultIPv6Gateway) {
    this.defaultIPv6Gateway = defaultIPv6Gateway;
    return this;
  }

  /**
   * defaultIPv6Gateway
   * @return defaultIPv6Gateway
  */
  @ApiModelProperty(value = "defaultIPv6Gateway")


  public String getDefaultIPv6Gateway() {
    return defaultIPv6Gateway;
  }

  public void setDefaultIPv6Gateway(String defaultIPv6Gateway) {
    this.defaultIPv6Gateway = defaultIPv6Gateway;
  }

  public DeviceWan ipv6DnsServer(String ipv6DnsServer) {
    this.ipv6DnsServer = ipv6DnsServer;
    return this;
  }

  /**
   * ipv6DnsServer
   * @return ipv6DnsServer
  */
  @ApiModelProperty(value = "ipv6DnsServer")


  public String getIpv6DnsServer() {
    return ipv6DnsServer;
  }

  public void setIpv6DnsServer(String ipv6DnsServer) {
    this.ipv6DnsServer = ipv6DnsServer;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceWan deviceWan = (DeviceWan) o;
    return Objects.equals(this.wanIfname, deviceWan.wanIfname) &&
        Objects.equals(this.wanName, deviceWan.wanName) &&
        Objects.equals(this.wanInstancePath, deviceWan.wanInstancePath) &&
        Objects.equals(this.wanType, deviceWan.wanType) &&
        Objects.equals(this.connectionType, deviceWan.connectionType) &&
        Objects.equals(this.connectionStatus, deviceWan.connectionStatus) &&
        Objects.equals(this.vlanPriority, deviceWan.vlanPriority) &&
        Objects.equals(this.vlanMuxID, deviceWan.vlanMuxID) &&
        Objects.equals(this.vlanTPID, deviceWan.vlanTPID) &&
        Objects.equals(this.ipv4Enable, deviceWan.ipv4Enable) &&
        Objects.equals(this.ipv6Enable, deviceWan.ipv6Enable) &&
        Objects.equals(this.natEnable, deviceWan.natEnable) &&
        Objects.equals(this.firewallEnable, deviceWan.firewallEnable) &&
        Objects.equals(this.igmpSourceEnable, deviceWan.igmpSourceEnable) &&
        Objects.equals(this.mldSourceEnable, deviceWan.mldSourceEnable) &&
        Objects.equals(this.igmpEnabled, deviceWan.igmpEnabled) &&
        Objects.equals(this.mldEnabled, deviceWan.mldEnabled) &&
        Objects.equals(this.fullConeNatEnable, deviceWan.fullConeNatEnable) &&
        Objects.equals(this.enable, deviceWan.enable) &&
        Objects.equals(this.connectionID, deviceWan.connectionID) &&
        Objects.equals(this.wanIndex, deviceWan.wanIndex) &&
        Objects.equals(this.userName, deviceWan.userName) &&
        Objects.equals(this.password, deviceWan.password) &&
        Objects.equals(this.vpiValue, deviceWan.vpiValue) &&
        Objects.equals(this.vciValue, deviceWan.vciValue) &&
        Objects.equals(this.addressingType, deviceWan.addressingType) &&
        Objects.equals(this.externalIPAdress, deviceWan.externalIPAdress) &&
        Objects.equals(this.subnetMask, deviceWan.subnetMask) &&
        Objects.equals(this.defaultGateway, deviceWan.defaultGateway) &&
        Objects.equals(this.dnsServers, deviceWan.dnsServers) &&
        Objects.equals(this.dnsIfName, deviceWan.dnsIfName) &&
        Objects.equals(this.ipv6AddressingType, deviceWan.ipv6AddressingType) &&
        Objects.equals(this.ipv6Status, deviceWan.ipv6Status) &&
        Objects.equals(this.unnumberedModel, deviceWan.unnumberedModel) &&
        Objects.equals(this.dhcp6cForAddress, deviceWan.dhcp6cForAddress) &&
        Objects.equals(this.dhcp6cForPrefixDelegation, deviceWan.dhcp6cForPrefixDelegation) &&
        Objects.equals(this.dhcp6cPrefixDelegationEnable, deviceWan.dhcp6cPrefixDelegationEnable) &&
        Objects.equals(this.externalIPv6Address, deviceWan.externalIPv6Address) &&
        Objects.equals(this.defaultIPv6Gateway, deviceWan.defaultIPv6Gateway) &&
        Objects.equals(this.ipv6DnsServer, deviceWan.ipv6DnsServer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wanIfname, wanName, wanInstancePath, wanType, connectionType, connectionStatus, vlanPriority, vlanMuxID, vlanTPID, ipv4Enable, ipv6Enable, natEnable, firewallEnable, igmpSourceEnable, mldSourceEnable, igmpEnabled, mldEnabled, fullConeNatEnable, enable, connectionID, wanIndex, userName, password, vpiValue, vciValue, addressingType, externalIPAdress, subnetMask, defaultGateway, dnsServers, dnsIfName, ipv6AddressingType, ipv6Status, unnumberedModel, dhcp6cForAddress, dhcp6cForPrefixDelegation, dhcp6cPrefixDelegationEnable, externalIPv6Address, defaultIPv6Gateway, ipv6DnsServer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceWan {\n");
    
    sb.append("    wanIfname: ").append(toIndentedString(wanIfname)).append("\n");
    sb.append("    wanName: ").append(toIndentedString(wanName)).append("\n");
    sb.append("    wanInstancePath: ").append(toIndentedString(wanInstancePath)).append("\n");
    sb.append("    wanType: ").append(toIndentedString(wanType)).append("\n");
    sb.append("    connectionType: ").append(toIndentedString(connectionType)).append("\n");
    sb.append("    connectionStatus: ").append(toIndentedString(connectionStatus)).append("\n");
    sb.append("    vlanPriority: ").append(toIndentedString(vlanPriority)).append("\n");
    sb.append("    vlanMuxID: ").append(toIndentedString(vlanMuxID)).append("\n");
    sb.append("    vlanTPID: ").append(toIndentedString(vlanTPID)).append("\n");
    sb.append("    ipv4Enable: ").append(toIndentedString(ipv4Enable)).append("\n");
    sb.append("    ipv6Enable: ").append(toIndentedString(ipv6Enable)).append("\n");
    sb.append("    natEnable: ").append(toIndentedString(natEnable)).append("\n");
    sb.append("    firewallEnable: ").append(toIndentedString(firewallEnable)).append("\n");
    sb.append("    igmpSourceEnable: ").append(toIndentedString(igmpSourceEnable)).append("\n");
    sb.append("    mldSourceEnable: ").append(toIndentedString(mldSourceEnable)).append("\n");
    sb.append("    igmpEnabled: ").append(toIndentedString(igmpEnabled)).append("\n");
    sb.append("    mldEnabled: ").append(toIndentedString(mldEnabled)).append("\n");
    sb.append("    fullConeNatEnable: ").append(toIndentedString(fullConeNatEnable)).append("\n");
    sb.append("    enable: ").append(toIndentedString(enable)).append("\n");
    sb.append("    connectionID: ").append(toIndentedString(connectionID)).append("\n");
    sb.append("    wanIndex: ").append(toIndentedString(wanIndex)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    vpiValue: ").append(toIndentedString(vpiValue)).append("\n");
    sb.append("    vciValue: ").append(toIndentedString(vciValue)).append("\n");
    sb.append("    addressingType: ").append(toIndentedString(addressingType)).append("\n");
    sb.append("    externalIPAdress: ").append(toIndentedString(externalIPAdress)).append("\n");
    sb.append("    subnetMask: ").append(toIndentedString(subnetMask)).append("\n");
    sb.append("    defaultGateway: ").append(toIndentedString(defaultGateway)).append("\n");
    sb.append("    dnsServers: ").append(toIndentedString(dnsServers)).append("\n");
    sb.append("    dnsIfName: ").append(toIndentedString(dnsIfName)).append("\n");
    sb.append("    ipv6AddressingType: ").append(toIndentedString(ipv6AddressingType)).append("\n");
    sb.append("    ipv6Status: ").append(toIndentedString(ipv6Status)).append("\n");
    sb.append("    unnumberedModel: ").append(toIndentedString(unnumberedModel)).append("\n");
    sb.append("    dhcp6cForAddress: ").append(toIndentedString(dhcp6cForAddress)).append("\n");
    sb.append("    dhcp6cForPrefixDelegation: ").append(toIndentedString(dhcp6cForPrefixDelegation)).append("\n");
    sb.append("    dhcp6cPrefixDelegationEnable: ").append(toIndentedString(dhcp6cPrefixDelegationEnable)).append("\n");
    sb.append("    externalIPv6Address: ").append(toIndentedString(externalIPv6Address)).append("\n");
    sb.append("    defaultIPv6Gateway: ").append(toIndentedString(defaultIPv6Gateway)).append("\n");
    sb.append("    ipv6DnsServer: ").append(toIndentedString(ipv6DnsServer)).append("\n");
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

