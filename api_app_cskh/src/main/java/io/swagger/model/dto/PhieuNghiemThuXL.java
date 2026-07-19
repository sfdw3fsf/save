package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PhieuNghiemThuXL extends BaseDTO {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("suCoId")
    private Long suCoId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("mucDoSc")
    private String mucDoSc = null;
    @JsonProperty("nhomSc")
    private String nhomSc = null;
    @JsonProperty("nguonSc")
    private String nguonSc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;
    @JsonProperty("trangThaiSc")
    private String trangThaiSc = null;
    @JsonProperty("canhBaoDt")
    private String canhBaoDt = null;
    @JsonProperty("mauNen")
    private String mauNen = null;
    @JsonProperty("mauChu")
    private String mauChu = null;

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getMucDoSc() {
        return mucDoSc;
    }

    public void setMucDoSc(String mucDoSc) {
        this.mucDoSc = mucDoSc;
    }

    public String getNhomSc() {
        return nhomSc;
    }

    public void setNhomSc(String nhomSc) {
        this.nhomSc = nhomSc;
    }

    public String getNguonSc() {
        return nguonSc;
    }

    public void setNguonSc(String nguonSc) {
        this.nguonSc = nguonSc;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getNgaySc() {
        return ngaySc;
    }

    public void setNgaySc(String ngaySc) {
        this.ngaySc = ngaySc;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThaiSc() {
        return trangThaiSc;
    }

    public void setTrangThaiSc(String trangThaiSc) {
        this.trangThaiSc = trangThaiSc;
    }

    public String getCanhBaoDt() {
        return canhBaoDt;
    }

    public void setCanhBaoDt(String canhBaoDt) {
        this.canhBaoDt = canhBaoDt;
    }

    public String getMauNen() {
        return mauNen;
    }

    public void setMauNen(String mauNen) {
        this.mauNen = mauNen;
    }

    public String getMauChu() {
        return mauChu;
    }

    public void setMauChu(String mauChu) {
        this.mauChu = mauChu;
    }
}
