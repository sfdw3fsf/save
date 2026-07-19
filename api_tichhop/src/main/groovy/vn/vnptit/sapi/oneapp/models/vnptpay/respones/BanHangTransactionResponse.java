package vn.vnptit.sapi.oneapp.models.vnptpay.respones;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * BaseResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")

public class BanHangTransactionResponse {
    @JsonProperty("error")
    private String error = "0";

    @JsonProperty("message")
    private String message = "";

    @JsonProperty("refTransId")
    private String refTransId = null;

    @JsonProperty("status")
    private String status = "";

    @JsonProperty("statusMessage")
    private String statusMessage = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRefTransId() {
        return refTransId;
    }

    public void setRefTransId(String refTransId) {
        this.refTransId = refTransId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BanHangTransactionResponse baseResponse = (BanHangTransactionResponse) o;
        return Objects.equals(this.error, baseResponse.error) &&
                Objects.equals(this.message, baseResponse.message) &&
                Objects.equals(this.refTransId, baseResponse.refTransId) &&
                Objects.equals(this.statusMessage, baseResponse.statusMessage) &&
                Objects.equals(this.status, baseResponse.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, message, refTransId, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseResponse {\n");
        sb.append("    error: ").append(toIndentedString(error)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
        sb.append("    messageDetail: ").append(toIndentedString(refTransId)).append("\n");
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
