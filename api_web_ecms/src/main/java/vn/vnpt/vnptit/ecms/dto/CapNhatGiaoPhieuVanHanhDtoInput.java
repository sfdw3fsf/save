package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CapNhatGiaoPhieuVanHanhDtoInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer kieu;
    private Integer phieuvh_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer vanhanh_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer donvi_nhan_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nhanvien_nhan_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Date ngay_giao;
    private String noidung_giao;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer phieuvh_cha_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer ttvh_id;

    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    public Integer getPhieuvh_id() {
        return phieuvh_id;
    }

    public void setPhieuvh_id(Integer phieuvh_id) {
        this.phieuvh_id = phieuvh_id;
    }

    public Integer getVanhanh_id() {
        return vanhanh_id;
    }

    public void setVanhanh_id(Integer vanhanh_id) {
        this.vanhanh_id = vanhanh_id;
    }

    public Integer getDonvi_nhan_id() {
        return donvi_nhan_id;
    }

    public void setDonvi_nhan_id(Integer donvi_nhan_id) {
        this.donvi_nhan_id = donvi_nhan_id;
    }

    public Integer getNhanvien_nhan_id() {
        return nhanvien_nhan_id;
    }

    public void setNhanvien_nhan_id(Integer nhanvien_nhan_id) {
        this.nhanvien_nhan_id = nhanvien_nhan_id;
    }

    public Date getNgay_giao() {
        return ngay_giao;
    }

    public void setNgay_giao(Date ngay_giao) {
        this.ngay_giao = ngay_giao;
    }

    public String getNoidung_giao() {
        return noidung_giao;
    }

    public void setNoidung_giao(String noidung_giao) {
        this.noidung_giao = noidung_giao;
    }

    public Integer getPhieuvh_cha_id() {
        return phieuvh_cha_id;
    }

    public void setPhieuvh_cha_id(Integer phieuvh_cha_id) {
        this.phieuvh_cha_id = phieuvh_cha_id;
    }

    public Integer getTtvh_id() {
        return ttvh_id;
    }

    public void setTtvh_id(Integer ttvh_id) {
        this.ttvh_id = ttvh_id;
    }
}
