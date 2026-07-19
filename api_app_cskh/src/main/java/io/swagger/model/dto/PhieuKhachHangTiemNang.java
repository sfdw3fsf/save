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
public class PhieuKhachHangTiemNang {
    @JsonProperty("phieuId")
    private Long phieuId = null;
    @JsonProperty("phieuChaId")
    private Long phieuChaId = null;
    @JsonProperty("hdkhId")
    private Long hdkhId = null;
    @JsonProperty("hdtbId")
    private Long hdtbId = null;
    @JsonProperty("khachHangId")
    private Long khachHangId = null;
    @JsonProperty("hdtbId1")
    private Long hdtbId1 = null;
    @JsonProperty("kieuLDId")
    private Long kieuLDId = null;
    @JsonProperty("donViNhanId")
    private Long donViNhanId = null;
    @JsonProperty("dichVuVTId")
    private Long dichVuVTId = null;
    @JsonProperty("loaiNCId")
    private Long loaiNCId = null;
    @JsonProperty("mucDoId")
    private Long mucDoId = null;
    @JsonProperty("nhanVienTBId")
    private Long nhanVienTBId = null;
    @JsonProperty("isCheck")
    private String isCheck = null;
    @JsonProperty("maGD")
    private String maGD = null;
    @JsonProperty("tenKH")
    private String tenKH = null;
    @JsonProperty("diaChiKH")
    private String diaChiKH = null;
    @JsonProperty("ngayYC")
    private String ngayYC = null;
    @JsonProperty("trangThaiHD")
    private String trangThaiHD = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("soDT")
    private String soDT = null;
    @JsonProperty("ttgv")
    private String ttgv = null;
    @JsonProperty("tenNV")
    private String tenNV = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("maKH")
    private String maKH = null;
    @JsonProperty("tenDVVT")
    private String tenDVVT = null;
    @JsonProperty("dvGiao")
    private String dvGiao = null;
    @JsonProperty("lyDoTonTC")
    private String lyDoTonTC = null;
    @JsonProperty("tenKieuLD")
    private String tenKieuLD = null;
    @JsonProperty("moTa")
    private String moTa = null;
    @JsonProperty("gioHenTu")
    private String gioHenTu = null;
    @JsonProperty("gioHenDen")
    private String gioHenDen = null;
    @JsonProperty("tenHoDan")
    private String tenHoDan = null;
    @JsonProperty("diaChiHoDan")
    private String diaChiHoDan = null;
    @JsonProperty("moTaHoDan")
    private String moTaHoDan = null;
    @JsonProperty("toaDo")
    private String toaDo = null;
    @JsonProperty("tinhTrang")
    private String tinhTrang = null;
    @JsonProperty("chiTieuTG")
    private String chiTieuTG = null;
    @JsonProperty("thoiGianCK")
    private String thoiGianCK = null;
    @JsonProperty("gioCL")
    private String gioCL = null;
    @JsonProperty("nhanVienTN")
    private String nhanVienTN = null;
    @JsonProperty("donViTN")
    private String donViTN = null;
    @JsonProperty("loaiNC")
    private String loaiNC = null;
    @JsonProperty("mucDo")
    private String mucDo = null;
    @JsonProperty("loaiHinhTB")
    private String loaiHinhTB = null;
    @JsonProperty("soThangCK")
    private String soThangCK = null;
    @JsonProperty("thangCK")
    private String thangCK = null;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(Long phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public Long getHdkhId() {
        return hdkhId;
    }

    public void setHdkhId(Long hdkhId) {
        this.hdkhId = hdkhId;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public Long getHdtbId1() {
        return hdtbId1;
    }

    public void setHdtbId1(Long hdtbId1) {
        this.hdtbId1 = hdtbId1;
    }

    public Long getKieuLDId() {
        return kieuLDId;
    }

    public void setKieuLDId(Long kieuLDId) {
        this.kieuLDId = kieuLDId;
    }

    public Long getDonViNhanId() {
        return donViNhanId;
    }

    public void setDonViNhanId(Long donViNhanId) {
        this.donViNhanId = donViNhanId;
    }

    public Long getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Long dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public Long getLoaiNCId() {
        return loaiNCId;
    }

    public void setLoaiNCId(Long loaiNCId) {
        this.loaiNCId = loaiNCId;
    }

    public Long getMucDoId() {
        return mucDoId;
    }

    public void setMucDoId(Long mucDoId) {
        this.mucDoId = mucDoId;
    }

    public Long getNhanVienTBId() {
        return nhanVienTBId;
    }

    public void setNhanVienTBId(Long nhanVienTBId) {
        this.nhanVienTBId = nhanVienTBId;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getMaGD() {
        return maGD;
    }

    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }

    public String getNgayYC() {
        return ngayYC;
    }

    public void setNgayYC(String ngayYC) {
        this.ngayYC = ngayYC;
    }

    public String getTrangThaiHD() {
        return trangThaiHD;
    }

    public void setTrangThaiHD(String trangThaiHD) {
        this.trangThaiHD = trangThaiHD;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getTtgv() {
        return ttgv;
    }

    public void setTtgv(String ttgv) {
        this.ttgv = ttgv;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenDVVT() {
        return tenDVVT;
    }

    public void setTenDVVT(String tenDVVT) {
        this.tenDVVT = tenDVVT;
    }

    public String getDvGiao() {
        return dvGiao;
    }

    public void setDvGiao(String dvGiao) {
        this.dvGiao = dvGiao;
    }

    public String getLyDoTonTC() {
        return lyDoTonTC;
    }

    public void setLyDoTonTC(String lyDoTonTC) {
        this.lyDoTonTC = lyDoTonTC;
    }

    public String getTenKieuLD() {
        return tenKieuLD;
    }

    public void setTenKieuLD(String tenKieuLD) {
        this.tenKieuLD = tenKieuLD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGioHenTu() {
        return gioHenTu;
    }

    public void setGioHenTu(String gioHenTu) {
        this.gioHenTu = gioHenTu;
    }

    public String getGioHenDen() {
        return gioHenDen;
    }

    public void setGioHenDen(String gioHenDen) {
        this.gioHenDen = gioHenDen;
    }

    public String getTenHoDan() {
        return tenHoDan;
    }

    public void setTenHoDan(String tenHoDan) {
        this.tenHoDan = tenHoDan;
    }

    public String getDiaChiHoDan() {
        return diaChiHoDan;
    }

    public void setDiaChiHoDan(String diaChiHoDan) {
        this.diaChiHoDan = diaChiHoDan;
    }

    public String getMoTaHoDan() {
        return moTaHoDan;
    }

    public void setMoTaHoDan(String moTaHoDan) {
        this.moTaHoDan = moTaHoDan;
    }

    public String getToaDo() {
        return toaDo;
    }

    public void setToaDo(String toaDo) {
        this.toaDo = toaDo;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getChiTieuTG() {
        return chiTieuTG;
    }

    public void setChiTieuTG(String chiTieuTG) {
        this.chiTieuTG = chiTieuTG;
    }

    public String getThoiGianCK() {
        return thoiGianCK;
    }

    public void setThoiGianCK(String thoiGianCK) {
        this.thoiGianCK = thoiGianCK;
    }

    public String getGioCL() {
        return gioCL;
    }

    public void setGioCL(String gioCL) {
        this.gioCL = gioCL;
    }

    public String getNhanVienTN() {
        return nhanVienTN;
    }

    public void setNhanVienTN(String nhanVienTN) {
        this.nhanVienTN = nhanVienTN;
    }

    public String getDonViTN() {
        return donViTN;
    }

    public void setDonViTN(String donViTN) {
        this.donViTN = donViTN;
    }

    public String getLoaiNC() {
        return loaiNC;
    }

    public void setLoaiNC(String loaiNC) {
        this.loaiNC = loaiNC;
    }

    public String getMucDo() {
        return mucDo;
    }

    public void setMucDo(String mucDo) {
        this.mucDo = mucDo;
    }

    public String getLoaiHinhTB() {
        return loaiHinhTB;
    }

    public void setLoaiHinhTB(String loaiHinhTB) {
        this.loaiHinhTB = loaiHinhTB;
    }

    public String getSoThangCK() {
        return soThangCK;
    }

    public void setSoThangCK(String soThangCK) {
        this.soThangCK = soThangCK;
    }

    public String getThangCK() {
        return thangCK;
    }

    public void setThangCK(String thangCK) {
        this.thangCK = thangCK;
    }
}
