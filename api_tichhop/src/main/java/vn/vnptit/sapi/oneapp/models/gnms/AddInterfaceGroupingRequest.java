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
 * AddInterfaceGroupingRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AddInterfaceGroupingRequest   {
  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("wanInterface")
  private String wanInterface;

  @JsonProperty("lanInterface")
  private String lanInterface;

  public AddInterfaceGroupingRequest groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  /**
   * Get groupName
   * @return groupName
  */
  @ApiModelProperty(value = "")


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public AddInterfaceGroupingRequest wanInterface(String wanInterface) {
    this.wanInterface = wanInterface;
    return this;
  }

  /**
   * WAN Interface ID: Get from API getWanInterfacesList
   * @return wanInterface
  */
  @ApiModelProperty(value = "WAN Interface ID: Get from API getWanInterfacesList")


  public String getWanInterface() {
    return wanInterface;
  }

  public void setWanInterface(String wanInterface) {
    this.wanInterface = wanInterface;
  }

  public AddInterfaceGroupingRequest lanInterface(String lanInterface) {
    this.lanInterface = lanInterface;
    return this;
  }

  /**
   * LAN Interface ID: Get from API getLanInterfacesList
   * @return lanInterface
  */
  @ApiModelProperty(value = "LAN Interface ID: Get from API getLanInterfacesList")


  public String getLanInterface() {
    return lanInterface;
  }

  public void setLanInterface(String lanInterface) {
    this.lanInterface = lanInterface;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddInterfaceGroupingRequest addInterfaceGroupingRequest = (AddInterfaceGroupingRequest) o;
    return Objects.equals(this.groupName, addInterfaceGroupingRequest.groupName) &&
        Objects.equals(this.wanInterface, addInterfaceGroupingRequest.wanInterface) &&
        Objects.equals(this.lanInterface, addInterfaceGroupingRequest.lanInterface);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupName, wanInterface, lanInterface);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddInterfaceGroupingRequest {\n");
    
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    wanInterface: ").append(toIndentedString(wanInterface)).append("\n");
    sb.append("    lanInterface: ").append(toIndentedString(lanInterface)).append("\n");
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

