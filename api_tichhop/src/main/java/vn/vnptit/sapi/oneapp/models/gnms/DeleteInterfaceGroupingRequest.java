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
 * DeleteInterfaceGroupingRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DeleteInterfaceGroupingRequest   {
  @JsonProperty("index")
  private String index;

  @JsonProperty("wanInterfaceId")
  private String wanInterfaceId;

  @JsonProperty("lanInterfaceId")
  private String lanInterfaceId;

  public DeleteInterfaceGroupingRequest index(String index) {
    this.index = index;
    return this;
  }

  /**
   * ID of group: Get from API getInterfaceGroupingList
   * @return index
  */
  @ApiModelProperty(value = "ID of group: Get from API getInterfaceGroupingList")


  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public DeleteInterfaceGroupingRequest wanInterfaceId(String wanInterfaceId) {
    this.wanInterfaceId = wanInterfaceId;
    return this;
  }

  /**
   * WAN ID
   * @return wanInterfaceId
  */
  @ApiModelProperty(value = "WAN ID")


  public String getWanInterfaceId() {
    return wanInterfaceId;
  }

  public void setWanInterfaceId(String wanInterfaceId) {
    this.wanInterfaceId = wanInterfaceId;
  }

  public DeleteInterfaceGroupingRequest lanInterfaceId(String lanInterfaceId) {
    this.lanInterfaceId = lanInterfaceId;
    return this;
  }

  /**
   * List LAN ID
   * @return lanInterfaceId
  */
  @ApiModelProperty(value = "List LAN ID")


  public String getLanInterfaceId() {
    return lanInterfaceId;
  }

  public void setLanInterfaceId(String lanInterfaceId) {
    this.lanInterfaceId = lanInterfaceId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteInterfaceGroupingRequest deleteInterfaceGroupingRequest = (DeleteInterfaceGroupingRequest) o;
    return Objects.equals(this.index, deleteInterfaceGroupingRequest.index) &&
        Objects.equals(this.wanInterfaceId, deleteInterfaceGroupingRequest.wanInterfaceId) &&
        Objects.equals(this.lanInterfaceId, deleteInterfaceGroupingRequest.lanInterfaceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, wanInterfaceId, lanInterfaceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteInterfaceGroupingRequest {\n");
    
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    wanInterfaceId: ").append(toIndentedString(wanInterfaceId)).append("\n");
    sb.append("    lanInterfaceId: ").append(toIndentedString(lanInterfaceId)).append("\n");
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

