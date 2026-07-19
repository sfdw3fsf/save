package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Danh bạ thanh toán
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhBaThueBao extends DanhBaBase {

    @JsonProperty("thueBaoId")
    private Long thueBaoId;

    @JsonProperty("maTb")
    private String maTb;

    @JsonProperty("tenTb")
    private String tenTb;

    @JsonProperty("diaChiTb")
    private String diaChiTb;

    @JsonProperty("cuocTb")
    private Double cuocTb;

    @JsonProperty("cuocDv")
    private Double cuocDv;

    @JsonProperty("ngaySd")
    private String ngaySd;

    @JsonProperty("ngayCat")
    private String ngayCat;

    @JsonProperty("ngayTd")
    private String ngayTd;

    @JsonProperty("ngayKp")
    private String ngayKp;

    @JsonProperty("thanhToanId")
    private Long thanhToanId;

    @JsonProperty("loaiTbId")
    private Long loaiTbId;

    @JsonProperty("thueBaoChaId")
    private Long thueBaoChaId;

    @JsonProperty("trangThaiTbId")
    private Long trangThaiTbId;

    @JsonProperty("doiTuongId")
    private Long doiTuongId;

    @JsonProperty("khachHangId")
    private Long khachHangId;

    @JsonProperty("dichVuVtId")
    private Long dichVuVtId;

    @JsonProperty("ngayDm")
    private String ngayDm;

    @JsonProperty("donViId")
    private Long donViId;

    @JsonProperty("kieuLdId")
    private Long kieuLdId;

    @JsonProperty("syncStatus")
    private Long syncStatus;

    @JsonProperty("diaChiLd")
    private String diaChiLd;

    @JsonProperty("ngaySn")
    private String ngaySn;

    @JsonProperty("kieuTb")
    private Long kieuTb;

    @JsonProperty("donViDauId")
    private Long donViDauId;

    @JsonProperty("donViCuoiId")
    private Long donViCuoiId;

    @JsonProperty("maGia")
    private Long maGia;

    @JsonProperty("muCuocTbId")
    private Long muCuocTbId;

    @JsonProperty("muCuocTb")
    private String muCuocTb;

    @JsonProperty("ctvId")
    private Long ctvId;

    @JsonProperty("congIch")
    private String congIch;

    @JsonProperty("thueBaoCuId")
    private Long thueBaoCuId;

    @JsonProperty("maCuoc")
    private String maCuoc;

    @JsonProperty("soPlhd")
    private String soPlhd;

    @JsonProperty("maTbMoi")
    private String maTbMoi;

    @JsonProperty("soPl")
    private String soPl;

    @JsonProperty("maGd")
    private String maGd;

    @JsonProperty("chuyenMang")
    private String chuyenMang;

    @JsonProperty("tgThueTu")
    private String tgThueTu;

    @JsonProperty("tgThueDen")
    private String tgThueDen;

    @JsonProperty("goiKtId")
    private Long goiKtId;

    @JsonProperty("ngayCdlh")
    private String ngayCdlh;

    @JsonProperty("soIpSuDung")
    private String soIpSuDung;

    @JsonProperty("maDhvip")
    private String maDhvip;

    @JsonProperty("soThangCk")
    private Long soThangCk;

    @JsonProperty("ngayKtck")
    private String ngayKtck;

    @JsonProperty("ngayHhTd")
    private String ngayHhTd;

    @JsonProperty("nhaccCuId")
    private String nhaccCuId;

    @JsonProperty("tenDichVu")
    private String tenDichVu;

    @JsonProperty("loaiTb")
    private String loaiTb;

    public DanhBaThueBao convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            stt = rs.getLong("STT");
            thueBaoId = rs.getLong("THUEBAO_ID");
            maTb = rs.getString("MA_TB");
            tenTb = rs.getString("TEN_TB");
            diaChiTb = rs.getString("DIACHI_TB");
            cuocTb = rs.getDouble("CUOC_TB");
            cuocDv = rs.getDouble("CUOC_DV");
            ngaySd = rs.getString("NGAY_SD");
            ngayCat = rs.getString("NGAY_CAT");
            ngayTd = rs.getString("NGAY_TD");
            ngayKp = rs.getString("NGAY_KP");
            thanhToanId = rs.getLong("THANHTOAN_ID");
            loaiTbId = rs.getLong("LOAITB_ID");
            thueBaoChaId = rs.getLong("THUEBAO_CHA_ID");
            trangThaiTbId = rs.getLong("TRANGTHAITB_ID");
            doiTuongId = rs.getLong("DOITUONG_ID");
            khachHangId = rs.getLong("KHACHHANG_ID");
            dichVuVtId = rs.getLong("DICHVUVT_ID");
            ngayDm = rs.getString("NGAY_DM");
            donViId = rs.getLong("DONVI_ID");
            kieuLdId = rs.getLong("KIEULD_ID");
            syncStatus = rs.getLong("SYNC_STATUS");
            ketQuaCh = rs.getString("KETQUA_CH");
            diaChiLd = rs.getString("DIACHI_LD");
            ngaySn = rs.getString("NGAY_SN");
            kieuTb = rs.getLong("KIEU_TB");
            donViDauId = rs.getLong("DONVI_DAU_ID");
            donViCuoiId = rs.getLong("DONVI_CUOI_ID");
            email = rs.getString("EMAIL");
            maGia = rs.getLong("MAGIA");
            muCuocTbId = rs.getLong("MUCUOCTB_ID");
            muCuocTb = rs.getString("MUCUOC_TB");
            ghiChu = rs.getString("GHICHU");
            mayCn = rs.getString("MAY_CN");
            ngayCn = rs.getString("NGAY_CN");
            nguoiCn = rs.getString("NGUOI_CN");
            ctvId = rs.getLong("CTV_ID");
            congIch = rs.getString("CONGICH");
            thueBaoCuId = rs.getLong("THUEBAOCU_ID");
            maCuoc = rs.getString("MACUOC");
            soPlhd = rs.getString("SO_PLHD");
            maTbMoi = rs.getString("MA_TB_MOI");
            soPl = rs.getString("SO_PL");
            maGd = rs.getString("MA_GD");
            chuyenMang = rs.getString("CHUYENMANG");
            tgThueDen = rs.getString("TG_THUE_DEN");
            tgThueTu = rs.getString("TG_THUE_TU");
            goiKtId = rs.getLong("GOIKT_ID");
            ngayCdlh = rs.getString("NGAY_CDLH");
            soIpSuDung = rs.getString("SO_IP_SUDUNG");
            maDhvip = rs.getString("MA_DHVIP");
            soThangCk = rs.getLong("SOTHANG_CK");
            ngayKtck = rs.getString("NGAY_KTCK");
            ngayHhTd = rs.getString("NGAY_HH_TD");
            nhaccCuId = rs.getString("NHACC_CU_ID");
            tinhId = rs.getLong("TINHID");
            quanId = rs.getLong("QUANID");
            phuongId = rs.getLong("PHUONGID");
            phoId = rs.getLong("PHOID");
            apId = rs.getLong("APID");
            khuId = rs.getLong("KHUID");
            dacDiemId = rs.getLong("DACDIEMID");
            diaChi = rs.getString("DIACHI");
            soNha = rs.getString("SONHA");
            tenDichVu = rs.getString("TENDICHVU");
            loaiTb = rs.getString("LOAITB");
            datt = rs.getLong("DATT");
            return this;
        } else {
            return null;
        }
    }

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public String getDiaChiTb() {
        return diaChiTb;
    }

    public void setDiaChiTb(String diaChiTb) {
        this.diaChiTb = diaChiTb;
    }

    public Double getCuocTb() {
        return cuocTb;
    }

    public void setCuocTb(Double cuocTb) {
        this.cuocTb = cuocTb;
    }

    public Double getCuocDv() {
        return cuocDv;
    }

    public void setCuocDv(Double cuocDv) {
        this.cuocDv = cuocDv;
    }

    public String getNgaySd() {
        return ngaySd;
    }

    public void setNgaySd(String ngaySd) {
        this.ngaySd = ngaySd;
    }

    public String getNgayCat() {
        return ngayCat;
    }

    public void setNgayCat(String ngayCat) {
        this.ngayCat = ngayCat;
    }

    public String getNgayTd() {
        return ngayTd;
    }

    public void setNgayTd(String ngayTd) {
        this.ngayTd = ngayTd;
    }

    public String getNgayKp() {
        return ngayKp;
    }

    public void setNgayKp(String ngayKp) {
        this.ngayKp = ngayKp;
    }

    public Long getThanhToanId() {
        return thanhToanId;
    }

    public void setThanhToanId(Long thanhToanId) {
        this.thanhToanId = thanhToanId;
    }

    public Long getLoaiTbId() {
        return loaiTbId;
    }

    public void setLoaiTbId(Long loaiTbId) {
        this.loaiTbId = loaiTbId;
    }

    public Long getThueBaoChaId() {
        return thueBaoChaId;
    }

    public void setThueBaoChaId(Long thueBaoChaId) {
        this.thueBaoChaId = thueBaoChaId;
    }

    public Long getTrangThaiTbId() {
        return trangThaiTbId;
    }

    public void setTrangThaiTbId(Long trangThaiTbId) {
        this.trangThaiTbId = trangThaiTbId;
    }

    public Long getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Long doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public Long getDichVuVtId() {
        return dichVuVtId;
    }

    public void setDichVuVtId(Long dichVuVtId) {
        this.dichVuVtId = dichVuVtId;
    }

    public String getNgayDm() {
        return ngayDm;
    }

    public void setNgayDm(String ngayDm) {
        this.ngayDm = ngayDm;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getKieuLdId() {
        return kieuLdId;
    }

    public void setKieuLdId(Long kieuLdId) {
        this.kieuLdId = kieuLdId;
    }

    public Long getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Long syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getDiaChiLd() {
        return diaChiLd;
    }

    public void setDiaChiLd(String diaChiLd) {
        this.diaChiLd = diaChiLd;
    }

    public String getNgaySn() {
        return ngaySn;
    }

    public void setNgaySn(String ngaySn) {
        this.ngaySn = ngaySn;
    }

    public Long getKieuTb() {
        return kieuTb;
    }

    public void setKieuTb(Long kieuTb) {
        this.kieuTb = kieuTb;
    }

    public Long getDonViDauId() {
        return donViDauId;
    }

    public void setDonViDauId(Long donViDauId) {
        this.donViDauId = donViDauId;
    }

    public Long getDonViCuoiId() {
        return donViCuoiId;
    }

    public void setDonViCuoiId(Long donViCuoiId) {
        this.donViCuoiId = donViCuoiId;
    }

    public Long getMaGia() {
        return maGia;
    }

    public void setMaGia(Long maGia) {
        this.maGia = maGia;
    }

    public Long getMuCuocTbId() {
        return muCuocTbId;
    }

    public void setMuCuocTbId(Long muCuocTbId) {
        this.muCuocTbId = muCuocTbId;
    }

    public String getMuCuocTb() {
        return muCuocTb;
    }

    public void setMuCuocTb(String muCuocTb) {
        this.muCuocTb = muCuocTb;
    }

    public Long getCtvId() {
        return ctvId;
    }

    public void setCtvId(Long ctvId) {
        this.ctvId = ctvId;
    }

    public String getCongIch() {
        return congIch;
    }

    public void setCongIch(String congIch) {
        this.congIch = congIch;
    }

    public Long getThueBaoCuId() {
        return thueBaoCuId;
    }

    public void setThueBaoCuId(Long thueBaoCuId) {
        this.thueBaoCuId = thueBaoCuId;
    }

    public String getMaCuoc() {
        return maCuoc;
    }

    public void setMaCuoc(String maCuoc) {
        this.maCuoc = maCuoc;
    }

    public String getSoPlhd() {
        return soPlhd;
    }

    public void setSoPlhd(String soPlhd) {
        this.soPlhd = soPlhd;
    }

    public String getMaTbMoi() {
        return maTbMoi;
    }

    public void setMaTbMoi(String maTbMoi) {
        this.maTbMoi = maTbMoi;
    }

    public String getSoPl() {
        return soPl;
    }

    public void setSoPl(String soPl) {
        this.soPl = soPl;
    }

    public String getMaGd() {
        return maGd;
    }

    public void setMaGd(String maGd) {
        this.maGd = maGd;
    }

    public String getChuyenMang() {
        return chuyenMang;
    }

    public void setChuyenMang(String chuyenMang) {
        this.chuyenMang = chuyenMang;
    }

    public String getTgThueTu() {
        return tgThueTu;
    }

    public void setTgThueTu(String tgThueTu) {
        this.tgThueTu = tgThueTu;
    }

    public String getTgThueDen() {
        return tgThueDen;
    }

    public void setTgThueDen(String tgThueDen) {
        this.tgThueDen = tgThueDen;
    }

    public Long getGoiKtId() {
        return goiKtId;
    }

    public void setGoiKtId(Long goiKtId) {
        this.goiKtId = goiKtId;
    }

    public String getNgayCdlh() {
        return ngayCdlh;
    }

    public void setNgayCdlh(String ngayCdlh) {
        this.ngayCdlh = ngayCdlh;
    }

    public String getSoIpSuDung() {
        return soIpSuDung;
    }

    public void setSoIpSuDung(String soIpSuDung) {
        this.soIpSuDung = soIpSuDung;
    }

    public String getMaDhvip() {
        return maDhvip;
    }

    public void setMaDhvip(String maDhvip) {
        this.maDhvip = maDhvip;
    }

    public Long getSoThangCk() {
        return soThangCk;
    }

    public void setSoThangCk(Long soThangCk) {
        this.soThangCk = soThangCk;
    }

    public String getNgayKtck() {
        return ngayKtck;
    }

    public void setNgayKtck(String ngayKtck) {
        this.ngayKtck = ngayKtck;
    }

    public String getNgayHhTd() {
        return ngayHhTd;
    }

    public void setNgayHhTd(String ngayHhTd) {
        this.ngayHhTd = ngayHhTd;
    }

    public String getNhaccCuId() {
        return nhaccCuId;
    }

    public void setNhaccCuId(String nhaccCuId) {
        this.nhaccCuId = nhaccCuId;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getLoaiTb() {
        return loaiTb;
    }

    public void setLoaiTb(String loaiTb) {
        this.loaiTb = loaiTb;
    }
}
