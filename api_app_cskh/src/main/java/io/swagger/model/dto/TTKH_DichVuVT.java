package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TTKH_DichVuVT
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TTKH_DichVuVT extends BaseDTO {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("ma")
    private String ma = null;
    @JsonProperty("ten")
    private String ten = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("suDung")
    private String suDung = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getSuDung() {
        return suDung;
    }

    public void setSuDung(String suDung) {
        this.suDung = suDung;
    }
}
