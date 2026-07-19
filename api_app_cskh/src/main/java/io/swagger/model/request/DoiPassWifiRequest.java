package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;
import java.sql.Date;

/**
 * Đổi pass wifi
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DoiPassWifiRequest {

    @JsonProperty("account")
    private String account;

    @JsonProperty("tenWifi")
    private String tenWifi;

    @JsonProperty("pass")
    private String pass;

    @JsonProperty("kieuXtId")
    private Long kieuXtId;

    @JsonProperty("kieuMhId")
    private Long kieuMhId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTenWifi() {
        return tenWifi;
    }

    public void setTenWifi(String tenWifi) {
        this.tenWifi = tenWifi;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Long getKieuXtId() {
        return kieuXtId;
    }

    public void setKieuXtId(Long kieuXtId) {
        this.kieuXtId = kieuXtId;
    }

    public Long getKieuMhId() {
        return kieuMhId;
    }

    public void setKieuMhId(Long kieuMhId) {
        this.kieuMhId = kieuMhId;
    }
}
