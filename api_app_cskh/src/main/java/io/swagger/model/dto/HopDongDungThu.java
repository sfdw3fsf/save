package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Danh bạ base
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class HopDongDungThu extends BaseDTO {
    @JsonProperty("ctv_id")
    private String ctv_id = null;
    @JsonProperty("so_dt")
    private String so_dt = null;
    @JsonProperty("ma_gd")
    private String ma_gd = null;
    @JsonProperty("nhanvien_id")
    private String nhanvien_id = null;
    @JsonProperty("hdtb_id")
    private String hdtb_id = null;
    @JsonProperty("songay")
    private String songay = null;
    @JsonProperty("ngay_tb")
    private String ngay_tb = null;
    @JsonProperty("ngay_bd")
    private String ngay_bd = null;
    @JsonProperty("ngay_kt")
    private String ngay_kt = null;
    @JsonProperty("chitietkm_id")
    private String chitietkm_id = null;
    @JsonProperty("may_cn")
    private String may_cn = null;
    @JsonProperty("ngay_cn")
    private String ngay_cn = null;
    @JsonProperty("nguoi_cn")
    private String nguoi_cn = null;
    @JsonProperty("ip_cn")
    private String ip_cn = null;
    @JsonProperty("dichvuvt_id")
    private String dichvuvt_id = null;
    @JsonProperty("trangthai")
    private String trangthai = null;
    @JsonProperty("ma_tb")
    private String ma_tb = null;
    @JsonProperty("ten_tb")
    private String ten_tb = null;
    @JsonProperty("diachi_tb")
    private String diachi_tb = null;
    @JsonProperty("loaihinh_tb")
    private String loaihinh_tb = null;
    @JsonProperty("loaitb_id")
    private String loaitb_id = null;
    @JsonProperty("thuebao_id")
    private String thuebao_id = null;
    @JsonProperty("dv_lhd")
    private String dv_lhd = null;
    @JsonProperty("ten_nvgt")
    private String ten_nvgt = null;
    @JsonProperty("trangthai_tv")
    private String trangthai_tv = null;
    @JsonProperty("nguoi_tv")
    private String nguoi_tv = null;
    @JsonProperty("ngay_tv")
    private String ngay_tv = null;
    @JsonProperty("datcoc")
    private String datcoc = null;
    @JsonProperty("ten_kv")
    private String ten_kv = null;
    @JsonProperty("khuvuc_id")
    private String khuvuc_id = null;
    @JsonProperty("donvi_id")
    private String donvi_id = null;

    public String getCtv_id() {
        return ctv_id;
    }

    public void setCtv_id(String ctv_id) {
        this.ctv_id = ctv_id;
    }

    public String getSo_dt() {
        return so_dt;
    }

    public void setSo_dt(String so_dt) {
        this.so_dt = so_dt;
    }

    public String getMa_gd() {
        return ma_gd;
    }

    public void setMa_gd(String ma_gd) {
        this.ma_gd = ma_gd;
    }

    public String getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(String nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public String getHdtb_id() {
        return hdtb_id;
    }

    public void setHdtb_id(String hdtb_id) {
        this.hdtb_id = hdtb_id;
    }

    public String getSongay() {
        return songay;
    }

    public void setSongay(String songay) {
        this.songay = songay;
    }

    public String getNgay_tb() {
        return ngay_tb;
    }

    public void setNgay_tb(String ngay_tb) {
        this.ngay_tb = ngay_tb;
    }

    public String getNgay_bd() {
        return ngay_bd;
    }

    public void setNgay_bd(String ngay_bd) {
        this.ngay_bd = ngay_bd;
    }

    public String getNgay_kt() {
        return ngay_kt;
    }

    public void setNgay_kt(String ngay_kt) {
        this.ngay_kt = ngay_kt;
    }

    public String getChitietkm_id() {
        return chitietkm_id;
    }

    public void setChitietkm_id(String chitietkm_id) {
        this.chitietkm_id = chitietkm_id;
    }

    public String getMay_cn() {
        return may_cn;
    }

    public void setMay_cn(String may_cn) {
        this.may_cn = may_cn;
    }

    public String getNgay_cn() {
        return ngay_cn;
    }

    public void setNgay_cn(String ngay_cn) {
        this.ngay_cn = ngay_cn;
    }

    public String getNguoi_cn() {
        return nguoi_cn;
    }

    public void setNguoi_cn(String nguoi_cn) {
        this.nguoi_cn = nguoi_cn;
    }

    public String getIp_cn() {
        return ip_cn;
    }

    public void setIp_cn(String ip_cn) {
        this.ip_cn = ip_cn;
    }

    public String getDichvuvt_id() {
        return dichvuvt_id;
    }

    public void setDichvuvt_id(String dichvuvt_id) {
        this.dichvuvt_id = dichvuvt_id;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getMa_tb() {
        return ma_tb;
    }

    public void setMa_tb(String ma_tb) {
        this.ma_tb = ma_tb;
    }

    public String getTen_tb() {
        return ten_tb;
    }

    public void setTen_tb(String ten_tb) {
        this.ten_tb = ten_tb;
    }

    public String getDiachi_tb() {
        return diachi_tb;
    }

    public void setDiachi_tb(String diachi_tb) {
        this.diachi_tb = diachi_tb;
    }

    public String getLoaihinh_tb() {
        return loaihinh_tb;
    }

    public void setLoaihinh_tb(String loaihinh_tb) {
        this.loaihinh_tb = loaihinh_tb;
    }

    public String getLoaitb_id() {
        return loaitb_id;
    }

    public void setLoaitb_id(String loaitb_id) {
        this.loaitb_id = loaitb_id;
    }

    public String getThuebao_id() {
        return thuebao_id;
    }

    public void setThuebao_id(String thuebao_id) {
        this.thuebao_id = thuebao_id;
    }

    public String getDv_lhd() {
        return dv_lhd;
    }

    public void setDv_lhd(String dv_lhd) {
        this.dv_lhd = dv_lhd;
    }

    public String getTen_nvgt() {
        return ten_nvgt;
    }

    public void setTen_nvgt(String ten_nvgt) {
        this.ten_nvgt = ten_nvgt;
    }

    public String getTrangthai_tv() {
        return trangthai_tv;
    }

    public void setTrangthai_tv(String trangthai_tv) {
        this.trangthai_tv = trangthai_tv;
    }

    public String getNguoi_tv() {
        return nguoi_tv;
    }

    public void setNguoi_tv(String nguoi_tv) {
        this.nguoi_tv = nguoi_tv;
    }

    public String getNgay_tv() {
        return ngay_tv;
    }

    public void setNgay_tv(String ngay_tv) {
        this.ngay_tv = ngay_tv;
    }

    public String getDatcoc() {
        return datcoc;
    }

    public void setDatcoc(String datcoc) {
        this.datcoc = datcoc;
    }

    public String getTen_kv() {
        return ten_kv;
    }

    public void setTen_kv(String ten_kv) {
        this.ten_kv = ten_kv;
    }

    public String getKhuvuc_id() {
        return khuvuc_id;
    }

    public void setKhuvuc_id(String khuvuc_id) {
        this.khuvuc_id = khuvuc_id;
    }

    public String getDonvi_id() {
        return donvi_id;
    }

    public void setDonvi_id(String donvi_id) {
        this.donvi_id = donvi_id;
    }
}
