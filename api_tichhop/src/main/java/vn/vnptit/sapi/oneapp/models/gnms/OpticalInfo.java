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
 * OpticalInfo
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class OpticalInfo   {
  @JsonProperty("txBias")
  private Integer txBias;

  @JsonProperty("rxVcc")
  private Integer rxVcc;

  @JsonProperty("bOSAtemperature")
  private Integer bOSAtemperature;

  @JsonProperty("rxPower")
  private Integer rxPower;

  @JsonProperty("txPower")
  private Integer txPower;

  public OpticalInfo txBias(Integer txBias) {
    this.txBias = txBias;
    return this;
  }

  /**
   * Get txBias
   * @return txBias
  */
  @ApiModelProperty(value = "")


  public Integer getTxBias() {
    return txBias;
  }

  public void setTxBias(Integer txBias) {
    this.txBias = txBias;
  }

  public OpticalInfo rxVcc(Integer rxVcc) {
    this.rxVcc = rxVcc;
    return this;
  }

  /**
   * Get rxVcc
   * @return rxVcc
  */
  @ApiModelProperty(value = "")


  public Integer getRxVcc() {
    return rxVcc;
  }

  public void setRxVcc(Integer rxVcc) {
    this.rxVcc = rxVcc;
  }

  public OpticalInfo bOSAtemperature(Integer bOSAtemperature) {
    this.bOSAtemperature = bOSAtemperature;
    return this;
  }

  /**
   * Get bOSAtemperature
   * @return bOSAtemperature
  */
  @ApiModelProperty(value = "")


  public Integer getbOSAtemperature() {
    return bOSAtemperature;
  }

  public void setbOSAtemperature(Integer bOSAtemperature) {
    this.bOSAtemperature = bOSAtemperature;
  }

  public OpticalInfo rxPower(Integer rxPower) {
    this.rxPower = rxPower;
    return this;
  }

  /**
   * Get rxPower
   * @return rxPower
  */
  @ApiModelProperty(value = "")


  public Integer getRxPower() {
    return rxPower;
  }

  public void setRxPower(Integer rxPower) {
    this.rxPower = rxPower;
  }

  public OpticalInfo txPower(Integer txPower) {
    this.txPower = txPower;
    return this;
  }

  /**
   * Get txPower
   * @return txPower
  */
  @ApiModelProperty(value = "")


  public Integer getTxPower() {
    return txPower;
  }

  public void setTxPower(Integer txPower) {
    this.txPower = txPower;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpticalInfo opticalInfo = (OpticalInfo) o;
    return Objects.equals(this.txBias, opticalInfo.txBias) &&
        Objects.equals(this.rxVcc, opticalInfo.rxVcc) &&
        Objects.equals(this.bOSAtemperature, opticalInfo.bOSAtemperature) &&
        Objects.equals(this.rxPower, opticalInfo.rxPower) &&
        Objects.equals(this.txPower, opticalInfo.txPower);
  }

  @Override
  public int hashCode() {
    return Objects.hash(txBias, rxVcc, bOSAtemperature, rxPower, txPower);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpticalInfo {\n");
    
    sb.append("    txBias: ").append(toIndentedString(txBias)).append("\n");
    sb.append("    rxVcc: ").append(toIndentedString(rxVcc)).append("\n");
    sb.append("    bOSAtemperature: ").append(toIndentedString(bOSAtemperature)).append("\n");
    sb.append("    rxPower: ").append(toIndentedString(rxPower)).append("\n");
    sb.append("    txPower: ").append(toIndentedString(txPower)).append("\n");
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

