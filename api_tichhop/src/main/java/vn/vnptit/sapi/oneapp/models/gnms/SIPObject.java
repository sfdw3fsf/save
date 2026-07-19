package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SIPObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SIPObject   {
  @JsonProperty("enableVoiceService")
  private String enableVoiceService;

  @JsonProperty("boundIfName")
  private String boundIfName;

  @JsonProperty("BoundIfName")
  @Valid
  private List<String> boundIfNameArr = null;

  @JsonProperty("regions")
  @Valid
  private List<String> regions = null;

  @JsonProperty("ipAddressFamily")
  private String ipAddressFamily;

  @JsonProperty("region")
  private String region;

  @JsonProperty("digitMap")
  private String digitMap;

  @JsonProperty("userAgentDomain")
  private String userAgentDomain;

  @JsonProperty("proxyServer")
  private String proxyServer;

  @JsonProperty("proxyServerPort")
  private String proxyServerPort;

  @JsonProperty("outBoundProxy")
  private String outBoundProxy;

  @JsonProperty("outBoundProxyPort")
  private String outBoundProxyPort;

  @JsonProperty("registrarServer")
  private String registrarServer;

  @JsonProperty("registrarServerPort")
  private String registrarServerPort;

  @JsonProperty("dtmfMethod")
  private String dtmfMethod;

  public SIPObject enableVoiceService(String enableVoiceService) {
    this.enableVoiceService = enableVoiceService;
    return this;
  }

  /**
   * Get enableVoiceService
   * @return enableVoiceService
  */
  @ApiModelProperty(value = "")


  public String getEnableVoiceService() {
    return enableVoiceService;
  }

  public void setEnableVoiceService(String enableVoiceService) {
    this.enableVoiceService = enableVoiceService;
  }

  public SIPObject boundIfName(String boundIfName) {
    this.boundIfName = boundIfName;
    return this;
  }

  /**
   * Get boundIfName
   * @return boundIfName
  */
  @ApiModelProperty(value = "")


  public String getBoundIfName() {
    return boundIfName;
  }

  public void setBoundIfName(String boundIfName) {
    this.boundIfName = boundIfName;
  }

  public SIPObject boundIfNameArr(List<String> boundIfNameArr) {
    this.boundIfNameArr = boundIfNameArr;
    return this;
  }

  public SIPObject addBoundIfNameArrItem(String boundIfNameArrItem) {
    if (this.boundIfNameArr == null) {
      this.boundIfNameArr = new ArrayList<>();
    }
    this.boundIfNameArr.add(boundIfNameArrItem);
    return this;
  }

  /**
   * Get boundIfNameArr
   * @return boundIfNameArr
  */
  @ApiModelProperty(value = "")


  public List<String> getBoundIfNameArr() {
    return boundIfNameArr;
  }

  public void setBoundIfNameArr(List<String> boundIfNameArr) {
    this.boundIfNameArr = boundIfNameArr;
  }

  public SIPObject regions(List<String> regions) {
    this.regions = regions;
    return this;
  }

  public SIPObject addRegionsItem(String regionsItem) {
    if (this.regions == null) {
      this.regions = new ArrayList<>();
    }
    this.regions.add(regionsItem);
    return this;
  }

  /**
   * Get regions
   * @return regions
  */
  @ApiModelProperty(value = "")


  public List<String> getRegions() {
    return regions;
  }

  public void setRegions(List<String> regions) {
    this.regions = regions;
  }

  public SIPObject ipAddressFamily(String ipAddressFamily) {
    this.ipAddressFamily = ipAddressFamily;
    return this;
  }

  /**
   * Get ipAddressFamily
   * @return ipAddressFamily
  */
  @ApiModelProperty(value = "")


  public String getIpAddressFamily() {
    return ipAddressFamily;
  }

  public void setIpAddressFamily(String ipAddressFamily) {
    this.ipAddressFamily = ipAddressFamily;
  }

  public SIPObject region(String region) {
    this.region = region;
    return this;
  }

  /**
   * Get region
   * @return region
  */
  @ApiModelProperty(value = "")


  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public SIPObject digitMap(String digitMap) {
    this.digitMap = digitMap;
    return this;
  }

  /**
   * Get digitMap
   * @return digitMap
  */
  @ApiModelProperty(value = "")


  public String getDigitMap() {
    return digitMap;
  }

  public void setDigitMap(String digitMap) {
    this.digitMap = digitMap;
  }

  public SIPObject userAgentDomain(String userAgentDomain) {
    this.userAgentDomain = userAgentDomain;
    return this;
  }

  /**
   * Get userAgentDomain
   * @return userAgentDomain
  */
  @ApiModelProperty(value = "")


  public String getUserAgentDomain() {
    return userAgentDomain;
  }

  public void setUserAgentDomain(String userAgentDomain) {
    this.userAgentDomain = userAgentDomain;
  }

  public SIPObject proxyServer(String proxyServer) {
    this.proxyServer = proxyServer;
    return this;
  }

  /**
   * Get proxyServer
   * @return proxyServer
  */
  @ApiModelProperty(value = "")


  public String getProxyServer() {
    return proxyServer;
  }

  public void setProxyServer(String proxyServer) {
    this.proxyServer = proxyServer;
  }

  public SIPObject proxyServerPort(String proxyServerPort) {
    this.proxyServerPort = proxyServerPort;
    return this;
  }

  /**
   * Get proxyServerPort
   * @return proxyServerPort
  */
  @ApiModelProperty(value = "")


  public String getProxyServerPort() {
    return proxyServerPort;
  }

  public void setProxyServerPort(String proxyServerPort) {
    this.proxyServerPort = proxyServerPort;
  }

  public SIPObject outBoundProxy(String outBoundProxy) {
    this.outBoundProxy = outBoundProxy;
    return this;
  }

  /**
   * Get outBoundProxy
   * @return outBoundProxy
  */
  @ApiModelProperty(value = "")


  public String getOutBoundProxy() {
    return outBoundProxy;
  }

  public void setOutBoundProxy(String outBoundProxy) {
    this.outBoundProxy = outBoundProxy;
  }

  public SIPObject outBoundProxyPort(String outBoundProxyPort) {
    this.outBoundProxyPort = outBoundProxyPort;
    return this;
  }

  /**
   * Get outBoundProxyPort
   * @return outBoundProxyPort
  */
  @ApiModelProperty(value = "")


  public String getOutBoundProxyPort() {
    return outBoundProxyPort;
  }

  public void setOutBoundProxyPort(String outBoundProxyPort) {
    this.outBoundProxyPort = outBoundProxyPort;
  }

  public SIPObject registrarServer(String registrarServer) {
    this.registrarServer = registrarServer;
    return this;
  }

  /**
   * Get registrarServer
   * @return registrarServer
  */
  @ApiModelProperty(value = "")


  public String getRegistrarServer() {
    return registrarServer;
  }

  public void setRegistrarServer(String registrarServer) {
    this.registrarServer = registrarServer;
  }

  public SIPObject registrarServerPort(String registrarServerPort) {
    this.registrarServerPort = registrarServerPort;
    return this;
  }

  /**
   * Get registrarServerPort
   * @return registrarServerPort
  */
  @ApiModelProperty(value = "")


  public String getRegistrarServerPort() {
    return registrarServerPort;
  }

  public void setRegistrarServerPort(String registrarServerPort) {
    this.registrarServerPort = registrarServerPort;
  }

  public SIPObject dtmfMethod(String dtmfMethod) {
    this.dtmfMethod = dtmfMethod;
    return this;
  }

  /**
   * Get dtmfMethod
   * @return dtmfMethod
  */
  @ApiModelProperty(value = "")


  public String getDtmfMethod() {
    return dtmfMethod;
  }

  public void setDtmfMethod(String dtmfMethod) {
    this.dtmfMethod = dtmfMethod;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SIPObject siPObject = (SIPObject) o;
    return Objects.equals(this.enableVoiceService, siPObject.enableVoiceService) &&
        Objects.equals(this.boundIfName, siPObject.boundIfName) &&
        Objects.equals(this.boundIfNameArr, siPObject.boundIfNameArr) &&
        Objects.equals(this.regions, siPObject.regions) &&
        Objects.equals(this.ipAddressFamily, siPObject.ipAddressFamily) &&
        Objects.equals(this.region, siPObject.region) &&
        Objects.equals(this.digitMap, siPObject.digitMap) &&
        Objects.equals(this.userAgentDomain, siPObject.userAgentDomain) &&
        Objects.equals(this.proxyServer, siPObject.proxyServer) &&
        Objects.equals(this.proxyServerPort, siPObject.proxyServerPort) &&
        Objects.equals(this.outBoundProxy, siPObject.outBoundProxy) &&
        Objects.equals(this.outBoundProxyPort, siPObject.outBoundProxyPort) &&
        Objects.equals(this.registrarServer, siPObject.registrarServer) &&
        Objects.equals(this.registrarServerPort, siPObject.registrarServerPort) &&
        Objects.equals(this.dtmfMethod, siPObject.dtmfMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enableVoiceService, boundIfName, boundIfNameArr, regions, ipAddressFamily, region, digitMap, userAgentDomain, proxyServer, proxyServerPort, outBoundProxy, outBoundProxyPort, registrarServer, registrarServerPort, dtmfMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SIPObject {\n");
    
    sb.append("    enableVoiceService: ").append(toIndentedString(enableVoiceService)).append("\n");
    sb.append("    boundIfName: ").append(toIndentedString(boundIfName)).append("\n");
    sb.append("    boundIfNameArr: ").append(toIndentedString(boundIfNameArr)).append("\n");
    sb.append("    regions: ").append(toIndentedString(regions)).append("\n");
    sb.append("    ipAddressFamily: ").append(toIndentedString(ipAddressFamily)).append("\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    digitMap: ").append(toIndentedString(digitMap)).append("\n");
    sb.append("    userAgentDomain: ").append(toIndentedString(userAgentDomain)).append("\n");
    sb.append("    proxyServer: ").append(toIndentedString(proxyServer)).append("\n");
    sb.append("    proxyServerPort: ").append(toIndentedString(proxyServerPort)).append("\n");
    sb.append("    outBoundProxy: ").append(toIndentedString(outBoundProxy)).append("\n");
    sb.append("    outBoundProxyPort: ").append(toIndentedString(outBoundProxyPort)).append("\n");
    sb.append("    registrarServer: ").append(toIndentedString(registrarServer)).append("\n");
    sb.append("    registrarServerPort: ").append(toIndentedString(registrarServerPort)).append("\n");
    sb.append("    dtmfMethod: ").append(toIndentedString(dtmfMethod)).append("\n");
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

