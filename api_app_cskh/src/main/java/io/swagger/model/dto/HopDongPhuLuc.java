package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * HopDongPhuLuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class HopDongPhuLuc {
    @JsonProperty("maGD")
    private String maGD = null;
    @JsonProperty("tenKH")
    private String tenKH = null;
    @JsonProperty("hdkhId")
    private String hdkhId = null;
    @JsonProperty("diaChiKH")
    private String diaChiKH = null;
    @JsonProperty("soDienThoai")
    private String soDienThoai = null;
    @JsonProperty("tien")
    private String tien = null;
    @JsonProperty("vat")
    private String vat = null;
    @JsonProperty("tienHD")
    private String tienHD = null;
    @JsonProperty("ngayYc")
    private String ngayYc = null;
    @JsonProperty("tenNV")
    private String tenNV = null;
    @JsonProperty("kenhThuId")
    private String kenhThuId = null;
    @JsonProperty("htTraId")
    private String htTraId = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("phieuTTId")
    private String phieuTTId = null;
    @JsonProperty("nhanVienId")
    private String nhanVienId = null;
    @JsonProperty("trangThai")
    private String trangThai = null;
    @JsonProperty("trangThaiTT")
    private String trangThaiTT = null;
    @JsonProperty("kieu")
    private String kieu = null;
    @JsonProperty("kenhThu")
    private String kenhThu = null;
    @JsonProperty("htTra")
    private String htTra = null;
    private String ghichu = null;
    @JsonProperty("ghichu")

    public HopDongPhuLuc convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            maGD = rs.getString("MA_GD");
            tenKH = rs.getString("TEN_KH");
            hdkhId = rs.getString("HDKH_ID");
            diaChiKH = rs.getString("DIACHI_KH");
            soDienThoai = rs.getString("SO_DT");
            tien = rs.getString("TIEN");
            vat = rs.getString("VAT");
            tienHD = rs.getString("TIEN_HD");
            ngayYc = rs.getString("NGAY_YC");
            tenNV = rs.getString("TEN_NV");
            kenhThuId = rs.getString("KENHTHU_ID");
            htTraId = rs.getString("HT_TRA_ID");
            ngayCn = rs.getString("NGAY_CN");
            phieuTTId = rs.getString("PHIEUTT_ID");
            nhanVienId = rs.getString("NHANVIEN_ID");
            trangThai = rs.getString("TRANGTHAI");
            kieu = rs.getString("VKIEU");
            kenhThu = rs.getString("KENHTHU");
            htTra = rs.getString("HT_TRA");
            ghichu = rs.getString("GHICHU");
            if (trangThai != null && trangThai.trim().equals("1")) {
                trangThaiTT = "1";
            } else {
                if (kenhThu != null) {
                    if (trangThai != null && (trangThai.trim().equals("2") || trangThai.trim().equals("10"))) {
                        trangThaiTT = "0";
                    }
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public String getGhiChu() {
        return ghichu;
    }

    public void setGhiChu(String ghichu) {
        this.ghichu = ghichu;
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

    public String getHdkhId() {
        return hdkhId;
    }

    public void setHdkhId(String hdkhId) {
        this.hdkhId = hdkhId;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTien() {
        return tien;
    }

    public void setTien(String tien) {
        this.tien = tien;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTienHD() {
        return tienHD;
    }

    public void setTienHD(String tienHD) {
        this.tienHD = tienHD;
    }

    public String getNgayYc() {
        return ngayYc;
    }

    public void setNgayYc(String ngayYc) {
        this.ngayYc = ngayYc;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getKenhThuId() {
        return kenhThuId;
    }

    public void setKenhThuId(String kenhThuId) {
        this.kenhThuId = kenhThuId;
    }

    public String getHtTraId() {
        return htTraId;
    }

    public void setHtTraId(String htTraId) {
        this.htTraId = htTraId;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getPhieuTTId() {
        return phieuTTId;
    }

    public void setPhieuTTId(String phieuTTId) {
        this.phieuTTId = phieuTTId;
    }

    public String getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(String nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTrangThaiTT() {
        return trangThaiTT;
    }

    public void setTrangThaiTT(String trangThaiTT) {
        this.trangThaiTT = trangThaiTT;
    }

    public String getKieu() {
        return kieu;
    }

    public void setKieu(String kieu) {
        this.kieu = kieu;
    }

    public String getKenhThu() {
        return kenhThu;
    }

    public void setKenhThu(String kenhThu) {
        this.kenhThu = kenhThu;
    }

    public String getHtTra() {
        return htTra;
    }

    public void setHtTra(String htTra) {
        this.htTra = htTra;
    }
}
