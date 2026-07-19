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
 * BatchInput
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class BatchInput   {
  @JsonProperty("listSerial")
  private String listSerial;

  @JsonProperty("actionType")
  private Integer actionType;

  @JsonProperty("actionParams")
  private String actionParams;

  public BatchInput listSerial(String listSerial) {
    this.listSerial = listSerial;
    return this;
  }

  /**
   * Get listSerial
   * @return listSerial
  */
  @ApiModelProperty(example = "VNPT00AAAfc4,vnpt00bbcf24,VNPT0072696a", value = "")


  public String getListSerial() {
    return listSerial;
  }

  public void setListSerial(String listSerial) {
    this.listSerial = listSerial;
  }

  public BatchInput actionType(Integer actionType) {
    this.actionType = actionType;
    return this;
  }

  /**
   * Loại file backup:   * 100 : reboot   * 101 : reset   * 104 : xóa file backup 
   * @return actionType
  */
  @ApiModelProperty(value = "Loại file backup:   * 100 : reboot   * 101 : reset   * 104 : xóa file backup ")


  public Integer getActionType() {
    return actionType;
  }

  public void setActionType(Integer actionType) {
    this.actionType = actionType;
  }

  public BatchInput actionParams(String actionParams) {
    this.actionParams = actionParams;
    return this;
  }

  /**
   * Get actionParams
   * @return actionParams
  */
  @ApiModelProperty(example = "11231756|112346378|tool_", value = "")


  public String getActionParams() {
    return actionParams;
  }

  public void setActionParams(String actionParams) {
    this.actionParams = actionParams;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchInput batchInput = (BatchInput) o;
    return Objects.equals(this.listSerial, batchInput.listSerial) &&
        Objects.equals(this.actionType, batchInput.actionType) &&
        Objects.equals(this.actionParams, batchInput.actionParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listSerial, actionType, actionParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchInput {\n");
    
    sb.append("    listSerial: ").append(toIndentedString(listSerial)).append("\n");
    sb.append("    actionType: ").append(toIndentedString(actionType)).append("\n");
    sb.append("    actionParams: ").append(toIndentedString(actionParams)).append("\n");
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

