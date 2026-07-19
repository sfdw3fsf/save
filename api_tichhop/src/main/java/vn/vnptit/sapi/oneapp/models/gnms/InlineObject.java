package vn.vnptit.sapi.oneapp.models.gnms;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.LineObject;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class InlineObject   {
  @JsonProperty("SIPObject")
  private Object siPObject;

  @JsonProperty("LineObject")
  @Valid
  private List<LineObject> lineObject = null;

  public InlineObject siPObject(Object siPObject) {
    this.siPObject = siPObject;
    return this;
  }

  /**
   * Get siPObject
   * @return siPObject
  */
  @ApiModelProperty(value = "")


  public Object getSiPObject() {
    return siPObject;
  }

  public void setSiPObject(Object siPObject) {
    this.siPObject = siPObject;
  }

  public InlineObject lineObject(List<LineObject> lineObject) {
    this.lineObject = lineObject;
    return this;
  }

  public InlineObject addLineObjectItem(LineObject lineObjectItem) {
    if (this.lineObject == null) {
      this.lineObject = new ArrayList<>();
    }
    this.lineObject.add(lineObjectItem);
    return this;
  }

  /**
   * Get lineObject
   * @return lineObject
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<LineObject> getLineObject() {
    return lineObject;
  }

  public void setLineObject(List<LineObject> lineObject) {
    this.lineObject = lineObject;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineObject inlineObject = (InlineObject) o;
    return Objects.equals(this.siPObject, inlineObject.siPObject) &&
        Objects.equals(this.lineObject, inlineObject.lineObject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(siPObject, lineObject);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineObject {\n");
    
    sb.append("    siPObject: ").append(toIndentedString(siPObject)).append("\n");
    sb.append("    lineObject: ").append(toIndentedString(lineObject)).append("\n");
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

