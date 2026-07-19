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
public class SuCoDPN extends BaseDTO {
    @JsonProperty("suCoId")
    private Long suCoId = null;
    @JsonProperty("suCoChaId")
    private Long suCoChaId = null;
    @JsonProperty("phieuId")
    private Long phieuId = null;
    @JsonProperty("maSc")
    private String maSc = null;
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
    @JsonProperty("ngayTb")
    private String ngayTb = null;
    @JsonProperty("nhanVienTbId")
    private Long nhanVienTbId = null;
    @JsonProperty("nhanVienTb")
    private String nhanVienTb = null;
    @JsonProperty("dienThoaiLh")
    private String dienThoaiLh = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("nhanVienTnId")
    private Long nhanVienTnId = null;
    @JsonProperty("nhanVienTn")
    private String nhanVienTn = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;
    @JsonProperty("henSuaTu")
    private String henSuaTu = null;
    @JsonProperty("henSuaDen")
    private String henSuaDen = null;
    @JsonProperty("mucDoId")
    private Long mucDoId = null;
    @JsonProperty("mauNen")
    private String mauNen = null;
    @JsonProperty("mauChu")
    private String mauChu = null;
    @JsonProperty("mucDoSc")
    private String mucDoSc = null;
    @JsonProperty("khoaPhieu")
    private String khoaPhieu = null;
    @JsonProperty("moTa")
    private String moTa = null;
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

    public void setQuytrinh_id(Integer quytrinh_id) {
        this.quytrinh_id = quytrinh_id;
    } 

    public String getQuytrinh() {
        return quytrinh;
    }

    public void setQuytrinh(String quytrinh) {
        this.quytrinh = quytrinh;
    }

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public Long getSuCoChaId() {
        return suCoChaId;
    }

    public void setSuCoChaId(Long suCoChaId) {
        this.suCoChaId = suCoChaId;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
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

    public String getNgayTb() {
        return ngayTb;
    }

    public void setNgayTb(String ngayTb) {
        this.ngayTb = ngayTb;
    }

    public Long getNhanVienTbId() {
        return nhanVienTbId;
    }

    public void setNhanVienTbId(Long nhanVienTbId) {
        this.nhanVienTbId = nhanVienTbId;
    }

    public String getNhanVienTb() {
        return nhanVienTb;
    }

    public void setNhanVienTb(String nhanVienTb) {
        this.nhanVienTb = nhanVienTb;
    }

    public String getDienThoaiLh() {
        return dienThoaiLh;
    }

    public void setDienThoaiLh(String dienThoaiLh) {
        this.dienThoaiLh = dienThoaiLh;
    }

    public String getNgayTn() {
        return ngayTn;
    }

    public void setNgayTn(String ngayTn) {
        this.ngayTn = ngayTn;
    }

    public Long getNhanVienTnId() {
        return nhanVienTnId;
    }

    public void setNhanVienTnId(Long nhanVienTnId) {
        this.nhanVienTnId = nhanVienTnId;
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

    public Long getMucDoId() {
        return mucDoId;
    }

    public void setMucDoId(Long mucDoId) {
        this.mucDoId = mucDoId;
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

    public String getMucDoSc() {
        return mucDoSc;
    }

    public void setMucDoSc(String mucDoSc) {
        this.mucDoSc = mucDoSc;
    }

    public String getKhoaPhieu() {
        return khoaPhieu;
    }

    public void setKhoaPhieu(String khoaPhieu) {
        this.khoaPhieu = khoaPhieu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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
