package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CancelTransactionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class CancelTransactionRequest {
    @JsonProperty("cancelType")
    private String cancelType = null;
    @JsonProperty("amount")
    private String amount = null;
    @JsonProperty("description")
    private String description = null;
    @JsonProperty("refTransId")
    private String refTransId = null;

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefTransId() {
        return refTransId;
    }

    public void setRefTransId(String refTransId) {
        this.refTransId = refTransId;
    }
}
