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
public class SuCoCPN extends BaseDTO {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("suCoId")
    private String suCoId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("nhomSc")
    private String nhomSc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("ngayTb")
    private String ngayTb = null;
    @JsonProperty("nhanVienTb")
    private String nhanVienTb = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("nhanVienTn")
    private String nhanVienTn = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;
    @JsonProperty("mucDoSc")
    private String mucDoSc = null;
    @JsonProperty("quytrinh")
    private String quytrinh = null;
    @JsonProperty("quytrinh_id")
    private Integer quytrinh_id = null;
    @JsonProperty("ngayBd")
    private String ngayBd = null;
    @JsonProperty("ngayKt")
    private String ngayKt = null;

    public Integer getQuytrinh_id() {
        return quytrinh_id;
    }

    public void setQuytrinh_id(String Integer) {
        this.quytrinh_id = quytrinh_id;
    }

    public String getQuytrinh() {
        return quytrinh;
    }

    public void setQuytrinh(String quytrinh) {
        this.quytrinh = quytrinh;
    }

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public String getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(String suCoId) {
        this.suCoId = suCoId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getNhomSc() {
        return nhomSc;
    }

    public void setNhomSc(String nhomSc) {
        this.nhomSc = nhomSc;
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

    public String getNgayTb() {
        return ngayTb;
    }

    public void setNgayTb(String ngayTb) {
        this.ngayTb = ngayTb;
    }

    public String getNhanVienTb() {
        return nhanVienTb;
    }

    public void setNhanVienTb(String nhanVienTb) {
        this.nhanVienTb = nhanVienTb;
    }

    public String getNgayTn() {
        return ngayTn;
    }

    public void setNgayTn(String ngayTn) {
        this.ngayTn = ngayTn;
    }

    public String getNhanVienTn() {
        return nhanVienTn;
    }

    public void setNhanVienTn(String nhanVienTn) {
        this.nhanVienTn = nhanVienTn;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getMucDoSc() {
        return mucDoSc;
    }

    public void setMucDoSc(String mucDoSc) {
        this.mucDoSc = mucDoSc;
    }

    public void setQuytrinh_id(Integer quytrinh_id) {
        this.quytrinh_id = quytrinh_id;
    }

    public String getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(String ngayBd) {
        this.ngayBd = ngayBd;
    }

    public String getNgayKt() {
        return ngayKt;
    }

    public void setNgayKt(String ngayKt) {
        this.ngayKt = ngayKt;
    }
}
