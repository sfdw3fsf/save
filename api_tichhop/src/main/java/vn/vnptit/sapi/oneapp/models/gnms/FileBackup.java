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
 * FileBackup
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class FileBackup   {
  @JsonProperty("url")
  private String url;

  @JsonProperty("backup_date")
  private String backupDate;

  @JsonProperty("opticalSerial")
  private String opticalSerial;

  @JsonProperty("type")
  private Integer type;

  @JsonProperty("softwareVersion")
  private String softwareVersion;

  @JsonProperty("areaID")
  private String areaID;

  @JsonProperty("areaName")
  private String areaName;

  @JsonProperty("menID")
  private String menID;

  public FileBackup url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Đường dẫn file backup
   * @return url
  */
  @ApiModelProperty(value = "Đường dẫn file backup")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public FileBackup backupDate(String backupDate) {
    this.backupDate = backupDate;
    return this;
  }

  /**
   * Ngày thực hiện backup
   * @return backupDate
  */
  @ApiModelProperty(value = "Ngày thực hiện backup")


  public String getBackupDate() {
    return backupDate;
  }

  public void setBackupDate(String backupDate) {
    this.backupDate = backupDate;
  }

  public FileBackup opticalSerial(String opticalSerial) {
    this.opticalSerial = opticalSerial;
    return this;
  }

  /**
   * Mã định danh thiết bị
   * @return opticalSerial
  */
  @ApiModelProperty(value = "Mã định danh thiết bị")


  public String getOpticalSerial() {
    return opticalSerial;
  }

  public void setOpticalSerial(String opticalSerial) {
    this.opticalSerial = opticalSerial;
  }

  public FileBackup type(Integer type) {
    this.type = type;
    return this;
  }

  /**
   * Loại file backup:   * 0 : tự động   * 1 : manual 
   * @return type
  */
  @ApiModelProperty(value = "Loại file backup:   * 0 : tự động   * 1 : manual ")


  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public FileBackup softwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
    return this;
  }

  /**
   * Phiên bản firmware của ONT
   * @return softwareVersion
  */
  @ApiModelProperty(value = "Phiên bản firmware của ONT")


  public String getSoftwareVersion() {
    return softwareVersion;
  }

  public void setSoftwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
  }

  public FileBackup areaID(String areaID) {
    this.areaID = areaID;
    return this;
  }

  /**
   * Mã định danh khu vực của ONT
   * @return areaID
  */
  @ApiModelProperty(value = "Mã định danh khu vực của ONT")


  public String getAreaID() {
    return areaID;
  }

  public void setAreaID(String areaID) {
    this.areaID = areaID;
  }

  public FileBackup areaName(String areaName) {
    this.areaName = areaName;
    return this;
  }

  /**
   * Tên khu vực
   * @return areaName
  */
  @ApiModelProperty(value = "Tên khu vực")


  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public FileBackup menID(String menID) {
    this.menID = menID;
    return this;
  }

  /**
   * Mã MEN của thiết bị
   * @return menID
  */
  @ApiModelProperty(value = "Mã MEN của thiết bị")


  public String getMenID() {
    return menID;
  }

  public void setMenID(String menID) {
    this.menID = menID;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileBackup fileBackup = (FileBackup) o;
    return Objects.equals(this.url, fileBackup.url) &&
        Objects.equals(this.backupDate, fileBackup.backupDate) &&
        Objects.equals(this.opticalSerial, fileBackup.opticalSerial) &&
        Objects.equals(this.type, fileBackup.type) &&
        Objects.equals(this.softwareVersion, fileBackup.softwareVersion) &&
        Objects.equals(this.areaID, fileBackup.areaID) &&
        Objects.equals(this.areaName, fileBackup.areaName) &&
        Objects.equals(this.menID, fileBackup.menID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, backupDate, opticalSerial, type, softwareVersion, areaID, areaName, menID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileBackup {\n");
    
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    backupDate: ").append(toIndentedString(backupDate)).append("\n");
    sb.append("    opticalSerial: ").append(toIndentedString(opticalSerial)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    softwareVersion: ").append(toIndentedString(softwareVersion)).append("\n");
    sb.append("    areaID: ").append(toIndentedString(areaID)).append("\n");
    sb.append("    areaName: ").append(toIndentedString(areaName)).append("\n");
    sb.append("    menID: ").append(toIndentedString(menID)).append("\n");
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

