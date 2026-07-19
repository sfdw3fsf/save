package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BatchLog
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class BatchLog   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("batchID")
  private Integer batchID;

  @JsonProperty("opticalSerial")
  private String opticalSerial;

  @JsonProperty("status")
  private Integer status;

  @JsonProperty("description")
  private String description;

  @JsonProperty("type")
  private Integer type;

  @JsonProperty("Result")
  private String result;

  @JsonProperty("timeInsert")
  private BigDecimal timeInsert;

  @JsonProperty("timeFinish")
  private BigDecimal timeFinish;

  public BatchLog id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Thứ tự log
   * @return id
  */
  @ApiModelProperty(value = "Thứ tự log")


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BatchLog batchID(Integer batchID) {
    this.batchID = batchID;
    return this;
  }

  /**
   * Batch Session ID của lệnh
   * @return batchID
  */
  @ApiModelProperty(value = "Batch Session ID của lệnh")


  public Integer getBatchID() {
    return batchID;
  }

  public void setBatchID(Integer batchID) {
    this.batchID = batchID;
  }

  public BatchLog opticalSerial(String opticalSerial) {
    this.opticalSerial = opticalSerial;
    return this;
  }

  /**
   * Optical của thiết bị
   * @return opticalSerial
  */
  @ApiModelProperty(value = "Optical của thiết bị")


  public String getOpticalSerial() {
    return opticalSerial;
  }

  public void setOpticalSerial(String opticalSerial) {
    this.opticalSerial = opticalSerial;
  }

  public BatchLog status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * Trạng thái thực hiện lệnh:   * 1 : Đang thực hiện   * 2 : Success   * 3 : Error 
   * @return status
  */
  @ApiModelProperty(value = "Trạng thái thực hiện lệnh:   * 1 : Đang thực hiện   * 2 : Success   * 3 : Error ")


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public BatchLog description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Mô tả lệnh
   * @return description
  */
  @ApiModelProperty(value = "Mô tả lệnh")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BatchLog type(Integer type) {
    this.type = type;
    return this;
  }

  /**
   * Loại tác động: reboot, reset, …
   * @return type
  */
  @ApiModelProperty(value = "Loại tác động: reboot, reset, …")


  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public BatchLog result(String result) {
    this.result = result;
    return this;
  }

  /**
   * Mô tả kết quả thực hiện lệnh
   * @return result
  */
  @ApiModelProperty(value = "Mô tả kết quả thực hiện lệnh")


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public BatchLog timeInsert(BigDecimal timeInsert) {
    this.timeInsert = timeInsert;
    return this;
  }

  /**
   * Thời điểm thực hiện lệnh
   * @return timeInsert
  */
  @ApiModelProperty(value = "Thời điểm thực hiện lệnh")

  @Valid

  public BigDecimal getTimeInsert() {
    return timeInsert;
  }

  public void setTimeInsert(BigDecimal timeInsert) {
    this.timeInsert = timeInsert;
  }

  public BatchLog timeFinish(BigDecimal timeFinish) {
    this.timeFinish = timeFinish;
    return this;
  }

  /**
   * Thời điểm kết thúc lệnh
   * @return timeFinish
  */
  @ApiModelProperty(value = "Thời điểm kết thúc lệnh")

  @Valid

  public BigDecimal getTimeFinish() {
    return timeFinish;
  }

  public void setTimeFinish(BigDecimal timeFinish) {
    this.timeFinish = timeFinish;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchLog batchLog = (BatchLog) o;
    return Objects.equals(this.id, batchLog.id) &&
        Objects.equals(this.batchID, batchLog.batchID) &&
        Objects.equals(this.opticalSerial, batchLog.opticalSerial) &&
        Objects.equals(this.status, batchLog.status) &&
        Objects.equals(this.description, batchLog.description) &&
        Objects.equals(this.type, batchLog.type) &&
        Objects.equals(this.result, batchLog.result) &&
        Objects.equals(this.timeInsert, batchLog.timeInsert) &&
        Objects.equals(this.timeFinish, batchLog.timeFinish);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, batchID, opticalSerial, status, description, type, result, timeInsert, timeFinish);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchLog {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    batchID: ").append(toIndentedString(batchID)).append("\n");
    sb.append("    opticalSerial: ").append(toIndentedString(opticalSerial)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    timeInsert: ").append(toIndentedString(timeInsert)).append("\n");
    sb.append("    timeFinish: ").append(toIndentedString(timeFinish)).append("\n");
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

