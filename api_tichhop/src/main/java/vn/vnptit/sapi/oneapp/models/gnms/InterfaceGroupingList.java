package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.GNMSResponse;
import vn.vnptit.sapi.oneapp.models.gnms.InterfaceGrouping;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InterfaceGroupingList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class InterfaceGroupingList   {
  @JsonProperty("interfaceGroupingList")
  @Valid
  private List<InterfaceGrouping> interfaceGroupingList = null;

  @JsonProperty("errorCode")
  private String errorCode;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public InterfaceGroupingList interfaceGroupingList(List<InterfaceGrouping> interfaceGroupingList) {
    this.interfaceGroupingList = interfaceGroupingList;
    return this;
  }

  public InterfaceGroupingList addInterfaceGroupingListItem(InterfaceGrouping interfaceGroupingListItem) {
    if (this.interfaceGroupingList == null) {
      this.interfaceGroupingList = new ArrayList<>();
    }
    this.interfaceGroupingList.add(interfaceGroupingListItem);
    return this;
  }

  /**
   * Get interfaceGroupingList
   * @return interfaceGroupingList
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<InterfaceGrouping> getInterfaceGroupingList() {
    return interfaceGroupingList;
  }

  public void setInterfaceGroupingList(List<InterfaceGrouping> interfaceGroupingList) {
    this.interfaceGroupingList = interfaceGroupingList;
  }

  public InterfaceGroupingList errorCode(String errorCode) {
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

  public InterfaceGroupingList errorMessage(String errorMessage) {
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
    InterfaceGroupingList interfaceGroupingList = (InterfaceGroupingList) o;
    return Objects.equals(this.interfaceGroupingList, interfaceGroupingList.interfaceGroupingList) &&
        Objects.equals(this.errorCode, interfaceGroupingList.errorCode) &&
        Objects.equals(this.errorMessage, interfaceGroupingList.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interfaceGroupingList, errorCode, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InterfaceGroupingList {\n");
    
    sb.append("    interfaceGroupingList: ").append(toIndentedString(interfaceGroupingList)).append("\n");
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

