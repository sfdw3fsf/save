package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * AnhNghiemThuSc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChiTietKhieuNai extends BaseDTO {
    @JsonProperty("thuebaoId")
    private String thuebaoId = null;
    @JsonProperty("thanhtoanId")
    private String thanhtoanId = null;
    @JsonProperty("maTb")
    private String maTb = null;
    @JsonProperty("dichvuvtId")
    private String dichvuvtId = null;
    @JsonProperty("tenDvvt")
    private String tenDvvt = null;
    @JsonProperty("loaitbId")
    private String loaitbId = null;
    @JsonProperty("loaihinhTb")
    private String loaihinhTb = null;
    @JsonProperty("tenLoaikh")
    private String tenLoaikh = null;
    @JsonProperty("trangthaiTb")
    private String trangthaiTb = null;
    @JsonProperty("diachiTb")
    private String diachiTb = null;
    @JsonProperty("diachiTt")
    private String diachiTt = null;
    @JsonProperty("phanvungId")
    private String phanvungId = null;
    @JsonProperty("khieunaiId")
    private String khieunaiId = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("nguoiKn")
    private String nguoiKn = null;
    @JsonProperty("dienthoaiLh")
    private String dienthoaiLh = null;
    @JsonProperty("chudeknId")
    private String chudeknId = null;
    @JsonProperty("ndKhieunai")
    private String ndKhieunai = null;
    @JsonProperty("ttknId")
    private String ttknId = null;
    @JsonProperty("donviId")
    private String donviId = null;
    @JsonProperty("nhanvienId")
    private String nhanvienId = null;
    @JsonProperty("ngayGq")
    private String ngayGq = null;
    @JsonProperty("nhanvienGqId")
    private String nhanvienGqId = null;
    @JsonProperty("noidungGq")
    private String noidungGq = null;
    @JsonProperty("hengqTu")
    private String hengqTu = null;
    @JsonProperty("hengqDen")
    private String hengqDen = null;
    @JsonProperty("ndTon")
    private String ndTon = null;
    @JsonProperty("ghichuKn")
    private String ghichuKn = null;
    @JsonProperty("mayCn")
    private String mayCn = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;
    @JsonProperty("congvanXl")
    private String congvanXl = null;
    @JsonProperty("ngayBn")
    private String ngayBn = null;
    @JsonProperty("congvanBn")
    private String congvanBn = null;
    @JsonProperty("cmt")
    private String cmt = null;
    @JsonProperty("ngaycap")
    private String ngaycap = null;
    @JsonProperty("noicap")
    private String noicap = null;
    @JsonProperty("htknId")
    private String htknId = null;
    @JsonProperty("nguyennhanKn")
    private String nguyennhanKn = null;
    @JsonProperty("diachiLh")
    private String diachiLh = null;
    @JsonProperty("tienGc")
    private String tienGc = null;
    @JsonProperty("vatGc")
    private String vatGc = null;
    @JsonProperty("quytrinhId")
    private String quytrinhId = null;
    @JsonProperty("ketquaknId")
    private String ketquaknId = null;
    @JsonProperty("maKn")
    private String maKn = null;
    @JsonProperty("giamcuocDt")
    private String giamcuocDt = null;
    @JsonProperty("giamcuocCp")
    private String giamcuocCp = null;
    @JsonProperty("tientrinhknId")
    private String tientrinhknId = null;
    @JsonProperty("vatDt")
    private String vatDt = null;
    @JsonProperty("vatCp")
    private String vatCp = null;
    @JsonProperty("kyKn")
    private String kyKn = null;
    @JsonProperty("lydoHuygc")
    private String lydoHuygc = null;
    @JsonProperty("ngayhuy")
    private String ngayhuy = null;
    @JsonProperty("nguoihuy")
    private String nguoihuy = null;
    @JsonProperty("capdoxlId")
    private String capdoxlId = null;
    @JsonProperty("chitieuTg")
    private String chitieuTg = null;
    @JsonProperty("thoigianCk")
    private String thoigianCk = null;
    @JsonProperty("phananhId")
    private String phananhId = null;
    @JsonProperty("blacklistId")
    private String blacklistId = null;
    @JsonProperty("baohongId")
    private String baohongId = null;
    @JsonProperty("thoigianTn")
    private String thoigianTn = null;
    @JsonProperty("loaihdId")
    private String loaihdId = null;
    @JsonProperty("hdkhId")
    private String hdkhId = null;
    @JsonProperty("email")
    private String email = null;
    @JsonProperty("paxlId")
    private String paxlId = null;
    @JsonProperty("hailong")
    private String hailong = null;
    @JsonProperty("httlId")
    private String httlId = null;
    @JsonProperty("maTt")
    private String maTt = null;
    @JsonProperty("maKh")
    private String maKh = null;
    @JsonProperty("goiktId")
    private String goiktId = null;
    @JsonProperty("loaiknId")
    private String loaiknId = null;
    @JsonProperty("lydoTon")
    private String lydoTon = null;
    @JsonProperty("huonggiaoTieptheoId")
    private String huonggiaoTieptheoId = null;
    @JsonProperty("huonggiaoTieptheo")
    private String huonggiaoTieptheo = null;
    @JsonProperty("loaidvDichId")
    private String loaidvDichId = null;
    @JsonProperty("huonggiaoXulyxongId")
    private String huonggiaoXulyxongId = null;
    @JsonProperty("phieuknId")
    private String phieuknId = null;
    @JsonProperty("ngaygiao")
    private String ngaygiao = null;
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("donviGiaoId")
    private String donviGiaoId = null;
    @JsonProperty("nhanvienGiaoId")
    private String nhanvienGiaoId = null;
    @JsonProperty("donviNhanId")
    private String donviNhanId = null;
    @JsonProperty("nhanvienThId")
    private String nhanvienThId = null;
    @JsonProperty("phieuChaId")
    private String phieuChaId = null;
    @JsonProperty("huonggiaoId")
    private String huonggiaoId = null;
    @JsonProperty("ghichuTh")
    private String ghichuTh = null;
    @JsonProperty("ttphId")
    private String ttphId = null;
    @JsonProperty("nhanvienTraId")
    private String nhanvienTraId = null;
    @JsonProperty("ngaytra")
    private String ngaytra = null;
    @JsonProperty("ngayTh")
    private String ngayTh = null;
    @JsonProperty("ndTra")
    private String ndTra = null;
    @JsonProperty("socongvan")
    private String socongvan = null;
    @JsonProperty("ndXacminh")
    private String ndXacminh = null;
    @JsonProperty("mota")
    private String mota = null;
    @JsonProperty("songayTn")
    private String songayTn = null;
    @JsonProperty("lydotraConId")
    private String lydotraConId = null;
    @JsonProperty("ndTraCon")
    private String ndTraCon = null;
    @JsonProperty("ngaytraCon")
    private String ngaytraCon = null;
    @JsonProperty("donvitraConId")
    private String donvitraConId = null;
    @JsonProperty("nhanvientraConId")
    private String nhanvientraConId = null;
    @JsonProperty("linkfile")
    private String linkfile = null;
    @JsonProperty("tenLoaiKN")
    private String tenLoaiKN = null;
    @JsonProperty("tenHTKN")
    private String tenHTKN = null;
    @JsonProperty("tenKQLoi")
    private String tenKQLoi = null;
    @JsonProperty("chuDeKN")
    private ChuDeKN chuDeKN = null;

    public String getTenKQLoi() {
        return tenKQLoi;
    }

    public void setTenKQLoi(String tenKQLoi) {
        this.tenKQLoi = tenKQLoi;
    }

    public String getTenLoaiKN() {
        return tenLoaiKN;
    }

    public void setTenLoaiKN(String tenLoaiKN) {
        this.tenLoaiKN = tenLoaiKN;
    }

    public String getTenHTKN() {
        return tenHTKN;
    }

    public void setTenHTKN(String tenHTKN) {
        this.tenHTKN = tenHTKN;
    }

    public ChuDeKN getChuDeKN() {
        return chuDeKN;
    }

    public void setChuDeKN(ChuDeKN chuDeKN) {
        this.chuDeKN = chuDeKN;
    }

    public String getThuebaoId() {
        return thuebaoId;
    }

    public void setThuebaoId(String thuebaoId) {
        this.thuebaoId = thuebaoId;
    }

    public String getThanhtoanId() {
        return thanhtoanId;
    }

    public void setThanhtoanId(String thanhtoanId) {
        this.thanhtoanId = thanhtoanId;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getDichvuvtId() {
        return dichvuvtId;
    }

    public void setDichvuvtId(String dichvuvtId) {
        this.dichvuvtId = dichvuvtId;
    }

    public String getTenDvvt() {
        return tenDvvt;
    }

    public void setTenDvvt(String tenDvvt) {
        this.tenDvvt = tenDvvt;
    }

    public String getLoaitbId() {
        return loaitbId;
    }

    public void setLoaitbId(String loaitbId) {
        this.loaitbId = loaitbId;
    }

    public String getLoaihinhTb() {
        return loaihinhTb;
    }

    public void setLoaihinhTb(String loaihinhTb) {
        this.loaihinhTb = loaihinhTb;
    }

    public String getTenLoaikh() {
        return tenLoaikh;
    }

    public void setTenLoaikh(String tenLoaikh) {
        this.tenLoaikh = tenLoaikh;
    }

    public String getTrangthaiTb() {
        return trangthaiTb;
    }

    public void setTrangthaiTb(String trangthaiTb) {
        this.trangthaiTb = trangthaiTb;
    }

    public String getDiachiTb() {
        return diachiTb;
    }

    public void setDiachiTb(String diachiTb) {
        this.diachiTb = diachiTb;
    }

    public String getDiachiTt() {
        return diachiTt;
    }

    public void setDiachiTt(String diachiTt) {
        this.diachiTt = diachiTt;
    }

    public String getPhanvungId() {
        return phanvungId;
    }

    public void setPhanvungId(String phanvungId) {
        this.phanvungId = phanvungId;
    }

    public String getKhieunaiId() {
        return khieunaiId;
    }

    public void setKhieunaiId(String khieunaiId) {
        this.khieunaiId = khieunaiId;
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

    public String getDienthoaiLh() {
        return dienthoaiLh;
    }

    public void setDienthoaiLh(String dienthoaiLh) {
        this.dienthoaiLh = dienthoaiLh;
    }

    public String getChudeknId() {
        return chudeknId;
    }

    public void setChudeknId(String chudeknId) {
        this.chudeknId = chudeknId;
    }

    public String getNdKhieunai() {
        return ndKhieunai;
    }

    public void setNdKhieunai(String ndKhieunai) {
        this.ndKhieunai = ndKhieunai;
    }

    public String getTtknId() {
        return ttknId;
    }

    public void setTtknId(String ttknId) {
        this.ttknId = ttknId;
    }

    public String getDonviId() {
        return donviId;
    }

    public void setDonviId(String donviId) {
        this.donviId = donviId;
    }

    public String getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(String nhanvienId) {
        this.nhanvienId = nhanvienId;
    }

    public String getNgayGq() {
        return ngayGq;
    }

    public void setNgayGq(String ngayGq) {
        this.ngayGq = ngayGq;
    }

    public String getNhanvienGqId() {
        return nhanvienGqId;
    }

    public void setNhanvienGqId(String nhanvienGqId) {
        this.nhanvienGqId = nhanvienGqId;
    }

    public String getNoidungGq() {
        return noidungGq;
    }

    public void setNoidungGq(String noidungGq) {
        this.noidungGq = noidungGq;
    }

    public String getHengqTu() {
        return hengqTu;
    }

    public void setHengqTu(String hengqTu) {
        this.hengqTu = hengqTu;
    }

    public String getHengqDen() {
        return hengqDen;
    }

    public void setHengqDen(String hengqDen) {
        this.hengqDen = hengqDen;
    }

    public String getNdTon() {
        return ndTon;
    }

    public void setNdTon(String ndTon) {
        this.ndTon = ndTon;
    }

    public String getGhichuKn() {
        return ghichuKn;
    }

    public void setGhichuKn(String ghichuKn) {
        this.ghichuKn = ghichuKn;
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

    public String getCongvanXl() {
        return congvanXl;
    }

    public void setCongvanXl(String congvanXl) {
        this.congvanXl = congvanXl;
    }

    public String getNgayBn() {
        return ngayBn;
    }

    public void setNgayBn(String ngayBn) {
        this.ngayBn = ngayBn;
    }

    public String getCongvanBn() {
        return congvanBn;
    }

    public void setCongvanBn(String congvanBn) {
        this.congvanBn = congvanBn;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getNgaycap() {
        return ngaycap;
    }

    public void setNgaycap(String ngaycap) {
        this.ngaycap = ngaycap;
    }

    public String getNoicap() {
        return noicap;
    }

    public void setNoicap(String noicap) {
        this.noicap = noicap;
    }

    public String getHtknId() {
        return htknId;
    }

    public void setHtknId(String htknId) {
        this.htknId = htknId;
    }

    public String getNguyennhanKn() {
        return nguyennhanKn;
    }

    public void setNguyennhanKn(String nguyennhanKn) {
        this.nguyennhanKn = nguyennhanKn;
    }

    public String getDiachiLh() {
        return diachiLh;
    }

    public void setDiachiLh(String diachiLh) {
        this.diachiLh = diachiLh;
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

    public String getQuytrinhId() {
        return quytrinhId;
    }

    public void setQuytrinhId(String quytrinhId) {
        this.quytrinhId = quytrinhId;
    }

    public String getKetquaknId() {
        return ketquaknId;
    }

    public void setKetquaknId(String ketquaknId) {
        this.ketquaknId = ketquaknId;
    }

    public String getMaKn() {
        return maKn;
    }

    public void setMaKn(String maKn) {
        this.maKn = maKn;
    }

    public String getGiamcuocDt() {
        return giamcuocDt;
    }

    public void setGiamcuocDt(String giamcuocDt) {
        this.giamcuocDt = giamcuocDt;
    }

    public String getGiamcuocCp() {
        return giamcuocCp;
    }

    public void setGiamcuocCp(String giamcuocCp) {
        this.giamcuocCp = giamcuocCp;
    }

    public String getTientrinhknId() {
        return tientrinhknId;
    }

    public void setTientrinhknId(String tientrinhknId) {
        this.tientrinhknId = tientrinhknId;
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

    public String getLydoHuygc() {
        return lydoHuygc;
    }

    public void setLydoHuygc(String lydoHuygc) {
        this.lydoHuygc = lydoHuygc;
    }

    public String getNgayhuy() {
        return ngayhuy;
    }

    public void setNgayhuy(String ngayhuy) {
        this.ngayhuy = ngayhuy;
    }

    public String getNguoihuy() {
        return nguoihuy;
    }

    public void setNguoihuy(String nguoihuy) {
        this.nguoihuy = nguoihuy;
    }

    public String getCapdoxlId() {
        return capdoxlId;
    }

    public void setCapdoxlId(String capdoxlId) {
        this.capdoxlId = capdoxlId;
    }

    public String getChitieuTg() {
        return chitieuTg;
    }

    public void setChitieuTg(String chitieuTg) {
        this.chitieuTg = chitieuTg;
    }

    public String getThoigianCk() {
        return thoigianCk;
    }

    public void setThoigianCk(String thoigianCk) {
        this.thoigianCk = thoigianCk;
    }

    public String getPhananhId() {
        return phananhId;
    }

    public void setPhananhId(String phananhId) {
        this.phananhId = phananhId;
    }

    public String getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(String blacklistId) {
        this.blacklistId = blacklistId;
    }

    public String getBaohongId() {
        return baohongId;
    }

    public void setBaohongId(String baohongId) {
        this.baohongId = baohongId;
    }

    public String getThoigianTn() {
        return thoigianTn;
    }

    public void setThoigianTn(String thoigianTn) {
        this.thoigianTn = thoigianTn;
    }

    public String getLoaihdId() {
        return loaihdId;
    }

    public void setLoaihdId(String loaihdId) {
        this.loaihdId = loaihdId;
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

    public String getHailong() {
        return hailong;
    }

    public void setHailong(String hailong) {
        this.hailong = hailong;
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

    public String getLoaiknId() {
        return loaiknId;
    }

    public void setLoaiknId(String loaiknId) {
        this.loaiknId = loaiknId;
    }

    public String getLydoTon() {
        return lydoTon;
    }

    public void setLydoTon(String lydoTon) {
        this.lydoTon = lydoTon;
    }

    public String getHuonggiaoTieptheoId() {
        return huonggiaoTieptheoId;
    }

    public void setHuonggiaoTieptheoId(String huonggiaoTieptheoId) {
        this.huonggiaoTieptheoId = huonggiaoTieptheoId;
    }

    public String getHuonggiaoTieptheo() {
        return huonggiaoTieptheo;
    }

    public void setHuonggiaoTieptheo(String huonggiaoTieptheo) {
        this.huonggiaoTieptheo = huonggiaoTieptheo;
    }

    public String getLoaidvDichId() {
        return loaidvDichId;
    }

    public void setLoaidvDichId(String loaidvDichId) {
        this.loaidvDichId = loaidvDichId;
    }

    public String getHuonggiaoXulyxongId() {
        return huonggiaoXulyxongId;
    }

    public void setHuonggiaoXulyxongId(String huonggiaoXulyxongId) {
        this.huonggiaoXulyxongId = huonggiaoXulyxongId;
    }

    public String getPhieuknId() {
        return phieuknId;
    }

    public void setPhieuknId(String phieuknId) {
        this.phieuknId = phieuknId;
    }

    public String getNgaygiao() {
        return ngaygiao;
    }

    public void setNgaygiao(String ngaygiao) {
        this.ngaygiao = ngaygiao;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public String getDonviGiaoId() {
        return donviGiaoId;
    }

    public void setDonviGiaoId(String donviGiaoId) {
        this.donviGiaoId = donviGiaoId;
    }

    public String getNhanvienGiaoId() {
        return nhanvienGiaoId;
    }

    public void setNhanvienGiaoId(String nhanvienGiaoId) {
        this.nhanvienGiaoId = nhanvienGiaoId;
    }

    public String getDonviNhanId() {
        return donviNhanId;
    }

    public void setDonviNhanId(String donviNhanId) {
        this.donviNhanId = donviNhanId;
    }

    public String getNhanvienThId() {
        return nhanvienThId;
    }

    public void setNhanvienThId(String nhanvienThId) {
        this.nhanvienThId = nhanvienThId;
    }

    public String getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(String phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public String getHuonggiaoId() {
        return huonggiaoId;
    }

    public void setHuonggiaoId(String huonggiaoId) {
        this.huonggiaoId = huonggiaoId;
    }

    public String getGhichuTh() {
        return ghichuTh;
    }

    public void setGhichuTh(String ghichuTh) {
        this.ghichuTh = ghichuTh;
    }

    public String getTtphId() {
        return ttphId;
    }

    public void setTtphId(String ttphId) {
        this.ttphId = ttphId;
    }

    public String getNhanvienTraId() {
        return nhanvienTraId;
    }

    public void setNhanvienTraId(String nhanvienTraId) {
        this.nhanvienTraId = nhanvienTraId;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public String getNgayTh() {
        return ngayTh;
    }

    public void setNgayTh(String ngayTh) {
        this.ngayTh = ngayTh;
    }

    public String getNdTra() {
        return ndTra;
    }

    public void setNdTra(String ndTra) {
        this.ndTra = ndTra;
    }

    public String getSocongvan() {
        return socongvan;
    }

    public void setSocongvan(String socongvan) {
        this.socongvan = socongvan;
    }

    public String getNdXacminh() {
        return ndXacminh;
    }

    public void setNdXacminh(String ndXacminh) {
        this.ndXacminh = ndXacminh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getSongayTn() {
        return songayTn;
    }

    public void setSongayTn(String songayTn) {
        this.songayTn = songayTn;
    }

    public String getLydotraConId() {
        return lydotraConId;
    }

    public void setLydotraConId(String lydotraConId) {
        this.lydotraConId = lydotraConId;
    }

    public String getNdTraCon() {
        return ndTraCon;
    }

    public void setNdTraCon(String ndTraCon) {
        this.ndTraCon = ndTraCon;
    }

    public String getNgaytraCon() {
        return ngaytraCon;
    }

    public void setNgaytraCon(String ngaytraCon) {
        this.ngaytraCon = ngaytraCon;
    }

    public String getDonvitraConId() {
        return donvitraConId;
    }

    public void setDonvitraConId(String donvitraConId) {
        this.donvitraConId = donvitraConId;
    }

    public String getNhanvientraConId() {
        return nhanvientraConId;
    }

    public void setNhanvientraConId(String nhanvientraConId) {
        this.nhanvientraConId = nhanvientraConId;
    }

    public String getLinkfile() {
        return linkfile;
    }

    public void setLinkfile(String linkfile) {
        this.linkfile = linkfile;
    }
}
