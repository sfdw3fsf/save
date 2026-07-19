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
 * LanInterfacesResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class LanInterfacesResponse   {
  @JsonProperty("lanInterfacesList")
  @Valid
  private List<WanLanInterfaces> lanInterfacesList = null;

  @JsonProperty("errorCode")
  private String errorCode;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public LanInterfacesResponse lanInterfacesList(List<WanLanInterfaces> lanInterfacesList) {
    this.lanInterfacesList = lanInterfacesList;
    return this;
  }

  public LanInterfacesResponse addLanInterfacesListItem(WanLanInterfaces lanInterfacesListItem) {
    if (this.lanInterfacesList == null) {
      this.lanInterfacesList = new ArrayList<>();
    }
    this.lanInterfacesList.add(lanInterfacesListItem);
    return this;
  }

  /**
   * Get lanInterfacesList
   * @return lanInterfacesList
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<WanLanInterfaces> getLanInterfacesList() {
    return lanInterfacesList;
  }

  public void setLanInterfacesList(List<WanLanInterfaces> lanInterfacesList) {
    this.lanInterfacesList = lanInterfacesList;
  }

  public LanInterfacesResponse errorCode(String errorCode) {
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

  public LanInterfacesResponse errorMessage(String errorMessage) {
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
    LanInterfacesResponse lanInterfacesResponse = (LanInterfacesResponse) o;
    return Objects.equals(this.lanInterfacesList, lanInterfacesResponse.lanInterfacesList) &&
        Objects.equals(this.errorCode, lanInterfacesResponse.errorCode) &&
        Objects.equals(this.errorMessage, lanInterfacesResponse.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lanInterfacesList, errorCode, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LanInterfacesResponse {\n");
    
    sb.append("    lanInterfacesList: ").append(toIndentedString(lanInterfacesList)).append("\n");
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

