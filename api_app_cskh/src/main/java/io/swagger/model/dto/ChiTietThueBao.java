package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Chi tiết thuê bao
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChiTietThueBao {

    private Long thueBaoId;
    private Long thanhToanId;
    private Long khachHangId;
    private String maTb;
    private String maLt;
    private String maKh;
    private String dvCungDoiCap;
    private String tenDslam;
    private String port;
    private String ne;
    private String tenCapGoc;
    private String doiCapGoc;
    private String tenKetCuoi;
    private Long vitri;
    private String diaChi;
    private String nhanVienQuanLy;
    private String nhanVienNhanViec;
    private String ngayGiaoViec;
    private Long dichVuVtId;
    private String account;
    private String maDoiCap;
    private String vanDong;
    private String tenTb;
    private String diaChiLd;
    private String trangThaiTb;
    private Long loaiTbId;
    private String tenDvvt;
    private String loaiHinhTb;
    private String tenLoaiKh;
    private String diaChiTb;
    private String diaChiTt;
    private String sdt;
    private String diaChiKh;
    private Long loaiGtId;
    private String soGt;
    private String noiCap;
    private String ngayCap;
    private String tenKh;
    private String ngayCapGt;
    private Long ketCuoiId;
    private String diaChiKc;
    private String tenKc;
    private String ngaySd;
    private String ngayTd;
    private String ngayCat;
    private String thongTinDatCoc;
    private String goiCuoc;
    private String vmp;
    private String maVnpt;
    private String tenDt;
    private String ttNe;
    private String toaDo;
    private String tenNvkd;
    private String moTaLd;

    private Long trangThaiTbId;

    public ChiTietThueBao convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            thueBaoId = rs.getLong("THUEBAO_ID");
            thanhToanId = rs.getLong("THANHTOAN_ID");
            khachHangId = rs.getLong("KHACHHANG_ID");
            maTb = rs.getString("MA_TB");
            maLt = rs.getString("MA_LT");
            maKh = rs.getString("MA_KH");
            dvCungDoiCap = rs.getString("DVUCUNGDOICAP");
            tenDslam = rs.getString("TENDSLAM");
            port = rs.getString("PORT");
            ne = rs.getString("NE");
            tenCapGoc = rs.getString("TEN_CAP_GOC");
            doiCapGoc = rs.getString("DOI_CAP_GOC");
            tenKetCuoi = rs.getString("TEN_KET_CUOI");
            vitri = rs.getLong("VITRI");
            diaChi = rs.getString("DIA_CHI");
            nhanVienQuanLy = rs.getString("NHANVIEN_QUANLY");
            nhanVienNhanViec = rs.getString("NHANVIEN_NHANVIEC");
            ngayGiaoViec = rs.getString("NGAYGIAOVIEC");
            dichVuVtId = rs.getLong("DICHVUVT_ID");
            account = rs.getString("ACCOUNT");
            maDoiCap = rs.getString("MADOICAP");
            vanDong = rs.getString("VANDONG");
            tenTb = rs.getString("TEN_TB");
            diaChiLd = rs.getString("DIACHI_LD");
            trangThaiTb = rs.getString("TRANGTHAI_TB");
            loaiTbId = rs.getLong("LOAITB_ID");
            tenDvvt = rs.getString("TEN_DVVT");
            loaiHinhTb = rs.getString("LOAIHINH_TB");
            tenLoaiKh = rs.getString("TEN_LOAIKH");
            diaChiTb = rs.getString("DIACHI_TB");
            diaChiTt = rs.getString("DIACHI_TT");
            sdt = rs.getString("SO_DT");
            diaChiKh = rs.getString("DIACHI_KH");
            loaiGtId = rs.getLong("LOAIGT_ID");
            soGt = rs.getString("SO_GT");
            noiCap = rs.getString("NOICAP");
            ngayCap = rs.getString("NGAYCAP");
            tenKh = rs.getString("TEN_KH");
            ngayCapGt = rs.getString("NGAYCAP_GT");
            ketCuoiId = rs.getLong("KETCUOI_ID");
            diaChiKc = rs.getString("DIACHI_KC");
            tenKc = rs.getString("TEN_KC");
            ngaySd = rs.getString("NGAY_SD");
            ngayTd = rs.getString("NGAY_TD");
            ngayCat = rs.getString("NGAY_CAT");
            thongTinDatCoc = rs.getString("THONGTINDATCOC");
            goiCuoc = rs.getString("GOICUOC");
            vmp = rs.getString("VMP");
            maVnpt = rs.getString("MA_VNPT");
            tenDt = rs.getString("TEN_DT");
            ttNe = rs.getString("TT_NE");
            toaDo = rs.getString("TOADO");
            tenNvkd = rs.getString("TEN_NVKD");
            moTaLd = rs.getString("MOTA_LD");
            trangThaiTbId = rs.getLong("TRANGTHAITB_ID");
            return this;
        } else {
            return null;
        }
    }

    public Long getTrangThaiTbId() {
        return trangThaiTbId;
    }

    public void setTrangThaiTbId(Long trangThaiTbId) {
        this.trangThaiTbId = trangThaiTbId;
    }

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public Long getThanhToanId() {
        return thanhToanId;
    }

    public void setThanhToanId(Long thanhToanId) {
        this.thanhToanId = thanhToanId;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getMaLt() {
        return maLt;
    }

    public void setMaLt(String maLt) {
        this.maLt = maLt;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getDvCungDoiCap() {
        return dvCungDoiCap;
    }

    public void setDvCungDoiCap(String dvCungDoiCap) {
        this.dvCungDoiCap = dvCungDoiCap;
    }

    public String getTenDslam() {
        return tenDslam;
    }

    public void setTenDslam(String tenDslam) {
        this.tenDslam = tenDslam;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNe() {
        return ne;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }

    public String getTenCapGoc() {
        return tenCapGoc;
    }

    public void setTenCapGoc(String tenCapGoc) {
        this.tenCapGoc = tenCapGoc;
    }

    public String getDoiCapGoc() {
        return doiCapGoc;
    }

    public void setDoiCapGoc(String doiCapGoc) {
        this.doiCapGoc = doiCapGoc;
    }

    public String getTenKetCuoi() {
        return tenKetCuoi;
    }

    public void setTenKetCuoi(String tenKetCuoi) {
        this.tenKetCuoi = tenKetCuoi;
    }

    public Long getVitri() {
        return vitri;
    }

    public void setVitri(Long vitri) {
        this.vitri = vitri;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNhanVienQuanLy() {
        return nhanVienQuanLy;
    }

    public void setNhanVienQuanLy(String nhanVienQuanLy) {
        this.nhanVienQuanLy = nhanVienQuanLy;
    }

    public String getNhanVienNhanViec() {
        return nhanVienNhanViec;
    }

    public void setNhanVienNhanViec(String nhanVienNhanViec) {
        this.nhanVienNhanViec = nhanVienNhanViec;
    }

    public String getNgayGiaoViec() {
        return ngayGiaoViec;
    }

    public void setNgayGiaoViec(String ngayGiaoViec) {
        this.ngayGiaoViec = ngayGiaoViec;
    }

    public Long getDichVuVtId() {
        return dichVuVtId;
    }

    public void setDichVuVtId(Long dichVuVtId) {
        this.dichVuVtId = dichVuVtId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMaDoiCap() {
        return maDoiCap;
    }

    public void setMaDoiCap(String maDoiCap) {
        this.maDoiCap = maDoiCap;
    }

    public String getVanDong() {
        return vanDong;
    }

    public void setVanDong(String vanDong) {
        this.vanDong = vanDong;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public String getDiaChiLd() {
        return diaChiLd;
    }

    public void setDiaChiLd(String diaChiLd) {
        this.diaChiLd = diaChiLd;
    }

    public String getTrangThaiTb() {
        return trangThaiTb;
    }

    public void setTrangThaiTb(String trangThaiTb) {
        this.trangThaiTb = trangThaiTb;
    }

    public Long getLoaiTbId() {
        return loaiTbId;
    }

    public void setLoaiTbId(Long loaiTbId) {
        this.loaiTbId = loaiTbId;
    }

    public String getTenDvvt() {
        return tenDvvt;
    }

    public void setTenDvvt(String tenDvvt) {
        this.tenDvvt = tenDvvt;
    }

    public String getLoaiHinhTb() {
        return loaiHinhTb;
    }

    public void setLoaiHinhTb(String loaiHinhTb) {
        this.loaiHinhTb = loaiHinhTb;
    }

    public String getTenLoaiKh() {
        return tenLoaiKh;
    }

    public void setTenLoaiKh(String tenLoaiKh) {
        this.tenLoaiKh = tenLoaiKh;
    }

    public String getDiaChiTb() {
        return diaChiTb;
    }

    public void setDiaChiTb(String diaChiTb) {
        this.diaChiTb = diaChiTb;
    }

    public String getDiaChiTt() {
        return diaChiTt;
    }

    public void setDiaChiTt(String diaChiTt) {
        this.diaChiTt = diaChiTt;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChiKh() {
        return diaChiKh;
    }

    public void setDiaChiKh(String diaChiKh) {
        this.diaChiKh = diaChiKh;
    }

    public Long getLoaiGtId() {
        return loaiGtId;
    }

    public void setLoaiGtId(Long loaiGtId) {
        this.loaiGtId = loaiGtId;
    }

    public String getSoGt() {
        return soGt;
    }

    public void setSoGt(String soGt) {
        this.soGt = soGt;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getNgayCapGt() {
        return ngayCapGt;
    }

    public void setNgayCapGt(String ngayCapGt) {
        this.ngayCapGt = ngayCapGt;
    }

    public Long getKetCuoiId() {
        return ketCuoiId;
    }

    public void setKetCuoiId(Long ketCuoiId) {
        this.ketCuoiId = ketCuoiId;
    }

    public String getDiaChiKc() {
        return diaChiKc;
    }

    public void setDiaChiKc(String diaChiKc) {
        this.diaChiKc = diaChiKc;
    }

    public String getTenKc() {
        return tenKc;
    }

    public void setTenKc(String tenKc) {
        this.tenKc = tenKc;
    }

    public String getNgaySd() {
        return ngaySd;
    }

    public void setNgaySd(String ngaySd) {
        this.ngaySd = ngaySd;
    }

    public String getNgayTd() {
        return ngayTd;
    }

    public void setNgayTd(String ngayTd) {
        this.ngayTd = ngayTd;
    }

    public String getNgayCat() {
        return ngayCat;
    }

    public void setNgayCat(String ngayCat) {
        this.ngayCat = ngayCat;
    }

    public String getThongTinDatCoc() {
        return thongTinDatCoc;
    }

    public void setThongTinDatCoc(String thongTinDatCoc) {
        this.thongTinDatCoc = thongTinDatCoc;
    }

    public String getGoiCuoc() {
        return goiCuoc;
    }

    public void setGoiCuoc(String goiCuoc) {
        this.goiCuoc = goiCuoc;
    }

    public String getVmp() {
        return vmp;
    }

    public void setVmp(String vmp) {
        this.vmp = vmp;
    }

    public String getMaVnpt() {
        return maVnpt;
    }

    public void setMaVnpt(String maVnpt) {
        this.maVnpt = maVnpt;
    }

    public String getTenDt() {
        return tenDt;
    }

    public void setTenDt(String tenDt) {
        this.tenDt = tenDt;
    }

    public String getTtNe() {
        return ttNe;
    }

    public void setTtNe(String ttNe) {
        this.ttNe = ttNe;
    }

    public String getToaDo() {
        return toaDo;
    }

    public void setToaDo(String toaDo) {
        this.toaDo = toaDo;
    }

    public String getTenNvkd() {
        return tenNvkd;
    }

    public void setTenNvkd(String tenNvkd) {
        this.tenNvkd = tenNvkd;
    }

    public String getMoTaLd() {
        return moTaLd;
    }

    public void setMoTaLd(String moTaLd) {
        this.moTaLd = moTaLd;
    }
}
