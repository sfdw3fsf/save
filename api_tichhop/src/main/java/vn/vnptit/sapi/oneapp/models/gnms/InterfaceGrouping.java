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
 * InterfaceGrouping
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class InterfaceGrouping   {
  @JsonProperty("index")
  private String index;

  @JsonProperty("groupName")
  private String groupName;

  @JsonProperty("wanInterface")
  private String wanInterface;

  @JsonProperty("lanInterface")
  private String lanInterface;

  @JsonProperty("wanInterfaceName")
  private String wanInterfaceName;

  @JsonProperty("lanInterfaceName")
  private String lanInterfaceName;

  public InterfaceGrouping index(String index) {
    this.index = index;
    return this;
  }

  /**
   * Get index
   * @return index
  */
  @ApiModelProperty(value = "")


  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public InterfaceGrouping groupName(String groupName) {
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

  public InterfaceGrouping wanInterface(String wanInterface) {
    this.wanInterface = wanInterface;
    return this;
  }

  /**
   * Get wanInterface
   * @return wanInterface
  */
  @ApiModelProperty(value = "")


  public String getWanInterface() {
    return wanInterface;
  }

  public void setWanInterface(String wanInterface) {
    this.wanInterface = wanInterface;
  }

  public InterfaceGrouping lanInterface(String lanInterface) {
    this.lanInterface = lanInterface;
    return this;
  }

  /**
   * Get lanInterface
   * @return lanInterface
  */
  @ApiModelProperty(value = "")


  public String getLanInterface() {
    return lanInterface;
  }

  public void setLanInterface(String lanInterface) {
    this.lanInterface = lanInterface;
  }

  public InterfaceGrouping wanInterfaceName(String wanInterfaceName) {
    this.wanInterfaceName = wanInterfaceName;
    return this;
  }

  /**
   * Get wanInterfaceName
   * @return wanInterfaceName
  */
  @ApiModelProperty(value = "")


  public String getWanInterfaceName() {
    return wanInterfaceName;
  }

  public void setWanInterfaceName(String wanInterfaceName) {
    this.wanInterfaceName = wanInterfaceName;
  }

  public InterfaceGrouping lanInterfaceName(String lanInterfaceName) {
    this.lanInterfaceName = lanInterfaceName;
    return this;
  }

  /**
   * Get lanInterfaceName
   * @return lanInterfaceName
  */
  @ApiModelProperty(value = "")


  public String getLanInterfaceName() {
    return lanInterfaceName;
  }

  public void setLanInterfaceName(String lanInterfaceName) {
    this.lanInterfaceName = lanInterfaceName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InterfaceGrouping interfaceGrouping = (InterfaceGrouping) o;
    return Objects.equals(this.index, interfaceGrouping.index) &&
        Objects.equals(this.groupName, interfaceGrouping.groupName) &&
        Objects.equals(this.wanInterface, interfaceGrouping.wanInterface) &&
        Objects.equals(this.lanInterface, interfaceGrouping.lanInterface) &&
        Objects.equals(this.wanInterfaceName, interfaceGrouping.wanInterfaceName) &&
        Objects.equals(this.lanInterfaceName, interfaceGrouping.lanInterfaceName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, groupName, wanInterface, lanInterface, wanInterfaceName, lanInterfaceName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InterfaceGrouping {\n");
    
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    wanInterface: ").append(toIndentedString(wanInterface)).append("\n");
    sb.append("    lanInterface: ").append(toIndentedString(lanInterface)).append("\n");
    sb.append("    wanInterfaceName: ").append(toIndentedString(wanInterfaceName)).append("\n");
    sb.append("    lanInterfaceName: ").append(toIndentedString(lanInterfaceName)).append("\n");
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

