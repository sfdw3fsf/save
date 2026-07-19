package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CancelTransactionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class BanHangTransactionRequest {
    @JsonProperty("sendWalletId")
    private String sendWalletId = null;
    @JsonProperty("sendWalletAccountId")
    private String sendWalletAccountId = null;
    @JsonProperty("transferType")
    private String transferType = null;
    @JsonProperty("transferAmount")
    private String transferAmount = null;
    @JsonProperty("transferContent")
    private String transferContent = null;
    @JsonProperty("paymentCode")
    private String paymentCode = null;
    @JsonProperty("agencyId")
    private String agencyId = null;
    @JsonProperty("employeeId")
    private String employeeId = null;
    @JsonProperty("groupId")
    private String groupId = null;
    @JsonProperty("sellingDepartmentId")
    private String sellingDepartmentId = null;
    @JsonProperty("additionalInfo")
    private String additionalInfo = null;
    @JsonProperty("token")
    private String token = null;
    @JsonProperty("client_request")
    private String client_request = null;

    public String getSendWalletId() {
        return sendWalletId;
    }

    public void setSendWalletId(String sendWalletId) {
        this.sendWalletId = sendWalletId;
    }

    public String getSendWalletAccountId() {
        return sendWalletAccountId;
    }

    public void setSendWalletAccountId(String sendWalletAccountId) {
        this.sendWalletAccountId = sendWalletAccountId;
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

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClient_request() {
        return client_request;
    }

    public void setClient_request(String client_request) {
        this.client_request = client_request;
    }
}
