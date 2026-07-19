package vn.vnptit.sapi.oneapp.models.vnptpay.respones;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * BaseResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")

public class InquirePartnerResponse {
    @JsonProperty("error")
    private String error = "0";

    @JsonProperty("message")
    private String message = "";

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("transRequestId")
    private String transRequestId = null;

    @JsonProperty("transResponseId")
    private String transResponseId = null;

    @JsonProperty("options")
    private String options = null;

    @JsonProperty("serviceId")
    private String serviceId = null;

    @JsonProperty("paymentCode")
    private String paymentCode = null;

    @JsonProperty("billAmount")
    private String billAmount = null;

    @JsonProperty("secureCode")
    private String secureCode = null;

    @JsonProperty("status")
    private String status = "";

    @JsonProperty("statusMessage")
    private String statusMessage = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransRequestId() {
        return transRequestId;
    }

    public void setTransRequestId(String transRequestId) {
        this.transRequestId = transRequestId;
    }

    public String getTransResponseId() {
        return transResponseId;
    }

    public void setTransResponseId(String transResponseId) {
        this.transResponseId = transResponseId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
