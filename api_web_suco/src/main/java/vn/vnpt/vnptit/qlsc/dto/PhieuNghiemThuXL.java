package vn.vnpt.vnptit.qlsc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.Map;

@Schema
public class PhieuNghiemThuXL {
    @JsonProperty("chon")
    private String chon = null;
    @JsonProperty("suCoId")
    private Long suCoId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("mucDoSc")
    private String mucDoSc = null;
    @JsonProperty("nhomSc")
    private String nhomSc = null;
    @JsonProperty("nguonSc")
    private String nguonSc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("thoiGian")
    private String thoiGian = null;
    @JsonProperty("trangThaiSc")
    private String trangThaiSc = null;
    @JsonProperty("canhBaoDt")
    private String canhBaoDt = null;
    @JsonProperty("mauNen")
    private String mauNen = null;
    @JsonProperty("mauChu")
    private String mauChu = null;
    @JsonProperty("maQTNet")
    private String maQTNet;

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getMucDoSc() {
        return mucDoSc;
    }

    public void setMucDoSc(String mucDoSc) {
        this.mucDoSc = mucDoSc;
    }

    public String getNhomSc() {
        return nhomSc;
    }

    public void setNhomSc(String nhomSc) {
        this.nhomSc = nhomSc;
    }

    public String getNguonSc() {
        return nguonSc;
    }

    public void setNguonSc(String nguonSc) {
        this.nguonSc = nguonSc;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getNgaySc() {
        return ngaySc;
    }

    public void setNgaySc(String ngaySc) {
        this.ngaySc = ngaySc;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThaiSc() {
        return trangThaiSc;
    }

    public void setTrangThaiSc(String trangThaiSc) {
        this.trangThaiSc = trangThaiSc;
    }

    public String getCanhBaoDt() {
        return canhBaoDt;
    }

    public void setCanhBaoDt(String canhBaoDt) {
        this.canhBaoDt = canhBaoDt;
    }

    public String getMauNen() {
        return mauNen;
    }

    public void setMauNen(String mauNen) {
        this.mauNen = mauNen;
    }

    public String getMauChu() {
        return mauChu;
    }

    public void setMauChu(String mauChu) {
        this.mauChu = mauChu;
    }

    public void setMaQTNet(String maQTNet) {
        this.maQTNet = maQTNet;
    }

    public String getMaQTNet() {
        return maQTNet;
    }

    public PhieuNghiemThuXL convert(Map<String, Object> obj) {
        try {
            chon = (String) obj.get("CHON");
            suCoId = obj != null ? ((Number) obj.get("SUCO_ID")).longValue() : null;

            maSc = (String) obj.get("MA_SC");
            mucDoSc = (String) obj.get("MUCDO_SC");
            nhomSc = (String) obj.get("NHOM_SC");
            nguonSc = (String) obj.get("NGUON_SC");
            noiDungSc = (String) obj.get("NOIDUNG_SC");
            ngaySc = (String) obj.get("NGAY_SC");
            thoiGian = (String) obj.get("THOIGIAN");
            trangThaiSc = (String) obj.get("TRANGTHAI_SC");

            mauNen = (String) obj.get("MAUNEN");
            mauChu = (String) obj.get("MAUCHU");
        }catch (Exception e) {
            return null;
        }
        try{
            canhBaoDt = (String) obj.get("CANHBAO_DT");
        }catch(Exception e){
            canhBaoDt = null;
        }

        try{
            maQTNet = (String) obj.get("NET_MA_QT");
        }catch(Exception e){
            maQTNet = null;
        }


        return this;
    }
}

