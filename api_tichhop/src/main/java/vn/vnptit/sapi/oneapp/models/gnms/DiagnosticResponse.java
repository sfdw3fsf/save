package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DownloadResult;
import vn.vnptit.sapi.oneapp.models.gnms.PingResult;
import vn.vnptit.sapi.oneapp.models.gnms.UploadResult;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DiagnosticResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DiagnosticResponse   {
  @JsonProperty("pingResult")
  private PingResult pingResult;

  @JsonProperty("downloadResult")
  private DownloadResult downloadResult;

  @JsonProperty("uploadResult")
  private UploadResult uploadResult;

  @JsonProperty("error_code")
  private String errorCode = "0";

  @JsonProperty("message")
  private String message = "success";

  @JsonProperty("result")
  private String result;

  public DiagnosticResponse pingResult(PingResult pingResult) {
    this.pingResult = pingResult;
    return this;
  }

  /**
   * Get pingResult
   * @return pingResult
  */
  @ApiModelProperty(value = "")

  @Valid

  public PingResult getPingResult() {
    return pingResult;
  }

  public void setPingResult(PingResult pingResult) {
    this.pingResult = pingResult;
  }

  public DiagnosticResponse downloadResult(DownloadResult downloadResult) {
    this.downloadResult = downloadResult;
    return this;
  }

  /**
   * Get downloadResult
   * @return downloadResult
  */
  @ApiModelProperty(value = "")

  @Valid

  public DownloadResult getDownloadResult() {
    return downloadResult;
  }

  public void setDownloadResult(DownloadResult downloadResult) {
    this.downloadResult = downloadResult;
  }

  public DiagnosticResponse uploadResult(UploadResult uploadResult) {
    this.uploadResult = uploadResult;
    return this;
  }

  /**
   * Get uploadResult
   * @return uploadResult
  */
  @ApiModelProperty(value = "")

  @Valid

  public UploadResult getUploadResult() {
    return uploadResult;
  }

  public void setUploadResult(UploadResult uploadResult) {
    this.uploadResult = uploadResult;
  }

  public DiagnosticResponse errorCode(String errorCode) {
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

  public DiagnosticResponse message(String message) {
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

  public DiagnosticResponse result(String result) {
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
    DiagnosticResponse diagnosticResponse = (DiagnosticResponse) o;
    return Objects.equals(this.pingResult, diagnosticResponse.pingResult) &&
        Objects.equals(this.downloadResult, diagnosticResponse.downloadResult) &&
        Objects.equals(this.uploadResult, diagnosticResponse.uploadResult) &&
        Objects.equals(this.errorCode, diagnosticResponse.errorCode) &&
        Objects.equals(this.message, diagnosticResponse.message) &&
        Objects.equals(this.result, diagnosticResponse.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pingResult, downloadResult, uploadResult, errorCode, message, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiagnosticResponse {\n");
    
    sb.append("    pingResult: ").append(toIndentedString(pingResult)).append("\n");
    sb.append("    downloadResult: ").append(toIndentedString(downloadResult)).append("\n");
    sb.append("    uploadResult: ").append(toIndentedString(uploadResult)).append("\n");
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

