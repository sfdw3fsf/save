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
 * TemplateZerotouch
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TemplateZerotouch   {
  @JsonProperty("id")
  private BigDecimal id;

  @JsonProperty("templateName")
  private String templateName;

  @JsonProperty("templateCode")
  private String templateCode;

  @JsonProperty("url")
  private String url;

  @JsonProperty("modelname")
  private String modelname;

  @JsonProperty("softwareVersion")
  private String softwareVersion;

  @JsonProperty("description")
  private String description;

  @JsonProperty("createDate")
  private String createDate;

  @JsonProperty("status")
  private BigDecimal status;

  @JsonProperty("inputFormat")
  private String inputFormat;

  public TemplateZerotouch id(BigDecimal id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public TemplateZerotouch templateName(String templateName) {
    this.templateName = templateName;
    return this;
  }

  /**
   * Get templateName
   * @return templateName
  */
  @ApiModelProperty(value = "")


  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public TemplateZerotouch templateCode(String templateCode) {
    this.templateCode = templateCode;
    return this;
  }

  /**
   * Get templateCode
   * @return templateCode
  */
  @ApiModelProperty(value = "")


  public String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }

  public TemplateZerotouch url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  */
  @ApiModelProperty(value = "")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public TemplateZerotouch modelname(String modelname) {
    this.modelname = modelname;
    return this;
  }

  /**
   * Get modelname
   * @return modelname
  */
  @ApiModelProperty(value = "")


  public String getModelname() {
    return modelname;
  }

  public void setModelname(String modelname) {
    this.modelname = modelname;
  }

  public TemplateZerotouch softwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
    return this;
  }

  /**
   * Get softwareVersion
   * @return softwareVersion
  */
  @ApiModelProperty(value = "")


  public String getSoftwareVersion() {
    return softwareVersion;
  }

  public void setSoftwareVersion(String softwareVersion) {
    this.softwareVersion = softwareVersion;
  }

  public TemplateZerotouch description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TemplateZerotouch createDate(String createDate) {
    this.createDate = createDate;
    return this;
  }

  /**
   * Get createDate
   * @return createDate
  */
  @ApiModelProperty(value = "")


  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public TemplateZerotouch status(BigDecimal status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getStatus() {
    return status;
  }

  public void setStatus(BigDecimal status) {
    this.status = status;
  }

  public TemplateZerotouch inputFormat(String inputFormat) {
    this.inputFormat = inputFormat;
    return this;
  }

  /**
   * Get inputFormat
   * @return inputFormat
  */
  @ApiModelProperty(value = "")


  public String getInputFormat() {
    return inputFormat;
  }

  public void setInputFormat(String inputFormat) {
    this.inputFormat = inputFormat;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateZerotouch templateZerotouch = (TemplateZerotouch) o;
    return Objects.equals(this.id, templateZerotouch.id) &&
        Objects.equals(this.templateName, templateZerotouch.templateName) &&
        Objects.equals(this.templateCode, templateZerotouch.templateCode) &&
        Objects.equals(this.url, templateZerotouch.url) &&
        Objects.equals(this.modelname, templateZerotouch.modelname) &&
        Objects.equals(this.softwareVersion, templateZerotouch.softwareVersion) &&
        Objects.equals(this.description, templateZerotouch.description) &&
        Objects.equals(this.createDate, templateZerotouch.createDate) &&
        Objects.equals(this.status, templateZerotouch.status) &&
        Objects.equals(this.inputFormat, templateZerotouch.inputFormat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, templateName, templateCode, url, modelname, softwareVersion, description, createDate, status, inputFormat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateZerotouch {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    templateName: ").append(toIndentedString(templateName)).append("\n");
    sb.append("    templateCode: ").append(toIndentedString(templateCode)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    modelname: ").append(toIndentedString(modelname)).append("\n");
    sb.append("    softwareVersion: ").append(toIndentedString(softwareVersion)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createDate: ").append(toIndentedString(createDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    inputFormat: ").append(toIndentedString(inputFormat)).append("\n");
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

