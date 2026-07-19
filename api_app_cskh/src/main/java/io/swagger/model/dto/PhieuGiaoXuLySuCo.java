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
public class PhieuGiaoXuLySuCo extends BaseDTO {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("suCoId")
    private Long suCoId = null;
    @JsonProperty("suSoChaId")
    private Long suSoChaId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("phieuId")
    private Long phieuId = null;
    @JsonProperty("nguonScId")
    private Long nguonScId = null;
    @JsonProperty("nhomScId")
    private Long nhomScId = null;
    @JsonProperty("nhomSc")
    private String nhomSc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("dsThietBi")
    private String dsThietBi = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;
    @JsonProperty("henSuaTu")
    private String henSuaTu = null;
    @JsonProperty("henSuaDen")
    private String henSuaDen = null;
    @JsonProperty("trangThaiSc")
    private String trangThaiSc = null;
    @JsonProperty("canhBaoDt")
    private String canhBaoDt = null;
    @JsonProperty("mucDoId")
    private Long mucDoId = null;
    @JsonProperty("mucDoSc")
    private String mucDoSc = null;
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

    public Long getSuSoChaId() {
        return suSoChaId;
    }

    public void setSuSoChaId(Long suSoChaId) {
        this.suSoChaId = suSoChaId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
    }

    public Long getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Long nhomScId) {
        this.nhomScId = nhomScId;
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

    public String getDsThietBi() {
        return dsThietBi;
    }

    public void setDsThietBi(String dsThietBi) {
        this.dsThietBi = dsThietBi;
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

    public String getHenSuaTu() {
        return henSuaTu;
    }

    public void setHenSuaTu(String henSuaTu) {
        this.henSuaTu = henSuaTu;
    }

    public String getHenSuaDen() {
        return henSuaDen;
    }

    public void setHenSuaDen(String henSuaDen) {
        this.henSuaDen = henSuaDen;
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

    public Long getMucDoId() {
        return mucDoId;
    }

    public void setMucDoId(Long mucDoId) {
        this.mucDoId = mucDoId;
    }

    public String getMucDoSc() {
        return mucDoSc;
    }

    public void setMucDoSc(String mucDoSc) {
        this.mucDoSc = mucDoSc;
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
