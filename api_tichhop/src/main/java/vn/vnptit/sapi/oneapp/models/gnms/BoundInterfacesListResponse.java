package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BoundInterfacesListResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class BoundInterfacesListResponse   {
  @JsonProperty("boundInterfacesList")
  @Valid
  private List<String> boundInterfacesList = null;

  @JsonProperty("error_code")
  private String errorCode = "0";

  @JsonProperty("message")
  private String message = "success";

  @JsonProperty("result")
  private String result;

  public BoundInterfacesListResponse boundInterfacesList(List<String> boundInterfacesList) {
    this.boundInterfacesList = boundInterfacesList;
    return this;
  }

  public BoundInterfacesListResponse addBoundInterfacesListItem(String boundInterfacesListItem) {
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

  public BoundInterfacesListResponse errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * Kết quả thực hiện:  * `0` - success  * `501` - Device not found  * `502` - Device not connected  * `503` - Invalid parameter  * `500` - Other error  * `401` - Invalid user or password  * `504` - Keep silent  * `541` - Lỗi khác 
   * @return errorCode
  */
  @ApiModelProperty(value = "Kết quả thực hiện:  * `0` - success  * `501` - Device not found  * `502` - Device not connected  * `503` - Invalid parameter  * `500` - Other error  * `401` - Invalid user or password  * `504` - Keep silent  * `541` - Lỗi khác ")


  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public BoundInterfacesListResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @ApiModelProperty(value = "")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public BoundInterfacesListResponse result(String result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
  */
  @ApiModelProperty(value = "")


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoundInterfacesListResponse boundInterfacesListResponse = (BoundInterfacesListResponse) o;
    return Objects.equals(this.boundInterfacesList, boundInterfacesListResponse.boundInterfacesList) &&
        Objects.equals(this.errorCode, boundInterfacesListResponse.errorCode) &&
        Objects.equals(this.message, boundInterfacesListResponse.message) &&
        Objects.equals(this.result, boundInterfacesListResponse.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boundInterfacesList, errorCode, message, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoundInterfacesListResponse {\n");
    
    sb.append("    boundInterfacesList: ").append(toIndentedString(boundInterfacesList)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

