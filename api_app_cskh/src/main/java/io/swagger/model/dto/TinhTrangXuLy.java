package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Tình trạng thiết bị
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TinhTrangXuLy extends BaseDTO {

    @JsonProperty("chon")
    private String chon;

    @JsonProperty("suCoPtmId")
    private Long suCoPtmId;

    @JsonProperty("tinhTrang")
    private String tinhTrang;

    @JsonProperty("loaiPtmId")
    private Long loaiPtmId;

    @JsonProperty("ptmId")
    private Long ptmId;

    @JsonProperty("congSoi")
    private String congSoi;

    @JsonProperty("donViId")
    private Long donViId;

    @JsonProperty("donViNhan")
    private String donViNhan;

    @JsonProperty("kieuGiao")
    private String kieuGiao;

    @JsonProperty("phieuId")
    private Long phieuId;

    @JsonProperty("ngayGiao")
    private String ngayGiao;

    @JsonProperty("ndGiao")
    private String ndGiao;

    @JsonProperty("ttphId")
    private Long ttphId;

    @JsonProperty("ngayTra")
    private String ngayTra;

    @JsonProperty("ndTra")
    private String ndTra;

    @JsonProperty("lyDoTra")
    private String lyDoTra;

    @JsonProperty("khan")
    private String khan;

    @JsonProperty("chiTieuTg")
    private String chiTieuTg;

    @JsonProperty("conLai")
    private String conLai;

    @JsonProperty("ngayTh")
    private String ngayTh;

    @JsonProperty("nguoiTh")
    private String nguoiTh;

    @JsonProperty("ghiChuTh")
    private String ghiChuTh;

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public Long getSuCoPtmId() {
        return suCoPtmId;
    }

    public void setSuCoPtmId(Long suCoPtmId) {
        this.suCoPtmId = suCoPtmId;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Long getLoaiPtmId() {
        return loaiPtmId;
    }

    public void setLoaiPtmId(Long loaiPtmId) {
        this.loaiPtmId = loaiPtmId;
    }

    public Long getPtmId() {
        return ptmId;
    }

    public void setPtmId(Long ptmId) {
        this.ptmId = ptmId;
    }

    public String getCongSoi() {
        return congSoi;
    }

    public void setCongSoi(String congSoi) {
        this.congSoi = congSoi;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public String getDonViNhan() {
        return donViNhan;
    }

    public void setDonViNhan(String donViNhan) {
        this.donViNhan = donViNhan;
    }

    public String getKieuGiao() {
        return kieuGiao;
    }

    public void setKieuGiao(String kieuGiao) {
        this.kieuGiao = kieuGiao;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public Long getTtphId() {
        return ttphId;
    }

    public void setTtphId(Long ttphId) {
        this.ttphId = ttphId;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getNdTra() {
        return ndTra;
    }

    public void setNdTra(String ndTra) {
        this.ndTra = ndTra;
    }

    public String getLyDoTra() {
        return lyDoTra;
    }

    public void setLyDoTra(String lyDoTra) {
        this.lyDoTra = lyDoTra;
    }

    public String getKhan() {
        return khan;
    }

    public void setKhan(String khan) {
        this.khan = khan;
    }

    public String getChiTieuTg() {
        return chiTieuTg;
    }

    public void setChiTieuTg(String chiTieuTg) {
        this.chiTieuTg = chiTieuTg;
    }

    public String getConLai() {
        return conLai;
    }

    public void setConLai(String conLai) {
        this.conLai = conLai;
    }

    public String getNgayTh() {
        return ngayTh;
    }

    public void setNgayTh(String ngayTh) {
        this.ngayTh = ngayTh;
    }

    public String getNguoiTh() {
        return nguoiTh;
    }

    public void setNguoiTh(String nguoiTh) {
        this.nguoiTh = nguoiTh;
    }

    public String getGhiChuTh() {
        return ghiChuTh;
    }

    public void setGhiChuTh(String ghiChuTh) {
        this.ghiChuTh = ghiChuTh;
    }
}
