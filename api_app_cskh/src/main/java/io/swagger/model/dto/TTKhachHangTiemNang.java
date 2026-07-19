package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * File/Image
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TTKhachHangTiemNang {
    @JsonProperty("loaiTBId")
    private Long loaiTBId = null;
    @JsonProperty("dichVuVTId")
    private Long dichVuVTId = null;
    @JsonProperty("hdkhId")
    private Long hdkhId = null;
    @JsonProperty("thangHetHan")
    private String thangHetHan = null;
    @JsonProperty("tenKH")
    private String tenKH = null;
    @JsonProperty("loaiHinhTB")
    private String loaiHinhTB = null;
    @JsonProperty("soDT")
    private String soDT = null;
    @JsonProperty("diaChiKH")
    private String diaChiKH = null;
    @JsonProperty("kqxlId")
    private Long kqxlId = null;
    @JsonProperty("kqxl")
    private String kqxl = null;

    public String getKqxl() {
        return kqxl;
    }

    public void setKqxl(String kqxl) {
        this.kqxl = kqxl;
    }

    public Long getKqxlId() {
        return kqxlId;
    }

    public void setKqxlId(Long kqxlId) {
        this.kqxlId = kqxlId;
    }

    public Long getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(Long loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public Long getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Long dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public Long getHdkhId() {
        return hdkhId;
    }

    public void setHdkhId(Long hdkhId) {
        this.hdkhId = hdkhId;
    }

    public String getThangHetHan() {
        return thangHetHan;
    }

    public void setThangHetHan(String thangHetHan) {
        this.thangHetHan = thangHetHan;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getLoaiHinhTB() {
        return loaiHinhTB;
    }

    public void setLoaiHinhTB(String loaiHinhTB) {
        this.loaiHinhTB = loaiHinhTB;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }
}
