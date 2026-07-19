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
public class TienTrinhSuCo extends BaseDTO {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("sucoId")
    private Long sucoId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("nhomSc")
    private String nhomSc = null;
    @JsonProperty("nguonSc")
    private String nguonSc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("ngayTb")
    private String ngayTb = null;
    @JsonProperty("ngayNt")
    private String ngayNt = null;
    @JsonProperty("nhanVienTb")
    private String nhanVienTb = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("nhanVienTn")
    private String nhanVienTn = null;
    @JsonProperty("nhanVienNt")
    private String nhanVienNt = null;
    @JsonProperty("ghiChuXl")
    private String ghiChuXl = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;
    @JsonProperty("mucDoSc")
    private String mucDoSc = null;
    @JsonProperty("trangThaiSc")
    private String trangThaiSc = null;
    @JsonProperty("ghiChuHong")
    private String ghiChuHong = null;
    @JsonProperty("ttscId")
    private Long ttscId = null;
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

    public Long getSucoId() {
        return sucoId;
    }

    public void setSucoId(Long sucoId) {
        this.sucoId = sucoId;
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

    public String getNgayTb() {
        return ngayTb;
    }

    public void setNgayTb(String ngayTb) {
        this.ngayTb = ngayTb;
    }

    public String getNgayNt() {
        return ngayNt;
    }

    public void setNgayNt(String ngayNt) {
        this.ngayNt = ngayNt;
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

    public String getNhanVienNt() {
        return nhanVienNt;
    }

    public void setNhanVienNt(String nhanVienNt) {
        this.nhanVienNt = nhanVienNt;
    }

    public String getGhiChuXl() {
        return ghiChuXl;
    }

    public void setGhiChuXl(String ghiChuXl) {
        this.ghiChuXl = ghiChuXl;
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

    public String getTrangThaiSc() {
        return trangThaiSc;
    }

    public void setTrangThaiSc(String trangThaiSc) {
        this.trangThaiSc = trangThaiSc;
    }

    public String getGhiChuHong() {
        return ghiChuHong;
    }

    public void setGhiChuHong(String ghiChuHong) {
        this.ghiChuHong = ghiChuHong;
    }

    public Long getTtscId() {
        return ttscId;
    }

    public void setTtscId(Long ttscId) {
        this.ttscId = ttscId;
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
