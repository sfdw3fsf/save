package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CommitTransactionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class CommitTransactionRequest {
  @JsonProperty("transferResult")
  private String transferResult = null;
  @JsonProperty("transferType")
  private String transferType = null;
  @JsonProperty("transferId")
  private String transferId = null;
  @JsonProperty("transferContent")
  private String transferContent = null;
  @JsonProperty("phieuId")
  private String phieuId = null;
  @JsonProperty("confirmType")
  private String confirmType = null;

  public String getTransferResult() {
    return transferResult;
  }

  public void setTransferResult(String transferResult) {
    this.transferResult = transferResult;
  }

  public String getTransferType() {
    return transferType;
  }

  public void setTransferType(String transferType) {
    this.transferType = transferType;
  }

  public String getTransferId() {
    return transferId;
  }

  public void setTransferId(String transferId) {
    this.transferId = transferId;
  }

  public String getTransferContent() {
    return transferContent;
  }

  public void setTransferContent(String transferContent) {
    this.transferContent = transferContent;
  }

  public String getPhieuId() {
    return phieuId;
  }

  public void setPhieuId(String phieuId) {
    this.phieuId = phieuId;
  }

  public String getConfirmType() {
    return confirmType;
  }

  public void setConfirmType(String confirmType) {
    this.confirmType = confirmType;
  }
}
