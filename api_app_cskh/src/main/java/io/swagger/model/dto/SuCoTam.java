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
public class SuCoTam extends BaseDTO {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("phanVungId")
    private Long phanVungId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("nguonScId")
    private Long nguonScId = null;
    @JsonProperty("nhomScId")
    private Long nhomScId = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngayBh")
    private String ngayBh = null;
    @JsonProperty("donViBhId")
    private Long donViBhId = null;
    @JsonProperty("nhanVienBhId")
    private Long nhanVienBhId = null;
    @JsonProperty("dienThoaiLh")
    private String dienThoaiLh = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("donViTnId")
    private Long donViTnId = null;
    @JsonProperty("nhanVienTnId")
    private Long nhanVienTnId = null;
    @JsonProperty("henSuaTu")
    private String henSuaTu = null;
    @JsonProperty("henSuaDen")
    private String henSuaDen = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;
    @JsonProperty("mayCn")
    private String mayCn = null;
    @JsonProperty("ipCn")
    private String ipCn = null;
    @JsonProperty("moTa")
    private String moTa = null;
    @JsonProperty("donViBh")
    private String donViBh = null;
    @JsonProperty("donViTn")
    private String donViTn = null;
    @JsonProperty("nhanVienBh")
    private String nhanVienBh = null;
    @JsonProperty("nhanVienTn")
    private String nhanVienTn = null;
    @JsonProperty("nhomSc")
    private String nhomSc = null;
    @JsonProperty("nguoiBh")
    private String nguoiBh = null;
    @JsonProperty("nguoiTn")
    private String nguoiTn = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public Long getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(Long phanVungId) {
        this.phanVungId = phanVungId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
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

    public String getNgaySc() {
        return ngaySc;
    }

    public void setNgaySc(String ngaySc) {
        this.ngaySc = ngaySc;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getNgayBh() {
        return ngayBh;
    }

    public void setNgayBh(String ngayBh) {
        this.ngayBh = ngayBh;
    }

    public Long getDonViBhId() {
        return donViBhId;
    }

    public void setDonViBhId(Long donViBhId) {
        this.donViBhId = donViBhId;
    }

    public Long getNhanVienBhId() {
        return nhanVienBhId;
    }

    public void setNhanVienBhId(Long nhanVienBhId) {
        this.nhanVienBhId = nhanVienBhId;
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

    public Long getDonViTnId() {
        return donViTnId;
    }

    public void setDonViTnId(Long donViTnId) {
        this.donViTnId = donViTnId;
    }

    public Long getNhanVienTnId() {
        return nhanVienTnId;
    }

    public void setNhanVienTnId(Long nhanVienTnId) {
        this.nhanVienTnId = nhanVienTnId;
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

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
    }

    public String getIpCn() {
        return ipCn;
    }

    public void setIpCn(String ipCn) {
        this.ipCn = ipCn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getDonViBh() {
        return donViBh;
    }

    public void setDonViBh(String donViBh) {
        this.donViBh = donViBh;
    }

    public String getDonViTn() {
        return donViTn;
    }

    public void setDonViTn(String donViTn) {
        this.donViTn = donViTn;
    }

    public String getNhanVienBh() {
        return nhanVienBh;
    }

    public void setNhanVienBh(String nhanVienBh) {
        this.nhanVienBh = nhanVienBh;
    }

    public String getNhanVienTn() {
        return nhanVienTn;
    }

    public void setNhanVienTn(String nhanVienTn) {
        this.nhanVienTn = nhanVienTn;
    }

    public String getNhomSc() {
        return nhomSc;
    }

    public void setNhomSc(String nhomSc) {
        this.nhomSc = nhomSc;
    }

    public String getNguoiBh() {
        return nguoiBh;
    }

    public void setNguoiBh(String nguoiBh) {
        this.nguoiBh = nguoiBh;
    }

    public String getNguoiTn() {
        return nguoiTn;
    }

    public void setNguoiTn(String nguoiTn) {
        this.nguoiTn = nguoiTn;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
