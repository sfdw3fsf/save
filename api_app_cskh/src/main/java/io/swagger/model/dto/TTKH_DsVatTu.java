package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TTKH_DsVatTu
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TTKH_DsVatTu extends BaseDTO {
    @JsonProperty("vatTuId")
    private Long vatTuId = null;
    @JsonProperty("kieuTBId")
    private Long kieuTBId = null;
    @JsonProperty("tinhTrangId")
    private Long tinhTrangId = null;
    @JsonProperty("trangBiId")
    private Long trangBiId = null;
    @JsonProperty("khoId")
    private Long khoId = null;
    @JsonProperty("sdvtId")
    private Long sdvtId = null;
    @JsonProperty("dvTinhId")
    private Long dvTinhId = null;
    @JsonProperty("thietBiId")
    private Long thietBiId = null;
    @JsonProperty("loHang")
    private String loHang = null;
    @JsonProperty("ngaySD")
    private String ngaySD = null;
    @JsonProperty("serialGP")
    private String serialGP = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("tenVT")
    private String tenVT = null;
    @JsonProperty("soLuong")
    private String soLuong = null;
    @JsonProperty("dvTinh")
    private String dvTinh = null;
    @JsonProperty("bsVT")
    private String bsVT = null;

    public Long getVatTuId() {
        return vatTuId;
    }

    public void setVatTuId(Long vatTuId) {
        this.vatTuId = vatTuId;
    }

    public Long getKieuTBId() {
        return kieuTBId;
    }

    public void setKieuTBId(Long kieuTBId) {
        this.kieuTBId = kieuTBId;
    }

    public Long getTinhTrangId() {
        return tinhTrangId;
    }

    public void setTinhTrangId(Long tinhTrangId) {
        this.tinhTrangId = tinhTrangId;
    }

    public Long getTrangBiId() {
        return trangBiId;
    }

    public void setTrangBiId(Long trangBiId) {
        this.trangBiId = trangBiId;
    }

    public Long getKhoId() {
        return khoId;
    }

    public void setKhoId(Long khoId) {
        this.khoId = khoId;
    }

    public Long getSdvtId() {
        return sdvtId;
    }

    public void setSdvtId(Long sdvtId) {
        this.sdvtId = sdvtId;
    }

    public Long getDvTinhId() {
        return dvTinhId;
    }

    public void setDvTinhId(Long dvTinhId) {
        this.dvTinhId = dvTinhId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public String getLoHang() {
        return loHang;
    }

    public void setLoHang(String loHang) {
        this.loHang = loHang;
    }

    public String getNgaySD() {
        return ngaySD;
    }

    public void setNgaySD(String ngaySD) {
        this.ngaySD = ngaySD;
    }

    public String getSerialGP() {
        return serialGP;
    }

    public void setSerialGP(String serialGP) {
        this.serialGP = serialGP;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenVT() {
        return tenVT;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getDvTinh() {
        return dvTinh;
    }

    public void setDvTinh(String dvTinh) {
        this.dvTinh = dvTinh;
    }

    public String getBsVT() {
        return bsVT;
    }

    public void setBsVT(String bsVT) {
        this.bsVT = bsVT;
    }
}
