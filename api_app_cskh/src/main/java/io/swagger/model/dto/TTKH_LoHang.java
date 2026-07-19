package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TTKH_LoHang
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TTKH_LoHang extends BaseDTO {
    @JsonProperty("loHang")
    private String loHang = null;
    @JsonProperty("lh")
    private String lh = null;
    @JsonProperty("donGia")
    private String donGia = null;

    public String getLoHang() {
        return loHang;
    }

    public void setLoHang(String loHang) {
        this.loHang = loHang;
    }

    public String getLh() {
        return lh;
    }

    public void setLh(String lh) {
        this.lh = lh;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }
}
