package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * ModifyPasswordVNNRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ModifyPasswordVNNRequest {
    @JsonProperty("accountName")
    private String accountName;
    @JsonProperty("accountPassword")
    private String accountPassword;
    @JsonProperty("password")
    private String password;
    @JsonProperty("userId")
    private String userId;

    public ModifyPasswordVNNRequest() {
    }

    public ModifyPasswordVNNRequest(String accountName, String accountPassword, String password, String userId) {
        this.accountName = accountName;
        this.password = password;
        this.userId = userId;
        this.accountPassword = accountPassword;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
