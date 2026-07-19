package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Tiếp nhận sự cố
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TiepNhanSuCo {

    @JsonProperty("chon")
    private String chon;

    @JsonProperty("phanVungId")
    private Long phanVungId;

    @JsonProperty("maSc")
    private String maSc;

    @JsonProperty("nguonScId")
    private Long nguonScId;

    @JsonProperty("nhomScId")
    private Long nhomScId;

    @JsonProperty("ngaySc")
    private String ngaySc;

    @JsonProperty("noiDungPhanAnh")
    private String noiDungPhanAnh;
    @JsonProperty("noiDungSc")
    private String noiDungSc;

    @JsonProperty("ngayBh")
    private String ngayBh;

    @JsonProperty("donViBhId")
    private Long donViBhId;

    @JsonProperty("nhanVienBhId")
    private Long nhanVienBhId;

    @JsonProperty("dienThoaiLh")
    private String dienThoaiLh;

    @JsonProperty("ngayTn")
    private String ngayTn;

    @JsonProperty("donViTnId")
    private Long donViTnId;

    @JsonProperty("nhanVienTnId")
    private Long nhanVienTnId;

    @JsonProperty("henSuaTu")
    private String henSuaTu;

    @JsonProperty("henSuaDen")
    private String henSuaDen;

    @JsonProperty("ngayCn")
    private String ngayCn;

    @JsonProperty("nguoiCn")
    private String nguoiCn;

    @JsonProperty("mayCn")
    private String mayCn;

    @JsonProperty("ipCn")
    private String ipCn;

    @JsonProperty("moTa")
    private String moTa;

    @JsonProperty("donViBh")
    private String donViBh;

    @JsonProperty("donViTn")
    private String donViTn;

    @JsonProperty("nhanVienBh")
    private String nhanVienBh;

    @JsonProperty("nhanVienTn")
    private String nhanVienTn;

    @JsonProperty("nhomSc")
    private String nhomSc;

    @JsonProperty("nguoiBh")
    private String nguoiBh;

    @JsonProperty("nguoiTn")
    private String nguoiTn;

    @JsonProperty("thoiGian")
    private String thoiGian;
    @JsonProperty("batDau")
    private String batDau;
    @JsonProperty("ketThuc")
    private String ketThuc;
    @JsonProperty("phanTuLoi")
    private String phanTuLoi;
    @JsonProperty("idPhanTuLoi")
    private Long idPhanTuLoi;
    @JsonProperty("flagGiaoViec")
    private String flagGiaoViec;
    @JsonProperty("dsIdPhanTuLoi")
    private String dsIdPhanTuLoi;


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

    public String getPhanTuLoi() {
        return phanTuLoi;
    }

    public void setPhanTuLoi(String phanTuLoi) {
        this.phanTuLoi = phanTuLoi;
    }

    public String getNoiDungPhanAnh() {
        return noiDungPhanAnh;
    }

    public void setNoiDungPhanAnh(String noiDungPhanAnh) {
        this.noiDungPhanAnh = noiDungPhanAnh;
    }

    public String getBatDau() {
        return batDau;
    }

    public void setBatDau(String batDau) {
        this.batDau = batDau;
    }

    public String getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(String ketThuc) {
        this.ketThuc = ketThuc;
    }

    public String getFlagGiaoViec() {
        return flagGiaoViec;
    }

    public void setFlagGiaoViec(String flagGiaoViec) {
        this.flagGiaoViec = flagGiaoViec;
    }
    public Long getIdPhanTuLoi() {
        return idPhanTuLoi;
    }

    public void setIdPhanTuLoi(Long idPhanTuLoi) {
        this.idPhanTuLoi = idPhanTuLoi;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getDsIdPhanTuLoi() {
        return dsIdPhanTuLoi;
    }

    public void setDsIdPhanTuLoi(String dsIdPhanTuLoi) {
        this.dsIdPhanTuLoi = dsIdPhanTuLoi;
    }
}
