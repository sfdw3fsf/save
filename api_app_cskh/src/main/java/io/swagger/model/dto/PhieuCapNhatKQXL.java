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
public class PhieuCapNhatKQXL extends BaseDTO {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("sucoId")
    private Long sucoId = null;
    @JsonProperty("sucoChaId")
    private Long sucoChaId = null;
    @JsonProperty("phieuId")
    private Long phieuId = null;
    @JsonProperty("nhomScId")
    private Long nhomScId = null;
    @JsonProperty("mucDoId")
    private Long mucDoId = null;
    @JsonProperty("nguonScId")
    private Long nguonScId = null;
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
    @JsonProperty("trangThaiPh")
    private String trangThaiPh = null;
    @JsonProperty("noiDungGiao")
    private String noiDungGiao = null;
    @JsonProperty("donViGiao")
    private String donViGiao = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("donViNhan")
    private String donViNhan = null;
    @JsonProperty("mucDoKc")
    private String mucDoKc = null;
    @JsonProperty("chiTieuTg")
    private String chiTieuTg = null;
    @JsonProperty("conLai")
    private String conLai = null;
    @JsonProperty("ghiChuXl")
    private String ghiChuXl = null;
    @JsonProperty("canhBaoDt")
    private String canhBaoDt = null;
    @JsonProperty("mauNen")
    private String mauNen = null;
    @JsonProperty("mauChu")
    private String mauChu = null;
    @JsonProperty("moTa")
    private String moTa = null;

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

    public Long getSucoChaId() {
        return sucoChaId;
    }

    public void setSucoChaId(Long sucoChaId) {
        this.sucoChaId = sucoChaId;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Long nhomScId) {
        this.nhomScId = nhomScId;
    }

    public Long getMucDoId() {
        return mucDoId;
    }

    public void setMucDoId(Long mucDoId) {
        this.mucDoId = mucDoId;
    }

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
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

    public String getTrangThaiPh() {
        return trangThaiPh;
    }

    public void setTrangThaiPh(String trangThaiPh) {
        this.trangThaiPh = trangThaiPh;
    }

    public String getNoiDungGiao() {
        return noiDungGiao;
    }

    public void setNoiDungGiao(String noiDungGiao) {
        this.noiDungGiao = noiDungGiao;
    }

    public String getDonViGiao() {
        return donViGiao;
    }

    public void setDonViGiao(String donViGiao) {
        this.donViGiao = donViGiao;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getDonViNhan() {
        return donViNhan;
    }

    public void setDonViNhan(String donViNhan) {
        this.donViNhan = donViNhan;
    }

    public String getMucDoKc() {
        return mucDoKc;
    }

    public void setMucDoKc(String mucDoKc) {
        this.mucDoKc = mucDoKc;
    }

    public String getChiTieuTg() {
        return chiTieuTg;
    }

    public void setChiTieuTg(String chiTieuTg) {
        this.chiTieuTg = chiTieuTg;
    }

    public String getConLai() {
        return conLai;
    }

    public void setConLai(String conLai) {
        this.conLai = conLai;
    }

    public String getGhiChuXl() {
        return ghiChuXl;
    }

    public void setGhiChuXl(String ghiChuXl) {
        this.ghiChuXl = ghiChuXl;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
