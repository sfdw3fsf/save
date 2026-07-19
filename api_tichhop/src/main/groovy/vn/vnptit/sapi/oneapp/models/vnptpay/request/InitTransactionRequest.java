package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * InitTransactionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class InitTransactionRequest {
    @JsonProperty("transferCode")
    private String transferCode = null;
    @JsonProperty("transferType")
    private String transferType = null;
    @JsonProperty("transferAmount")
    private String transferAmount = null;
    @JsonProperty("transferContent")
    private String transferContent = null;
    @JsonProperty("sendWalletId")
    private String sendWalletId = null;
    @JsonProperty("collaboratorId")
    private String collaboratorId = null;
    @JsonProperty("customerId")
    private String customerId = null;
    @JsonProperty("sendWalletAccountId")
    private String sendWalletAccountId = null;
    @JsonProperty("groupId")
    private String groupId = null;
    @JsonProperty("sellingDepartmentId")
    private String sellingDepartmentId = null;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSellingDepartmentId() {
        return sellingDepartmentId;
    }

    public void setSellingDepartmentId(String sellingDepartmentId) {
        this.sellingDepartmentId = sellingDepartmentId;
    }

    public String getTransferCode() {
        return transferCode;
    }

    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferContent() {
        return transferContent;
    }

    public void setTransferContent(String transferContent) {
        this.transferContent = transferContent;
    }

    public String getSendWalletId() {
        return sendWalletId;
    }

    public void setSendWalletId(String sendWalletId) {
        this.sendWalletId = sendWalletId;
    }

    public String getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(String collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSendWalletAccountId() {
        return sendWalletAccountId;
    }

    public void setSendWalletAccountId(String sendWalletAccountId) {
        this.sendWalletAccountId = sendWalletAccountId;
    }
}
