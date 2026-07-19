package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.AdslDevice;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListAdslDevice
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ListAdslDevice   {
  @JsonProperty("totalCount")
  private Integer totalCount;

  @JsonProperty("adslDeviceList")
  @Valid
  private List<AdslDevice> adslDeviceList = null;

  public ListAdslDevice totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * Get totalCount
   * @return totalCount
  */
  @ApiModelProperty(value = "")


  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public ListAdslDevice adslDeviceList(List<AdslDevice> adslDeviceList) {
    this.adslDeviceList = adslDeviceList;
    return this;
  }

  public ListAdslDevice addAdslDeviceListItem(AdslDevice adslDeviceListItem) {
    if (this.adslDeviceList == null) {
      this.adslDeviceList = new ArrayList<>();
    }
    this.adslDeviceList.add(adslDeviceListItem);
    return this;
  }

  /**
   * Get adslDeviceList
   * @return adslDeviceList
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<AdslDevice> getAdslDeviceList() {
    return adslDeviceList;
  }

  public void setAdslDeviceList(List<AdslDevice> adslDeviceList) {
    this.adslDeviceList = adslDeviceList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListAdslDevice listAdslDevice = (ListAdslDevice) o;
    return Objects.equals(this.totalCount, listAdslDevice.totalCount) &&
        Objects.equals(this.adslDeviceList, listAdslDevice.adslDeviceList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalCount, adslDeviceList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListAdslDevice {\n");
    
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    adslDeviceList: ").append(toIndentedString(adslDeviceList)).append("\n");
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

