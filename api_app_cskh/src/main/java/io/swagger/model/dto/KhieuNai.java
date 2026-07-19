package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Nhân viên
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class KhieuNai extends BaseDTO {
    @JsonProperty("phanVungId")
    private String phanVungId = null;
    @JsonProperty("khieuNaiId")
    private String khieuNaiId = null;
    @JsonProperty("thueBaoId")
    private String thueBaoId = null;
    @JsonProperty("dichVuVTId")
    private String dichVuVTId = null;
    @JsonProperty("loaiTBId")
    private String loaiTBId = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("nguoiKn")
    private String nguoiKn = null;
    @JsonProperty("dienThoaiLh")
    private String dienThoaiLh = null;
    @JsonProperty("chuDeKNId")
    private String chuDeKNId = null;
    @JsonProperty("ndKhieuNai")
    private String ndKhieuNai = null;
    @JsonProperty("ttknId")
    private String ttknId = null;
    @JsonProperty("donViId")
    private String donViId = null;
    @JsonProperty("nhanVienId")
    private String nhanVienId = null;
    @JsonProperty("ngayGq")
    private String ngayGq = null;
    @JsonProperty("nhanVienGqId")
    private String nhanVienGqId = null;
    @JsonProperty("noiDungGq")
    private String noiDungGq = null;
    @JsonProperty("henGQTu")
    private String henGQTu = null;
    @JsonProperty("henGQDen")
    private String henGQDen = null;
    @JsonProperty("ndTon")
    private String ndTon = null;
    @JsonProperty("ghiChuKn")
    private String ghiChuKn = null;
    @JsonProperty("mayCn")
    private String mayCn = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;
    @JsonProperty("congVanXl")
    private String congVanXl = null;
    @JsonProperty("ngayBn")
    private String ngayBn = null;
    @JsonProperty("congVanBn")
    private String congVanBn = null;
    @JsonProperty("cmt")
    private String cmt = null;
    @JsonProperty("ngayCap")
    private String ngayCap = null;
    @JsonProperty("noiCap")
    private String noiCap = null;
    @JsonProperty("htKNId")
    private String htKNId = null;
    @JsonProperty("nguyenNhanKn")
    private String nguyenNhanKn = null;
    @JsonProperty("diaChiLh")
    private String diaChiLh = null;
    @JsonProperty("maTb")
    private String maTb = null;
    @JsonProperty("tienGc")
    private String tienGc = null;
    @JsonProperty("vatGc")
    private String vatGc = null;
    @JsonProperty("quyTrinhId")
    private String quyTrinhId = null;
    @JsonProperty("ketQuaKNId")
    private String ketQuaKNId = null;
    @JsonProperty("thanhToanId")
    private String thanhToanId = null;
    @JsonProperty("maKn")
    private String maKn = null;
    @JsonProperty("giamCuocDt")
    private String giamCuocDt = null;
    @JsonProperty("giamCuocCp")
    private String giamCuocCp = null;
    @JsonProperty("tienTrinhknId")
    private String tienTrinhknId = null;
    @JsonProperty("vatDt")
    private String vatDt = null;
    @JsonProperty("vatCp")
    private String vatCp = null;
    @JsonProperty("kyKn")
    private String kyKn = null;
    @JsonProperty("lyDoHuygc")
    private String lyDoHuygc = null;
    @JsonProperty("ngayHuy")
    private String ngayHuy = null;
    @JsonProperty("nguoiHuy")
    private String nguoiHuy = null;
    @JsonProperty("capDoXLId")
    private String capDoXLId = null;
    @JsonProperty("chiTieuTg")
    private String chiTieuTg = null;
    @JsonProperty("thoiGianCk")
    private String thoiGianCk = null;
    @JsonProperty("phanAnhId")
    private String phanAnhId = null;
    @JsonProperty("blacklistId")
    private String blacklistId = null;
    @JsonProperty("baoHongId")
    private String baoHongId = null;
    @JsonProperty("thoiGianTn")
    private String thoiGianTn = null;
    @JsonProperty("loaiHDId")
    private String loaiHDId = null;
    @JsonProperty("hdkhId")
    private String hdkhId = null;
    @JsonProperty("email")
    private String email = null;
    @JsonProperty("paxlId")
    private String paxlId = null;
    @JsonProperty("haiLong")
    private String haiLong = null;
    @JsonProperty("httlId")
    private String httlId = null;
    @JsonProperty("maTt")
    private String maTt = null;
    @JsonProperty("maKh")
    private String maKh = null;
    @JsonProperty("goiktId")
    private String goiktId = null;
    @JsonProperty("tenTb")
    private String tenTb = null;
    @JsonProperty("diaChiTb")
    private String diaChiTb = null;
    @JsonProperty("tenChuDe")
    private String tenChuDe = null;
    @JsonProperty("tenNv")
    private String tenNv = null;
    @JsonProperty("phieuKNId")
    private String phieuKNId = null;
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("ngayHtGp")
    private String ngayHtGp = null;
    @JsonProperty("ngayCapCMT")
    private String ngayCapCMT = null;
    @JsonProperty("lyDoTon")
    private String lyDoTon = null;
    @JsonProperty("nhanVienThId")
    private String nhanVienThId = null;
    @JsonProperty("donViGiaoId")
    private String donViGiaoId = null;
    @JsonProperty("ndXacMinh")
    private String ndXacMinh = null;
    @JsonProperty("dvn")
    private String dvn = null;
    @JsonProperty("nhanVienGq")
    private String nhanVienGq = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("ndTraCon")
    private String ndTraCon = null;
    @JsonProperty("donViTraConId")
    private String donViTraConId = null;
    @JsonProperty("tenLoai")
    private String tenLoai = null;
    @JsonProperty("isHoanThanh")
    private String isHoanThanh = null;

    public String getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(String phanVungId) {
        this.phanVungId = phanVungId;
    }

    public String getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(String khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public String getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(String thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public String getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(String dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public String getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(String loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public String getNgayTn() {
        return ngayTn;
    }

    public void setNgayTn(String ngayTn) {
        this.ngayTn = ngayTn;
    }

    public String getNguoiKn() {
        return nguoiKn;
    }

    public void setNguoiKn(String nguoiKn) {
        this.nguoiKn = nguoiKn;
    }

    public String getDienThoaiLh() {
        return dienThoaiLh;
    }

    public void setDienThoaiLh(String dienThoaiLh) {
        this.dienThoaiLh = dienThoaiLh;
    }

    public String getChuDeKNId() {
        return chuDeKNId;
    }

    public void setChuDeKNId(String chuDeKNId) {
        this.chuDeKNId = chuDeKNId;
    }

    public String getNdKhieuNai() {
        return ndKhieuNai;
    }

    public void setNdKhieuNai(String ndKhieuNai) {
        this.ndKhieuNai = ndKhieuNai;
    }

    public String getTtknId() {
        return ttknId;
    }

    public void setTtknId(String ttknId) {
        this.ttknId = ttknId;
    }

    public String getDonViId() {
        return donViId;
    }

    public void setDonViId(String donViId) {
        this.donViId = donViId;
    }

    public String getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(String nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getNgayGq() {
        return ngayGq;
    }

    public void setNgayGq(String ngayGq) {
        this.ngayGq = ngayGq;
    }

    public String getNhanVienGqId() {
        return nhanVienGqId;
    }

    public void setNhanVienGqId(String nhanVienGqId) {
        this.nhanVienGqId = nhanVienGqId;
    }

    public String getNoiDungGq() {
        return noiDungGq;
    }

    public void setNoiDungGq(String noiDungGq) {
        this.noiDungGq = noiDungGq;
    }

    public String getHenGQTu() {
        return henGQTu;
    }

    public void setHenGQTu(String henGQTu) {
        this.henGQTu = henGQTu;
    }

    public String getHenGQDen() {
        return henGQDen;
    }

    public void setHenGQDen(String henGQDen) {
        this.henGQDen = henGQDen;
    }

    public String getNdTon() {
        return ndTon;
    }

    public void setNdTon(String ndTon) {
        this.ndTon = ndTon;
    }

    public String getGhiChuKn() {
        return ghiChuKn;
    }

    public void setGhiChuKn(String ghiChuKn) {
        this.ghiChuKn = ghiChuKn;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
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

    public String getCongVanXl() {
        return congVanXl;
    }

    public void setCongVanXl(String congVanXl) {
        this.congVanXl = congVanXl;
    }

    public String getNgayBn() {
        return ngayBn;
    }

    public void setNgayBn(String ngayBn) {
        this.ngayBn = ngayBn;
    }

    public String getCongVanBn() {
        return congVanBn;
    }

    public void setCongVanBn(String congVanBn) {
        this.congVanBn = congVanBn;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getHtKNId() {
        return htKNId;
    }

    public void setHtKNId(String htKNId) {
        this.htKNId = htKNId;
    }

    public String getNguyenNhanKn() {
        return nguyenNhanKn;
    }

    public void setNguyenNhanKn(String nguyenNhanKn) {
        this.nguyenNhanKn = nguyenNhanKn;
    }

    public String getDiaChiLh() {
        return diaChiLh;
    }

    public void setDiaChiLh(String diaChiLh) {
        this.diaChiLh = diaChiLh;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getTienGc() {
        return tienGc;
    }

    public void setTienGc(String tienGc) {
        this.tienGc = tienGc;
    }

    public String getVatGc() {
        return vatGc;
    }

    public void setVatGc(String vatGc) {
        this.vatGc = vatGc;
    }

    public String getQuyTrinhId() {
        return quyTrinhId;
    }

    public void setQuyTrinhId(String quyTrinhId) {
        this.quyTrinhId = quyTrinhId;
    }

    public String getKetQuaKNId() {
        return ketQuaKNId;
    }

    public void setKetQuaKNId(String ketQuaKNId) {
        this.ketQuaKNId = ketQuaKNId;
    }

    public String getThanhToanId() {
        return thanhToanId;
    }

    public void setThanhToanId(String thanhToanId) {
        this.thanhToanId = thanhToanId;
    }

    public String getMaKn() {
        return maKn;
    }

    public void setMaKn(String maKn) {
        this.maKn = maKn;
    }

    public String getGiamCuocDt() {
        return giamCuocDt;
    }

    public void setGiamCuocDt(String giamCuocDt) {
        this.giamCuocDt = giamCuocDt;
    }

    public String getGiamCuocCp() {
        return giamCuocCp;
    }

    public void setGiamCuocCp(String giamCuocCp) {
        this.giamCuocCp = giamCuocCp;
    }

    public String getTienTrinhknId() {
        return tienTrinhknId;
    }

    public void setTienTrinhknId(String tienTrinhknId) {
        this.tienTrinhknId = tienTrinhknId;
    }

    public String getVatDt() {
        return vatDt;
    }

    public void setVatDt(String vatDt) {
        this.vatDt = vatDt;
    }

    public String getVatCp() {
        return vatCp;
    }

    public void setVatCp(String vatCp) {
        this.vatCp = vatCp;
    }

    public String getKyKn() {
        return kyKn;
    }

    public void setKyKn(String kyKn) {
        this.kyKn = kyKn;
    }

    public String getLyDoHuygc() {
        return lyDoHuygc;
    }

    public void setLyDoHuygc(String lyDoHuygc) {
        this.lyDoHuygc = lyDoHuygc;
    }

    public String getNgayHuy() {
        return ngayHuy;
    }

    public void setNgayHuy(String ngayHuy) {
        this.ngayHuy = ngayHuy;
    }

    public String getNguoiHuy() {
        return nguoiHuy;
    }

    public void setNguoiHuy(String nguoiHuy) {
        this.nguoiHuy = nguoiHuy;
    }

    public String getCapDoXLId() {
        return capDoXLId;
    }

    public void setCapDoXLId(String capDoXLId) {
        this.capDoXLId = capDoXLId;
    }

    public String getChiTieuTg() {
        return chiTieuTg;
    }

    public void setChiTieuTg(String chiTieuTg) {
        this.chiTieuTg = chiTieuTg;
    }

    public String getThoiGianCk() {
        return thoiGianCk;
    }

    public void setThoiGianCk(String thoiGianCk) {
        this.thoiGianCk = thoiGianCk;
    }

    public String getPhanAnhId() {
        return phanAnhId;
    }

    public void setPhanAnhId(String phanAnhId) {
        this.phanAnhId = phanAnhId;
    }

    public String getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(String blacklistId) {
        this.blacklistId = blacklistId;
    }

    public String getBaoHongId() {
        return baoHongId;
    }

    public void setBaoHongId(String baoHongId) {
        this.baoHongId = baoHongId;
    }

    public String getThoiGianTn() {
        return thoiGianTn;
    }

    public void setThoiGianTn(String thoiGianTn) {
        this.thoiGianTn = thoiGianTn;
    }

    public String getLoaiHDId() {
        return loaiHDId;
    }

    public void setLoaiHDId(String loaiHDId) {
        this.loaiHDId = loaiHDId;
    }

    public String getHdkhId() {
        return hdkhId;
    }

    public void setHdkhId(String hdkhId) {
        this.hdkhId = hdkhId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaxlId() {
        return paxlId;
    }

    public void setPaxlId(String paxlId) {
        this.paxlId = paxlId;
    }

    public String getHaiLong() {
        return haiLong;
    }

    public void setHaiLong(String haiLong) {
        this.haiLong = haiLong;
    }

    public String getHttlId() {
        return httlId;
    }

    public void setHttlId(String httlId) {
        this.httlId = httlId;
    }

    public String getMaTt() {
        return maTt;
    }

    public void setMaTt(String maTt) {
        this.maTt = maTt;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getGoiktId() {
        return goiktId;
    }

    public void setGoiktId(String goiktId) {
        this.goiktId = goiktId;
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

    public String getTenChuDe() {
        return tenChuDe;
    }

    public void setTenChuDe(String tenChuDe) {
        this.tenChuDe = tenChuDe;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(String phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public String getNgayHtGp() {
        return ngayHtGp;
    }

    public void setNgayHtGp(String ngayHtGp) {
        this.ngayHtGp = ngayHtGp;
    }

    public String getNgayCapCMT() {
        return ngayCapCMT;
    }

    public void setNgayCapCMT(String ngayCapCMT) {
        this.ngayCapCMT = ngayCapCMT;
    }

    public String getLyDoTon() {
        return lyDoTon;
    }

    public void setLyDoTon(String lyDoTon) {
        this.lyDoTon = lyDoTon;
    }

    public String getNhanVienThId() {
        return nhanVienThId;
    }

    public void setNhanVienThId(String nhanVienThId) {
        this.nhanVienThId = nhanVienThId;
    }

    public String getDonViGiaoId() {
        return donViGiaoId;
    }

    public void setDonViGiaoId(String donViGiaoId) {
        this.donViGiaoId = donViGiaoId;
    }

    public String getNdXacMinh() {
        return ndXacMinh;
    }

    public void setNdXacMinh(String ndXacMinh) {
        this.ndXacMinh = ndXacMinh;
    }

    public String getDvn() {
        return dvn;
    }

    public void setDvn(String dvn) {
        this.dvn = dvn;
    }

    public String getNhanVienGq() {
        return nhanVienGq;
    }

    public void setNhanVienGq(String nhanVienGq) {
        this.nhanVienGq = nhanVienGq;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getNdTraCon() {
        return ndTraCon;
    }

    public void setNdTraCon(String ndTraCon) {
        this.ndTraCon = ndTraCon;
    }

    public String getDonViTraConId() {
        return donViTraConId;
    }

    public void setDonViTraConId(String donViTraConId) {
        this.donViTraConId = donViTraConId;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getIsHoanThanh() {
        return isHoanThanh;
    }

    public void setIsHoanThanh(String isHoanThanh) {
        this.isHoanThanh = isHoanThanh;
    }
}
