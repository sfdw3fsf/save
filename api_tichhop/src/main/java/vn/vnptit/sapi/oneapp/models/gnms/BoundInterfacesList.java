package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.GNMSResponse;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BoundInterfacesList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class BoundInterfacesList   {
  @JsonProperty("boundInterfacesList")
  @Valid
  private List<String> boundInterfacesList = null;

  @JsonProperty("errorCode")
  private String errorCode;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public BoundInterfacesList boundInterfacesList(List<String> boundInterfacesList) {
    this.boundInterfacesList = boundInterfacesList;
    return this;
  }

  public BoundInterfacesList addBoundInterfacesListItem(String boundInterfacesListItem) {
    if (this.boundInterfacesList == null) {
      this.boundInterfacesList = new ArrayList<>();
    }
    this.boundInterfacesList.add(boundInterfacesListItem);
    return this;
  }

  /**
   * Get boundInterfacesList
   * @return boundInterfacesList
  */
  @ApiModelProperty(value = "")


  public List<String> getBoundInterfacesList() {
    return boundInterfacesList;
  }

  public void setBoundInterfacesList(List<String> boundInterfacesList) {
    this.boundInterfacesList = boundInterfacesList;
  }

  public BoundInterfacesList errorCode(String errorCode) {
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

  public BoundInterfacesList errorMessage(String errorMessage) {
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
    BoundInterfacesList boundInterfacesList = (BoundInterfacesList) o;
    return Objects.equals(this.boundInterfacesList, boundInterfacesList.boundInterfacesList) &&
        Objects.equals(this.errorCode, boundInterfacesList.errorCode) &&
        Objects.equals(this.errorMessage, boundInterfacesList.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boundInterfacesList, errorCode, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoundInterfacesList {\n");
    
    sb.append("    boundInterfacesList: ").append(toIndentedString(boundInterfacesList)).append("\n");
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

