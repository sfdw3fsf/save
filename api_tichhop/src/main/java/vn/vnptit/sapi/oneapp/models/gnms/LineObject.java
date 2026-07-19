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
 * LineObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class LineObject   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("enable")
  private String enable;

  @JsonProperty("callIDNamer")
  private String callIDNamer;

  @JsonProperty("sipAuthUserName")
  private String sipAuthUserName;

  @JsonProperty("sipAuthUserPass")
  private String sipAuthUserPass;

  @JsonProperty("sipUri")
  private String sipUri;

  @JsonProperty("callStatus")
  private String callStatus;

  @JsonProperty("callState")
  private String callState;

  @JsonProperty("callWaitingEnable")
  private String callWaitingEnable;

  public LineObject id(Integer id) {
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

  public LineObject enable(String enable) {
    this.enable = enable;
    return this;
  }

  /**
   * Get enable
   * @return enable
  */
  @ApiModelProperty(value = "")


  public String getEnable() {
    return enable;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }

  public LineObject callIDNamer(String callIDNamer) {
    this.callIDNamer = callIDNamer;
    return this;
  }

  /**
   * Get callIDNamer
   * @return callIDNamer
  */
  @ApiModelProperty(value = "")


  public String getCallIDNamer() {
    return callIDNamer;
  }

  public void setCallIDNamer(String callIDNamer) {
    this.callIDNamer = callIDNamer;
  }

  public LineObject sipAuthUserName(String sipAuthUserName) {
    this.sipAuthUserName = sipAuthUserName;
    return this;
  }

  /**
   * Get sipAuthUserName
   * @return sipAuthUserName
  */
  @ApiModelProperty(value = "")


  public String getSipAuthUserName() {
    return sipAuthUserName;
  }

  public void setSipAuthUserName(String sipAuthUserName) {
    this.sipAuthUserName = sipAuthUserName;
  }

  public LineObject sipAuthUserPass(String sipAuthUserPass) {
    this.sipAuthUserPass = sipAuthUserPass;
    return this;
  }

  /**
   * Get sipAuthUserPass
   * @return sipAuthUserPass
  */
  @ApiModelProperty(value = "")


  public String getSipAuthUserPass() {
    return sipAuthUserPass;
  }

  public void setSipAuthUserPass(String sipAuthUserPass) {
    this.sipAuthUserPass = sipAuthUserPass;
  }

  public LineObject sipUri(String sipUri) {
    this.sipUri = sipUri;
    return this;
  }

  /**
   * Get sipUri
   * @return sipUri
  */
  @ApiModelProperty(value = "")


  public String getSipUri() {
    return sipUri;
  }

  public void setSipUri(String sipUri) {
    this.sipUri = sipUri;
  }

  public LineObject callStatus(String callStatus) {
    this.callStatus = callStatus;
    return this;
  }

  /**
   * Get callStatus
   * @return callStatus
  */
  @ApiModelProperty(value = "")


  public String getCallStatus() {
    return callStatus;
  }

  public void setCallStatus(String callStatus) {
    this.callStatus = callStatus;
  }

  public LineObject callState(String callState) {
    this.callState = callState;
    return this;
  }

  /**
   * Get callState
   * @return callState
  */
  @ApiModelProperty(value = "")


  public String getCallState() {
    return callState;
  }

  public void setCallState(String callState) {
    this.callState = callState;
  }

  public LineObject callWaitingEnable(String callWaitingEnable) {
    this.callWaitingEnable = callWaitingEnable;
    return this;
  }

  /**
   * Get callWaitingEnable
   * @return callWaitingEnable
  */
  @ApiModelProperty(value = "")


  public String getCallWaitingEnable() {
    return callWaitingEnable;
  }

  public void setCallWaitingEnable(String callWaitingEnable) {
    this.callWaitingEnable = callWaitingEnable;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LineObject lineObject = (LineObject) o;
    return Objects.equals(this.id, lineObject.id) &&
        Objects.equals(this.enable, lineObject.enable) &&
        Objects.equals(this.callIDNamer, lineObject.callIDNamer) &&
        Objects.equals(this.sipAuthUserName, lineObject.sipAuthUserName) &&
        Objects.equals(this.sipAuthUserPass, lineObject.sipAuthUserPass) &&
        Objects.equals(this.sipUri, lineObject.sipUri) &&
        Objects.equals(this.callStatus, lineObject.callStatus) &&
        Objects.equals(this.callState, lineObject.callState) &&
        Objects.equals(this.callWaitingEnable, lineObject.callWaitingEnable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, enable, callIDNamer, sipAuthUserName, sipAuthUserPass, sipUri, callStatus, callState, callWaitingEnable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LineObject {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    enable: ").append(toIndentedString(enable)).append("\n");
    sb.append("    callIDNamer: ").append(toIndentedString(callIDNamer)).append("\n");
    sb.append("    sipAuthUserName: ").append(toIndentedString(sipAuthUserName)).append("\n");
    sb.append("    sipAuthUserPass: ").append(toIndentedString(sipAuthUserPass)).append("\n");
    sb.append("    sipUri: ").append(toIndentedString(sipUri)).append("\n");
    sb.append("    callStatus: ").append(toIndentedString(callStatus)).append("\n");
    sb.append("    callState: ").append(toIndentedString(callState)).append("\n");
    sb.append("    callWaitingEnable: ").append(toIndentedString(callWaitingEnable)).append("\n");
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

