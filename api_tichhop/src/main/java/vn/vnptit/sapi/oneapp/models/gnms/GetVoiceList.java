package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.GNMSResponse;
import vn.vnptit.sapi.oneapp.models.gnms.LineObject;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GetVoiceList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class GetVoiceList   {
  @JsonProperty("SIPObject")
  private Object siPObject;

  @JsonProperty("LineObject")
  @Valid
  private List<LineObject> lineObject = null;

  @JsonProperty("errorCode")
  private String errorCode;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public GetVoiceList siPObject(Object siPObject) {
    this.siPObject = siPObject;
    return this;
  }

  /**
   * Get siPObject
   * @return siPObject
  */
  @ApiModelProperty(value = "")


  public Object getSiPObject() {
    return siPObject;
  }

  public void setSiPObject(Object siPObject) {
    this.siPObject = siPObject;
  }

  public GetVoiceList lineObject(List<LineObject> lineObject) {
    this.lineObject = lineObject;
    return this;
  }

  public GetVoiceList addLineObjectItem(LineObject lineObjectItem) {
    if (this.lineObject == null) {
      this.lineObject = new ArrayList<>();
    }
    this.lineObject.add(lineObjectItem);
    return this;
  }

  /**
   * Get lineObject
   * @return lineObject
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<LineObject> getLineObject() {
    return lineObject;
  }

  public void setLineObject(List<LineObject> lineObject) {
    this.lineObject = lineObject;
  }

  public GetVoiceList errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * Get errorCode
   * @return errorCode
  */
  @ApiModelProperty(value = "")


  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public GetVoiceList errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Get errorMessage
   * @return errorMessage
  */
  @ApiModelProperty(value = "")


  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetVoiceList getVoiceList = (GetVoiceList) o;
    return Objects.equals(this.siPObject, getVoiceList.siPObject) &&
        Objects.equals(this.lineObject, getVoiceList.lineObject) &&
        Objects.equals(this.errorCode, getVoiceList.errorCode) &&
        Objects.equals(this.errorMessage, getVoiceList.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(siPObject, lineObject, errorCode, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetVoiceList {\n");
    
    sb.append("    siPObject: ").append(toIndentedString(siPObject)).append("\n");
    sb.append("    lineObject: ").append(toIndentedString(lineObject)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
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

