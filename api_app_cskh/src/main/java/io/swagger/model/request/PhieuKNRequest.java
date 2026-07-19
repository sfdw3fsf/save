package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Danh sách chọn thiết bị
 */
@Validated
@Schema
public class PhieuKNRequest extends BaseDTO {
    @JsonProperty("id")
    private Long id = null;//1:Insert; 2:update; 3:Delete

    @JsonProperty("khieunaiId")
    private Long khieunaiId = null;
    @JsonProperty("thuebaoId")
    private Long thuebaoId = null;
    @JsonProperty("dichvuvtId")
    private Long dichvuvtId = null;
    @JsonProperty("loaitbId")
    private Long loaitbId = null;
    @JsonProperty("ngayTn")
    private String ngayTn = null;
    @JsonProperty("nguoiKn")
    private String nguoiKn = null;
    @JsonProperty("dienthoaiLh")
    private String dienthoaiLh = null;
    @JsonProperty("chudeknId")
    private Long chudeknId = null;
    @JsonProperty("ndKhieunai")
    private String ndKhieunai = null;
    @JsonProperty("ttknId")
    private Long ttknId = null;
    @JsonProperty("ngayGq")
    private String ngayGq = null;
    @JsonProperty("nhanvienGqId")
    private Long nhanvienGqId = null;
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
    private Long htknId = null;
    @JsonProperty("nguyennhanKn")
    private String nguyennhanKn = null;
    @JsonProperty("diachiLh")
    private String diachiLh = null;
    @JsonProperty("maTb")
    private String maTb = null;
    @JsonProperty("tienGc")
    private Long tienGc = null;
    @JsonProperty("vatGc")
    private Long vatGc = null;
    @JsonProperty("quytrinhId")
    private Long quytrinhId = null;
    @JsonProperty("huonggiaoId")
    private Long huonggiaoId = null;
    @JsonProperty("ketquaknId")
    private Long ketquaknId = null;
    @JsonProperty("thanhtoanId")
    private Long thanhtoanId = null;
    @JsonProperty("maKn")
    private String maKn = null;
    @JsonProperty("giamcuocDt")
    private Long giamcuocDt = null;
    @JsonProperty("giamcuocCp")
    private Long giamcuocCp = null;
    @JsonProperty("tientrinhknId")
    private Long tientrinhknId = null;
    @JsonProperty("vatDt")
    private Long vatDt = null;
    @JsonProperty("vatCp")
    private Long vatCp = null;

    //Kết quả xử lý
    @JsonProperty("xulyDuoc")
    private Long xulyDuoc = null;
    @JsonProperty("lydoKoXl")
    private String lydoKoXl = null;//Lý do không xử lý được
    @JsonProperty("ngayXl")
    private String ngayXl = null;
    @JsonProperty("nguoixlId")
    private Long nguoixlId = null;
    @JsonProperty("phieuknId")
    private Long phieuknId = null;
    @JsonProperty("nguyennhanXl")
    private String nguyennhanXl = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKhieunaiId() {
        return khieunaiId;
    }

    public void setKhieunaiId(Long khieunaiId) {
        this.khieunaiId = khieunaiId;
    }

    public Long getThuebaoId() {
        return thuebaoId;
    }

    public void setThuebaoId(Long thuebaoId) {
        this.thuebaoId = thuebaoId;
    }

    public Long getDichvuvtId() {
        return dichvuvtId;
    }

    public void setDichvuvtId(Long dichvuvtId) {
        this.dichvuvtId = dichvuvtId;
    }

    public Long getLoaitbId() {
        return loaitbId;
    }

    public void setLoaitbId(Long loaitbId) {
        this.loaitbId = loaitbId;
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

    public Long getChudeknId() {
        return chudeknId;
    }

    public void setChudeknId(Long chudeknId) {
        this.chudeknId = chudeknId;
    }

    public String getNdKhieunai() {
        return ndKhieunai;
    }

    public void setNdKhieunai(String ndKhieunai) {
        this.ndKhieunai = ndKhieunai;
    }

    public Long getTtknId() {
        return ttknId;
    }

    public void setTtknId(Long ttknId) {
        this.ttknId = ttknId;
    }

    public String getNgayGq() {
        return ngayGq;
    }

    public void setNgayGq(String ngayGq) {
        this.ngayGq = ngayGq;
    }

    public Long getNhanvienGqId() {
        return nhanvienGqId;
    }

    public void setNhanvienGqId(Long nhanvienGqId) {
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

    public Long getHtknId() {
        return htknId;
    }

    public void setHtknId(Long htknId) {
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

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public Long getTienGc() {
        return tienGc;
    }

    public void setTienGc(Long tienGc) {
        this.tienGc = tienGc;
    }

    public Long getVatGc() {
        return vatGc;
    }

    public void setVatGc(Long vatGc) {
        this.vatGc = vatGc;
    }

    public Long getQuytrinhId() {
        return quytrinhId;
    }

    public void setQuytrinhId(Long quytrinhId) {
        this.quytrinhId = quytrinhId;
    }

    public Long getHuonggiaoId() {
        return huonggiaoId;
    }

    public void setHuonggiaoId(Long huonggiaoId) {
        this.huonggiaoId = huonggiaoId;
    }

    public Long getKetquaknId() {
        return ketquaknId;
    }

    public void setKetquaknId(Long ketquaknId) {
        this.ketquaknId = ketquaknId;
    }

    public Long getThanhtoanId() {
        return thanhtoanId;
    }

    public void setThanhtoanId(Long thanhtoanId) {
        this.thanhtoanId = thanhtoanId;
    }

    public String getMaKn() {
        return maKn;
    }

    public void setMaKn(String maKn) {
        this.maKn = maKn;
    }

    public Long getGiamcuocDt() {
        return giamcuocDt;
    }

    public void setGiamcuocDt(Long giamcuocDt) {
        this.giamcuocDt = giamcuocDt;
    }

    public Long getGiamcuocCp() {
        return giamcuocCp;
    }

    public void setGiamcuocCp(Long giamcuocCp) {
        this.giamcuocCp = giamcuocCp;
    }

    public Long getTientrinhknId() {
        return tientrinhknId;
    }

    public void setTientrinhknId(Long tientrinhknId) {
        this.tientrinhknId = tientrinhknId;
    }

    public Long getVatDt() {
        return vatDt;
    }

    public void setVatDt(Long vatDt) {
        this.vatDt = vatDt;
    }

    public Long getVatCp() {
        return vatCp;
    }

    public void setVatCp(Long vatCp) {
        this.vatCp = vatCp;
    }

    public Long getXulyDuoc() {
        return xulyDuoc;
    }

    public void setXulyDuoc(Long xulyDuoc) {
        this.xulyDuoc = xulyDuoc;
    }

    public String getLydoKoXl() {
        return lydoKoXl;
    }

    public void setLydoKoXl(String lydoKoXl) {
        this.lydoKoXl = lydoKoXl;
    }

    public String getNgayXl() {
        return ngayXl;
    }

    public void setNgayXl(String ngayXl) {
        this.ngayXl = ngayXl;
    }

    public Long getNguoixlId() {
        return nguoixlId;
    }

    public void setNguoixlId(Long nguoixlId) {
        this.nguoixlId = nguoixlId;
    }

    public Long getPhieuknId() {
        return phieuknId;
    }

    public void setPhieuknId(Long phieuknId) {
        this.phieuknId = phieuknId;
    }

    public String getNguyennhanXl() {
        return nguyennhanXl;
    }

    public void setNguyennhanXl(String nguyennhanXl) {
        this.nguyennhanXl = nguyennhanXl;
    }
}
