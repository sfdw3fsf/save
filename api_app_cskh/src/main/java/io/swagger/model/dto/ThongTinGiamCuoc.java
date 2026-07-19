package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThongTinGiamCuoc extends BaseDTO {
    @JsonProperty("thangGC")
    private String thangGC = null;
    @JsonProperty("khieuNaiId")
    private Long khieuNaiId = null;
    @JsonProperty("thangGoc")
    private String thangGoc = null;
    @JsonProperty("thangTD")
    private String thangTD = null;
    @JsonProperty("thangSTD")
    private String thangSTD = null;
    @JsonProperty("tien")
    private String tien = null;
    @JsonProperty("tienGC")
    private String tienGC = null;
    @JsonProperty("vatGC")
    private String vatGC = null;
    @JsonProperty("kyGC")
    private String kyGC = null;
    @JsonProperty("vatPT")
    private String vatPT = null;
    @JsonProperty("ISUPDATE")
    private String ISUPDATE = null;

    public String getThangGC() {
        return thangGC;
    }

    public void setThangGC(String thangGC) {
        this.thangGC = thangGC;
    }

    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public String getThangGoc() {
        return thangGoc;
    }

    public void setThangGoc(String thangGoc) {
        this.thangGoc = thangGoc;
    }

    public String getThangTD() {
        return thangTD;
    }

    public void setThangTD(String thangTD) {
        this.thangTD = thangTD;
    }

    public String getThangSTD() {
        return thangSTD;
    }

    public void setThangSTD(String thangSTD) {
        this.thangSTD = thangSTD;
    }

    public String getTien() {
        return tien;
    }

    public void setTien(String tien) {
        this.tien = tien;
    }

    public String getTienGC() {
        return tienGC;
    }

    public void setTienGC(String tienGC) {
        this.tienGC = tienGC;
    }

    public String getVatGC() {
        return vatGC;
    }

    public void setVatGC(String vatGC) {
        this.vatGC = vatGC;
    }

    public String getKyGC() {
        return kyGC;
    }

    public void setKyGC(String kyGC) {
        this.kyGC = kyGC;
    }

    public String getVatPT() {
        return vatPT;
    }

    public void setVatPT(String vatPT) {
        this.vatPT = vatPT;
    }

    public String getISUPDATE() {
        return ISUPDATE;
    }

    public void setISUPDATE(String ISUPDATE) {
        this.ISUPDATE = ISUPDATE;
    }
}
