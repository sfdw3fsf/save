package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.GNMSResponse;
import vn.vnptit.sapi.oneapp.models.gnms.WanLanInterfaces;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WanInterfacesResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class WanInterfacesResponse   {
  @JsonProperty("wanInterfacesList")
  @Valid
  private List<WanLanInterfaces> wanInterfacesList = null;

  @JsonProperty("errorCode")
  private String errorCode;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public WanInterfacesResponse wanInterfacesList(List<WanLanInterfaces> wanInterfacesList) {
    this.wanInterfacesList = wanInterfacesList;
    return this;
  }

  public WanInterfacesResponse addWanInterfacesListItem(WanLanInterfaces wanInterfacesListItem) {
    if (this.wanInterfacesList == null) {
      this.wanInterfacesList = new ArrayList<>();
    }
    this.wanInterfacesList.add(wanInterfacesListItem);
    return this;
  }

  /**
   * Get wanInterfacesList
   * @return wanInterfacesList
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<WanLanInterfaces> getWanInterfacesList() {
    return wanInterfacesList;
  }

  public void setWanInterfacesList(List<WanLanInterfaces> wanInterfacesList) {
    this.wanInterfacesList = wanInterfacesList;
  }

  public WanInterfacesResponse errorCode(String errorCode) {
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

  public WanInterfacesResponse errorMessage(String errorMessage) {
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
    WanInterfacesResponse wanInterfacesResponse = (WanInterfacesResponse) o;
    return Objects.equals(this.wanInterfacesList, wanInterfacesResponse.wanInterfacesList) &&
        Objects.equals(this.errorCode, wanInterfacesResponse.errorCode) &&
        Objects.equals(this.errorMessage, wanInterfacesResponse.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wanInterfacesList, errorCode, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WanInterfacesResponse {\n");
    
    sb.append("    wanInterfacesList: ").append(toIndentedString(wanInterfacesList)).append("\n");
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

