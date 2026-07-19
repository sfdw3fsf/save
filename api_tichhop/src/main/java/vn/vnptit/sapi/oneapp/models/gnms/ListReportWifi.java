package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.ReportWifi;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListReportWifi
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ListReportWifi   {
  @JsonProperty("total")
  private Integer total;

  @JsonProperty("start")
  private Integer start;

  @JsonProperty("limit")
  private Integer limit;

  @JsonProperty("listReportWifi")
  @Valid
  private List<ReportWifi> listReportWifi = null;

  public ListReportWifi total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  */
  @ApiModelProperty(value = "")


  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public ListReportWifi start(Integer start) {
    this.start = start;
    return this;
  }

  /**
   * Get start
   * @return start
  */
  @ApiModelProperty(value = "")


  public Integer getStart() {
    return start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public ListReportWifi limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  /**
   * Get limit
   * @return limit
  */
  @ApiModelProperty(value = "")


  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public ListReportWifi listReportWifi(List<ReportWifi> listReportWifi) {
    this.listReportWifi = listReportWifi;
    return this;
  }

  public ListReportWifi addListReportWifiItem(ReportWifi listReportWifiItem) {
    if (this.listReportWifi == null) {
      this.listReportWifi = new ArrayList<>();
    }
    this.listReportWifi.add(listReportWifiItem);
    return this;
  }

  /**
   * Get listReportWifi
   * @return listReportWifi
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<ReportWifi> getListReportWifi() {
    return listReportWifi;
  }

  public void setListReportWifi(List<ReportWifi> listReportWifi) {
    this.listReportWifi = listReportWifi;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListReportWifi listReportWifi = (ListReportWifi) o;
    return Objects.equals(this.total, listReportWifi.total) &&
        Objects.equals(this.start, listReportWifi.start) &&
        Objects.equals(this.limit, listReportWifi.limit) &&
        Objects.equals(this.listReportWifi, listReportWifi.listReportWifi);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, start, limit, listReportWifi);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListReportWifi {\n");
    
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    listReportWifi: ").append(toIndentedString(listReportWifi)).append("\n");
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

