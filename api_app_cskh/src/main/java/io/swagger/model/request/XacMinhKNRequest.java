package io.swagger.model.request;

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
public class XacMinhKNRequest extends BaseDTO {
    @JsonProperty("phieuKNId")
    private Long phieuKNId = null;
    @JsonProperty("khieuNaiId")
    private Long khieuNaiId = null;
    @JsonProperty("ttknId")
    private Long ttknId = null;
    @JsonProperty("maCongVan")
    private String maCongVan = null;
    @JsonProperty("nvTHId")
    private Long nvTHId = null;
    @JsonProperty("ndThucHien")
    private String ndThucHien = null;
    @JsonProperty("phuongAnXLId")
    private Long phuongAnXLId = null;
    @JsonProperty("ketQuaKNId")
    private Long ketQuaKNId = null;
    @JsonProperty("hinhThuctlId")
    private Long hinhThuctlId = null;
    @JsonProperty("noiDungXM")
    private String noiDungXM = null;
    @JsonProperty("nguyenNhan")
    private String nguyenNhan = null;
    @JsonProperty("noiDungTL")
    private String noiDungTL = null;
    @JsonProperty("danhGia")
    private Long danhGia = null;
    @JsonProperty("quyTrinhId")
    private Long quyTrinhId = null;
    @JsonProperty("donViKnId")
    private Long donViKnId = null;
    @JsonProperty("kqXLId")
    private Long kqXLId = null;

    public Long getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(Long phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public Long getTtknId() {
        return ttknId;
    }

    public void setTtknId(Long ttknId) {
        this.ttknId = ttknId;
    }

    public String getMaCongVan() {
        return maCongVan;
    }

    public void setMaCongVan(String maCongVan) {
        this.maCongVan = maCongVan;
    }

    public Long getNvTHId() {
        return nvTHId;
    }

    public void setNvTHId(Long nvTHId) {
        this.nvTHId = nvTHId;
    }

    public String getNdThucHien() {
        return ndThucHien;
    }

    public void setNdThucHien(String ndThucHien) {
        this.ndThucHien = ndThucHien;
    }

    public Long getPhuongAnXLId() {
        return phuongAnXLId;
    }

    public void setPhuongAnXLId(Long phuongAnXLId) {
        this.phuongAnXLId = phuongAnXLId;
    }

    public Long getKetQuaKNId() {
        return ketQuaKNId;
    }

    public void setKetQuaKNId(Long ketQuaKNId) {
        this.ketQuaKNId = ketQuaKNId;
    }

    public Long getHinhThuctlId() {
        return hinhThuctlId;
    }

    public void setHinhThuctlId(Long hinhThuctlId) {
        this.hinhThuctlId = hinhThuctlId;
    }

    public String getNoiDungXM() {
        return noiDungXM;
    }

    public void setNoiDungXM(String noiDungXM) {
        this.noiDungXM = noiDungXM;
    }

    public String getNguyenNhan() {
        return nguyenNhan;
    }

    public void setNguyenNhan(String nguyenNhan) {
        this.nguyenNhan = nguyenNhan;
    }

    public String getNoiDungTL() {
        return noiDungTL;
    }

    public void setNoiDungTL(String noiDungTL) {
        this.noiDungTL = noiDungTL;
    }

    public Long getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(Long danhGia) {
        this.danhGia = danhGia;
    }

    public Long getQuyTrinhId() {
        return quyTrinhId;
    }

    public void setQuyTrinhId(Long quyTrinhId) {
        this.quyTrinhId = quyTrinhId;
    }

    public Long getDonViKnId() {
        return donViKnId;
    }

    public void setDonViKnId(Long donViKnId) {
        this.donViKnId = donViKnId;
    }

    public Long getKqXLId() {
        return kqXLId;
    }

    public void setKqXLId(Long kqXLId) {
        this.kqXLId = kqXLId;
    }
}
