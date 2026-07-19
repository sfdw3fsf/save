package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Thông tin wifi
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class Wifi {

    private String enable;

    private String ssid;

    private String pass;

    private String kieuXt;

    private String kieuMh;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getKieuXt() {
        return kieuXt;
    }

    public void setKieuXt(String kieuXt) {
        this.kieuXt = kieuXt;
    }

    public String getKieuMh() {
        return kieuMh;
    }

    public void setKieuMh(String kieuMh) {
        this.kieuMh = kieuMh;
    }
}
